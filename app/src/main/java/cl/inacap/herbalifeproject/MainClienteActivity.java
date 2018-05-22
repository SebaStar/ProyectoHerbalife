package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.utils.Solicitud;

public class MainClienteActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button registrarSeguimiento, verProgramaNutricional, historialSeguimiento, graficosProgreso;
    TextView clienteTv;

    HerbalifeDAO hdao;
    Cliente cliente;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.main_cliente_toolbar);
        registrarSeguimiento = findViewById(R.id.main_cliente_registrarSeguimiento);
        verProgramaNutricional = findViewById(R.id.main_cliente_verProgramaNutricional);
        historialSeguimiento = findViewById(R.id.main_cliente_historialSeguimiento);
        graficosProgreso = findViewById(R.id.main_cliente_graficosProgreso);
        clienteTv = toolbar.findViewById(R.id.toolbar_username);

        hdao = new HerbalifeDAO(context);
        cliente = hdao.buscarCliente(getIntent().getIntExtra(Solicitud.CLIENTE_ID, -1));

        clienteTv.setText(cliente.getNombre());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registrarSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        verProgramaNutricional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        historialSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HSeguimientoActivity.class);
                i.putExtra(Solicitud.CLIENTE_ID, cliente.getId());
                startActivity(i);
            }
        });
        graficosProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, GraficosActivity.class));
            }
        });
    }
}
