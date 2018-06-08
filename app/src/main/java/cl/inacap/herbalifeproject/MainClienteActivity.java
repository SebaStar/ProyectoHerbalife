package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    TextView titleTv, clienteTv;

    HerbalifeDAO hdao;
    Cliente cliente;

    Context context;
    Intent i;

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
        titleTv = toolbar.findViewById(R.id.toolbar_title);
        clienteTv = toolbar.findViewById(R.id.toolbar_username);

        hdao = new HerbalifeDAO(context);
        cliente = hdao.buscarCliente(getIntent().getIntExtra(Solicitud.CLIENTE_ID, -1));

        titleTv.setText("Ficha de seguimiento");
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
                i = new Intent(context, RESeguimientoActivity.class);
                i.putExtra(Solicitud.CLIENTE_ID, cliente.getId());
                startActivity(i);
            }
        });
        verProgramaNutricional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(context, AProgramaNutricionalActivity.class);
                i.putExtra(Solicitud.CLIENTE_ID, cliente.getId());
                startActivity(i);
            }
        });
        historialSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(context, HSeguimientoActivity.class);
                i.putExtra(Solicitud.CLIENTE_ID, cliente.getId());
                startActivity(i);
            }
        });
        graficosProgreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(context, GraficosActivity.class);
                i.putExtra(Solicitud.CLIENTE_ID, cliente.getId());
                startActivity(i);
            }
        });
    }
}
