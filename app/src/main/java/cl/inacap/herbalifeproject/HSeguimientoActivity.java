package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.adapter.HistorialSeguimientoPagerAdapter;
import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.dto.Seguimiento;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.Solicitud;

public class HSeguimientoActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager container;
    TextView tituloTv, clienteTv, vacioTv;
    FloatingActionButton fabEditar;

    List<Fragment> fragments = new ArrayList<>();
    List<Seguimiento> seguimientos = new ArrayList<>();
    Cliente cliente;
    HistorialSeguimientoPagerAdapter adapter;
    HerbalifeDAO hdao;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hseguimiento);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.hseguimiento_toolbar);
        container = findViewById(R.id.hseguimiento_container);
        vacioTv = findViewById(R.id.hseguimiento_vacio);
        fabEditar = findViewById(R.id.hseguimiento_fabEditar);
        tituloTv = toolbar.findViewById(R.id.toolbar_title);
        clienteTv = toolbar.findViewById(R.id.toolbar_username);

        setSupportActionBar(toolbar);
        tituloTv.setText("Historial de seguimiento");
        hdao = new HerbalifeDAO(context);
        final int clienteId = getIntent().getIntExtra(Solicitud.CLIENTE_ID, -1);
        cliente = hdao.buscarCliente(clienteId);
        clienteTv.setText(cliente.getNombre());
        seguimientos = hdao.listarSeguimientos(cliente.getId(), Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID));
        for (Seguimiento seguimiento : seguimientos)
            fragments.add(HSeguimientoFragment.newInstance(seguimiento.getId(), clienteId));
        adapter = new HistorialSeguimientoPagerAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);

        if (seguimientos.isEmpty()) {
            container.setVisibility(View.GONE);
            vacioTv.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.VISIBLE);
            vacioTv.setVisibility(View.GONE);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RESeguimientoActivity.class);
                i.putExtra(Solicitud.MODO_ID, 1);
                i.putExtra(Solicitud.CLIENTE_ID, cliente.getId());
                i.putExtra(Solicitud.SEGUIMIENTO_ID, seguimientos.get(container.getCurrentItem()).getId());
                startActivity(i);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
