package cl.inacap.herbalifeproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.dto.Seguimiento;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class RESeguimientoActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {

    Toolbar toolbar;
    TextView titleTv, clienteTv, fechaTv, imcTv;
    EditText pesoTxt, grasaTxt, oseaTxt, aguaTxt, muscularTxt, bmrTxt, edadMetabolicaTxt, viceralTxt, cinturaTxt;
    Button registrarEditarBtn;

    HerbalifeDAO hdao;
    Cliente cliente;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseguimiento);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.reseguimiento_toolbar);
        fechaTv = findViewById(R.id.reseguimiento_fecha);
        imcTv = findViewById(R.id.reseguimiento_imc);
        pesoTxt = findViewById(R.id.reseguimiento_peso);
        grasaTxt = findViewById(R.id.reseguimiento_grasa);
        oseaTxt = findViewById(R.id.reseguimiento_osea);
        aguaTxt = findViewById(R.id.reseguimiento_agua);
        muscularTxt = findViewById(R.id.reseguimiento_muscular);
        bmrTxt = findViewById(R.id.reseguimiento_bmr);
        edadMetabolicaTxt = findViewById(R.id.reseguimiento_edad);
        viceralTxt = findViewById(R.id.reseguimiento_viceral);
        cinturaTxt = findViewById(R.id.reseguimiento_cintura);
        registrarEditarBtn = findViewById(R.id.reseguimiento_registrar_editar);
        titleTv = toolbar.findViewById(R.id.toolbar_title);
        clienteTv = toolbar.findViewById(R.id.toolbar_username);

        hdao = new HerbalifeDAO(context);
        final int modo = getIntent().getIntExtra(Solicitud.MODO_ID, 0);
        final int clienteId = getIntent().getIntExtra(Solicitud.CLIENTE_ID, -1);
        cliente = hdao.buscarCliente(clienteId);

        titleTv.setText(modo == 0 ? "Registrar seguimiento" : "Editar seguimiento");
        clienteTv.setText(cliente.getNombre());
        setSupportActionBar(toolbar);

        if (modo == 1) {
            Seguimiento s = hdao.buscarSeguimiento(getIntent().getIntExtra(Solicitud.SEGUIMIENTO_ID, -1));
            fechaTv.setText(s.getFecha());
            pesoTxt.setText(String.valueOf(s.getPeso()));
            grasaTxt.setText(String.valueOf(s.getGrasaTotal()));
            oseaTxt.setText(String.valueOf(s.getOsea()));
            aguaTxt.setText(String.valueOf(s.getAgua()));
            muscularTxt.setText(String.valueOf(s.getMuscular()));
            bmrTxt.setText(String.valueOf(s.getBmr()));
            edadMetabolicaTxt.setText(String.valueOf(s.getEdadMetabolica()));
            viceralTxt.setText(String.valueOf(s.getGrasaViceral()));
            cinturaTxt.setText(String.valueOf(s.getCintura()));
            imcTv.setText(String.valueOf(SystemUtils.getInstance().getImc(Float.parseFloat(pesoTxt.getText().toString()), cliente.getAltura())));
            registrarEditarBtn.setEnabled(true);
            registrarEditarBtn.setTextColor(Color.WHITE);
        }

        registrarEditarBtn.setText(modo == 0 ? "Registrar" : "Editar");
        fechaTv.addTextChangedListener(this);
        imcTv.addTextChangedListener(this);
        pesoTxt.addTextChangedListener(this);
        grasaTxt.addTextChangedListener(this);
        oseaTxt.addTextChangedListener(this);
        aguaTxt.addTextChangedListener(this);
        muscularTxt.addTextChangedListener(this);
        bmrTxt.addTextChangedListener(this);
        edadMetabolicaTxt.addTextChangedListener(this);
        viceralTxt.addTextChangedListener(this);
        cinturaTxt.addTextChangedListener(this);

        pesoTxt.setOnFocusChangeListener(this);
        grasaTxt.setOnFocusChangeListener(this);
        oseaTxt.setOnFocusChangeListener(this);
        aguaTxt.setOnFocusChangeListener(this);
        muscularTxt.setOnFocusChangeListener(this);
        bmrTxt.setOnFocusChangeListener(this);
        edadMetabolicaTxt.setOnFocusChangeListener(this);
        viceralTxt.setOnFocusChangeListener(this);
        cinturaTxt.setOnFocusChangeListener(this);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener calendarDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fechaTv.setText(SystemUtils.getInstance().getTwoDigits(dayOfMonth) + "/" +
                        SystemUtils.getInstance().getTwoDigits(month + 1) + "/" + year);
                pesoTxt.requestFocus();
                SystemUtils.getInstance().keyboard(context);
            }
        };

        fechaTv.requestFocus();
        fechaTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    new DatePickerDialog(context, calendarDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fechaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, calendarDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        registrarEditarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seguimiento seguimiento = new Seguimiento(fechaTv.getText().toString(), Float.parseFloat(pesoTxt.getText().toString()),
                        Float.parseFloat(grasaTxt.getText().toString()), Float.parseFloat(oseaTxt.getText().toString()),
                        Float.parseFloat(aguaTxt.getText().toString()), Float.parseFloat(muscularTxt.getText().toString()),
                        Float.parseFloat(bmrTxt.getText().toString()), Integer.parseInt(edadMetabolicaTxt.getText().toString()),
                        Float.parseFloat(viceralTxt.getText().toString()), Float.parseFloat(cinturaTxt.getText().toString()),
                        clienteId, Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID));
                if (modo == 0) {
                    if (hdao.agregarSeguimiento(seguimiento))
                        Toast.makeText(context, "El seguimiento se agregó correctamente.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "No se pudo agregar el seguimiento.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (hdao.modificarSeguimiento(getIntent().getIntExtra(Solicitud.SEGUIMIENTO_ID, 0), seguimiento))
                        Toast.makeText(context, "El seguimiento se modificó correctamente.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "No se pudo modificar el seguimiento.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        SystemUtils.getInstance().mostrarDialogo(context).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        registrarEditarBtn.setEnabled(!fechaTv.getText().toString().trim().isEmpty() && !imcTv.getText().toString().trim().isEmpty() &&
                !pesoTxt.getText().toString().trim().isEmpty() && !grasaTxt.getText().toString().trim().isEmpty() &&
                !oseaTxt.getText().toString().trim().isEmpty() && !aguaTxt.getText().toString().trim().isEmpty() &&
                !muscularTxt.getText().toString().trim().isEmpty() && !bmrTxt.getText().toString().trim().isEmpty() &&
                !edadMetabolicaTxt.getText().toString().trim().isEmpty() && !viceralTxt.getText().toString().trim().isEmpty() &&
                !cinturaTxt.getText().toString().trim().isEmpty());
        registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String peso = pesoTxt.getText().toString().trim();
        if (!peso.isEmpty())
            imcTv.setText(String.valueOf(SystemUtils.getInstance().getImc(Float.parseFloat(peso), cliente.getAltura())));
        SystemUtils.getInstance().keyboard(context, v, !hasFocus);
    }
}
