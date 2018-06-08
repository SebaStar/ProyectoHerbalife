package cl.inacap.herbalifeproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cl.inacap.herbalifeproject.adapter.ProductoAdapter;
import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.dto.Producto;
import cl.inacap.herbalifeproject.dto.ProgramaNutricional;
import cl.inacap.herbalifeproject.utils.Solicitud;

public class AProgramaNutricionalActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView titleTv, clienteTv, vacioTv;
    RecyclerView listadoProductos;

    HerbalifeDAO hdao;
    Cliente cliente;
    ProgramaNutricional programa;
    ArrayList<Producto> productos;
    ProductoAdapter adapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprograma_nutricional);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.aprograma_toolbar);
        titleTv = toolbar.findViewById(R.id.toolbar_title);
        clienteTv = toolbar.findViewById(R.id.toolbar_username);
        vacioTv = findViewById(R.id.aprograma_vacioTv);
        listadoProductos = findViewById(R.id.aprograma_productoList);
        setSupportActionBar(toolbar);

        hdao = new HerbalifeDAO(context);
        cliente = hdao.buscarCliente(getIntent().getIntExtra(Solicitud.CLIENTE_ID, 0));
        programa = hdao.buscarProgramaNutricional(cliente.getProgramaNutricionalId());

        titleTv.setText("Mi programa nutricional");
        clienteTv.setText(cliente.getNombre());

        if (programa == null) {
            listadoProductos.setVisibility(View.GONE);
            vacioTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setText(programa.getNombre());
            productos = hdao.listarProductos(programa.getId());
            adapter = new ProductoAdapter(productos, context);
            listadoProductos.setAdapter(adapter);
            listadoProductos.setLayoutManager(new LinearLayoutManager(context));

            listadoProductos.setVisibility(View.VISIBLE);
            vacioTv.setVisibility(View.GONE);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
