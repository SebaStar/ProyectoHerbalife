package cl.inacap.herbalifeproject;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.adapter.GraficosPagerAdapter;

public class GraficosActivity extends AppCompatActivity {

    Toolbar toolbar;
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gráficos de seguimiento");

        fragments.add(PesoFragment.getInstance());
        fragments.add(GrasaFragment.getInstance());
        fragments.add(MasaMuscularFragment.getInstance());
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
