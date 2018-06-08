package cl.inacap.herbalifeproject;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.adapter.GraficosPagerAdapter;
import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.utils.Solicitud;

public class GraficosActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView titleTv, clienteTv;
    TabLayout tabLayout;
    ViewPager container;

    List<Fragment> fragments = new ArrayList<>();
    GraficosPagerAdapter adapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.graficos_toolbar);
        tabLayout = findViewById(R.id.graficos_tabs);
        container = findViewById(R.id.graficos_container);
        titleTv = toolbar.findViewById(R.id.toolbar_title);
        clienteTv = toolbar.findViewById(R.id.toolbar_username);

        setSupportActionBar(toolbar);
        int clienteId = getIntent().getIntExtra(Solicitud.CLIENTE_ID, -1);
        Bundle bundle = new Bundle();
        bundle.putInt(Solicitud.CLIENTE_ID, clienteId);
        titleTv.setText("Gráficos de seguimiento");
        clienteTv.setText(new HerbalifeDAO(context).buscarCliente(clienteId).getNombre());

        fragments.add(PesoFragment.getInstance(bundle));
        fragments.add(GrasaFragment.getInstance(bundle));
        fragments.add(MasaMuscularFragment.getInstance(bundle));
        adapter = new GraficosPagerAdapter(getSupportFragmentManager(), fragments);
        container.setAdapter(adapter);
        container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(container));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
