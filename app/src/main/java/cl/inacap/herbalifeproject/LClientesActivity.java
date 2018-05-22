package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.adapter.ClienteAdapter;
import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.interfaces.Listeners;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class LClientesActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView listaClientes;
    TextView vacioTv;
    MaterialSearchView searchView;

    HerbalifeDAO hdao;
    List<Cliente> clientes;
    ClienteAdapter adapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lclientes);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.lc_toolbar);
        listaClientes = findViewById(R.id.lc_listaClientes);
        vacioTv = findViewById(R.id.lc_vacio);
        searchView = findViewById(R.id.lc_searchView);
        searchView.setVoiceSearch(true);
        setSupportActionBar(toolbar);

        hdao = new HerbalifeDAO(context);
        clientes = hdao.listarClientes(Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID));
        adapter = new ClienteAdapter(clientes, context, new Listeners.OnRowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(context, MainClienteActivity.class);
                i.putExtra(Solicitud.CLIENTE_ID, clientes.get(position).getId());
                startActivity(i);
            }

            @Override
            public void onEditClick(int position) {
                Toast.makeText(context, "Editar seleccionado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                Toast.makeText(context, "Eliminar seleccionado", Toast.LENGTH_SHORT).show();
            }
        });
        listaClientes.setAdapter(adapter);
        listaClientes.setLayoutManager(new LinearLayoutManager(context));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                chequearItems(true);
                return SystemUtils.getInstance().keyboard(context, searchView, true);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty())
                    adapter.getFilter().filter(newText);
                else
                    adapter.reset();
                chequearItems(true);
                return true;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                adapter.reset();
                chequearItems(false);
            }
        });
        chequearItems(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listar_clientes_menu, menu);
        searchView.setMenuItem(menu.findItem(R.id.lc_search));
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String word = matches.get(0);
                if (!TextUtils.isEmpty(word))
                    searchView.setQuery(word, false);
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) searchView.closeSearch();
        else super.onBackPressed();
    }

    private void chequearItems(boolean filtrado) {
        if (!filtrado && adapter.isEmpty()) {
            listaClientes.setVisibility(View.GONE);
            vacioTv.setText("Sin clientes");
            vacioTv.setVisibility(View.VISIBLE);
        } else if (filtrado && adapter.isEmpty()) {
            listaClientes.setVisibility(View.GONE);
            vacioTv.setText("Sin resultados");
            vacioTv.setVisibility(View.VISIBLE);
        } else {
            listaClientes.setVisibility(View.VISIBLE);
            vacioTv.setVisibility(View.GONE);
        }
    }
}
