package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Usuario;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView titleTv, usernameTv;
    Button agregarPrograma, registrarCliente, verClientes, cerrarSesion;

    HerbalifeDAO hdao;
    Usuario usuario;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.main_toolbar);
        titleTv = toolbar.findViewById(R.id.toolbar_title);
        usernameTv = toolbar.findViewById(R.id.toolbar_username);
        agregarPrograma = findViewById(R.id.main_agregarProgramaBtn);
        registrarCliente = findViewById(R.id.main_agregarClienteBtn);
        verClientes = findViewById(R.id.main_verClientesBtn);
        cerrarSesion = findViewById(R.id.main_cerrarSesionBtn);

        hdao = new HerbalifeDAO(context);
        usuario = hdao.buscarUsuario(Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID));

        setSupportActionBar(toolbar);
        titleTv.setText("Inicio");
        usernameTv.setText(SystemUtils.getInstance().getMensajeBienvenida() + ", " + usuario.getNombre());

        agregarPrograma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, REProgramaNuticionalActivity.class));
            }
        });
        registrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, REClienteActivity.class));
            }
        });
        verClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LClientesActivity.class));
            }
        });
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.setPreferenceBoolean(context, Preferences.MAIN_PREF, Preferences.NO_CERRAR_SESION, false);
                Preferences.setPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID, -1);
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
    }
}
