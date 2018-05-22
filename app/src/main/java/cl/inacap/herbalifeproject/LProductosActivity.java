package cl.inacap.herbalifeproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.adapter.ProductoAdapter;
import cl.inacap.herbalifeproject.dto.Producto;
import cl.inacap.herbalifeproject.interfaces.Listeners;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class LProductosActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView vacioTv;
    RecyclerView listadoProductos;
    FloatingActionButton fabAgregar;

    ArrayList<Producto> productos = new ArrayList<>();
    ProductoAdapter adapter;

    String[] productosLista;
    int index = -1;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lproductos);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.lps_toolbar);
        vacioTv = findViewById(R.id.lps_vacioTv);
        listadoProductos = findViewById(R.id.lps_listadoProductos);
        fabAgregar = findViewById(R.id.lps_fabAgregar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Listado de productos");

        Bundle extras = getIntent().getExtras();
        productos = (ArrayList<Producto>)extras.getSerializable(Solicitud.PRODUCTOS);
        adapter = new ProductoAdapter(productos, context, new Listeners.OnRemoveRowClickListener() {
            @Override
            public void onRemove(int position) {
                adapter.removeAt(position);
                chequearItems();
            }
        });
        listadoProductos.setAdapter(adapter);
        listadoProductos.setLayoutManager(new LinearLayoutManager(context));
        productosLista = getResources().getStringArray(R.array.productos);
        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_agregar_producto);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                final TextView productoTv = dialog.findViewById(R.id.dap_nombreProductoTv);
                productoTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        new AlertDialog.Builder(context).setTitle("Productos")
                                .setSingleChoiceItems(productosLista, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        productoTv.setText(productosLista[which]);
                                        index = which;
                                        dialog.dismiss();
                                    }
                                }).setCancelable(true).create().show();
                    }
                });
                productoTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context).setTitle("Productos")
                                .setSingleChoiceItems(productosLista, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        productoTv.setText(productosLista[which]);
                                        index = which;
                                        dialog.dismiss();
                                    }
                                }).setCancelable(true).create().show();
                    }
                });
                final EditText cantidadTxt = dialog.findViewById(R.id.dap_cantidadTxt);
                Button aceptarBtn = dialog.findViewById(R.id.dap_aceptarBtn);
                Button cancelarBtn = dialog.findViewById(R.id.dap_cancelarBtn);

                aceptarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productos.add(new Producto(index, Integer.parseInt(cantidadTxt.getText().toString())));
                        adapter.clearItems(productos);
                        SystemUtils.getInstance().keyboard(context, v, true);
                        chequearItems();
                        dialog.dismiss();
                    }
                });
                cancelarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.getInstance().keyboard(context, v, true);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        chequearItems();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultados();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResultados();
    }

    private void setResultados() {
        Intent i = new Intent();
        Bundle extras = new Bundle();
        extras.putSerializable(Solicitud.PRODUCTOS, productos);
        i.putExtras(extras);
        setResult(Solicitud.RESULTADO_OK, i);
        finish();
    }

    private void chequearItems() {
        if (productos == null)
            productos = new ArrayList<>();
        if (productos.isEmpty()) {
            listadoProductos.setVisibility(View.GONE);
            vacioTv.setVisibility(View.VISIBLE);
        } else {
            listadoProductos.setVisibility(View.VISIBLE);
            vacioTv.setVisibility(View.GONE);
        }
    }
}
