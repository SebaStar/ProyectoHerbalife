package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.adapter.ProductoAdapter;
import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Producto;
import cl.inacap.herbalifeproject.dto.ProgramaNutricional;
import cl.inacap.herbalifeproject.interfaces.Listeners;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;
import cl.inacap.herbalifeproject.view.ClearableEditText;

public class REProgramaNuticionalActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nombreTxt, duracionTxt;
    TextView cantidadTv;
    Button revisarBtn;
    FloatingActionButton aceptarBtn;

    ArrayList<Producto> productos = new ArrayList<>();
    HerbalifeDAO hdao;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprograma_nuticional);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.reprograma_toolbar);
        nombreTxt = findViewById(R.id.reprograma_nombreTxt);
        duracionTxt = findViewById(R.id.reprograma_duracionTxt);
        cantidadTv = findViewById(R.id.reprograma_cantidadTv);
        revisarBtn = findViewById(R.id.reprograma_revisarBtn);
        aceptarBtn = findViewById(R.id.reprograma_fabAgregar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar programa nutricional");

        hdao = new HerbalifeDAO(context);

        nombreTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        duracionTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });

        revisarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LProductosActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(Solicitud.PRODUCTOS, productos);
                i.putExtras(extras);
                startActivityForResult(i, Solicitud.CODIGO_LISTADO_PRODUCTOS);
            }
        });
        aceptarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreTxt.getText().toString().trim(), duracion = duracionTxt.getText().toString().trim(),
                        nombreError = null, duracionError = null, productosError = null;

                if (nombre.isEmpty() || duracion.isEmpty() || productos.isEmpty()) {
                    nombreError = nombre.isEmpty() ? "Campo requerido" : null;
                    duracionError = duracion.isEmpty() ? "Campo requerido" : null;
                    productosError = productos.isEmpty() ? "El programa nutricional debe tener al menos un producto." : null;
                } else {
                    if (hdao.agregarProgramaNutricional(new ProgramaNutricional(nombre, Integer.parseInt(duracion)))) {
                        int ultimoId = hdao.getUltimoIdDeProgramaNutricional();

                        for (Producto p : productos) {
                            if (hdao.agregarProducto(p)) {
                                hdao.agregarPnProducto(ultimoId, p.getId());
                            } else break;
                        }
                        Toast.makeText(context, "El programa nutricional se agregó correctamente.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, "No se pudo agregar el programa nutricional.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                nombreTxt.setError(nombreError);
                duracionTxt.setError(duracionError);
                if (productosError != null)
                    Snackbar.make(v, productosError, Snackbar.LENGTH_SHORT);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().mostrarDialogo(context).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        SystemUtils.getInstance().mostrarDialogo(context).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Solicitud.CODIGO_LISTADO_PRODUCTOS) {
            if (data != null && resultCode == Solicitud.RESULTADO_OK) {
                Bundle extras = data.getExtras();
                productos = (ArrayList<Producto>)extras.getSerializable(Solicitud.PRODUCTOS);
                cantidadTv.setText(productos == null ? "No hay productos." : productos.isEmpty() ?
                        "No hay productos." : "Hay " + productos.size() + " productos.");
            }
        }
    }
}
