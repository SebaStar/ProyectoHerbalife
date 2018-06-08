package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Producto;
import cl.inacap.herbalifeproject.dto.ProgramaNutricional;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class REProgramaNuticionalActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {

    Toolbar toolbar;
    EditText nombreTxt, duracionTxt;
    TextView cantidadTv;
    Button revisarBtn, registrarEditarBtn;

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
        registrarEditarBtn = findViewById(R.id.reprograma_registrarEditarBtn);

        hdao = new HerbalifeDAO(context);
        final int modo = getIntent().getIntExtra(Solicitud.MODO_ID, 0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(modo == 0 ? "Registrar programa nutricional" : "Editar programa nutricional");

        if (modo == 1) {
            ProgramaNutricional pn = hdao.buscarProgramaNutricional(getIntent().getIntExtra(Solicitud.PROGRAMA_NUTRICIONAL_ID, 0));
            productos = hdao.listarProductos(pn.getId());
            nombreTxt.setText(pn.getNombre());
            duracionTxt.setText(String.valueOf(pn.getDuracion()));
            registrarEditarBtn.setEnabled(true);
            registrarEditarBtn.setTextColor(Color.WHITE);
        }
        registrarEditarBtn.setText(modo == 0 ? "Registrar" : "Editar");
        nombreTxt.addTextChangedListener(this);
        duracionTxt.addTextChangedListener(this);

        nombreTxt.setOnFocusChangeListener(this);
        duracionTxt.setOnFocusChangeListener(this);

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
        registrarEditarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreTxt.getText().toString().trim(), duracion = duracionTxt.getText().toString().trim();

                if (modo == 0) {
                    if (hdao.agregarProgramaNutricional(new ProgramaNutricional(nombre, Integer.parseInt(duracion)))) {
                        agregarEliminarProductos(true, hdao.getUltimoIdDeProgramaNutricional());
                        Toast.makeText(context, "El programa nutricional se agregó correctamente.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(context, "No se pudo agregar el programa nutricional.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int id = getIntent().getIntExtra(Solicitud.PROGRAMA_NUTRICIONAL_ID, 0);
                    if (hdao.modificarProgramaNutricional(id, new ProgramaNutricional(nombre, Integer.parseInt(duracion)))) {
                        if (agregarEliminarProductos(false, id))
                            agregarEliminarProductos(true, id);
                        Toast.makeText(context, "El programa nutricional se modificó correctamente.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(context, "No se pudo modificar el programa nutricional.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().mostrarDialogo(context).show();
            }
        });
    }

    private boolean agregarEliminarProductos(boolean agregar, int id) {
        for (Producto p : productos) {
            if (agregar) {
                if (hdao.agregarProducto(p)) {
                    int productoId = hdao.getUltimoIdDeProducto();
                    if (!hdao.agregarPnProducto(id, productoId))
                        return false;
                }
            }
            else {
                if (hdao.eliminarPnProducto(id, p.getId())) {
                    if (!hdao.eliminarProducto(p.getId()))
                        return false;
                }
            }
        }
        return true;
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
                productos = (ArrayList<Producto>) extras.getSerializable(Solicitud.PRODUCTOS);
                cantidadTv.setText(productos == null ? "No hay productos." : productos.isEmpty() ?
                        "No hay productos." : "Hay " + productos.size() + " productos.");
                validarCampos();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        validarCampos();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        SystemUtils.getInstance().keyboard(context, v, !hasFocus);
    }

    private void validarCampos() {
        registrarEditarBtn.setEnabled(!nombreTxt.getText().toString().trim().isEmpty() && !duracionTxt.getText().toString().trim().isEmpty() &&
                !productos.isEmpty());
        registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
    }
}
