package cl.inacap.herbalifeproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.SystemUtils;
import cl.inacap.herbalifeproject.view.ClearableEditText;

public class REClienteActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nombreTxt, telefonoTxt, alturaTxt;
    TextView fechaNacimientoTv, ciudadTv, edadTv, complexionTv, programaNutricionalTv;
    Button registrarEditarBtn;

    HerbalifeDAO hdao;
    String[] complexiones, ciudades;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recliente);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        toolbar = findViewById(R.id.recliente_toolbar);
        nombreTxt = findViewById(R.id.recliente_nombre);
        telefonoTxt = findViewById(R.id.recliente_telefono);
        alturaTxt = findViewById(R.id.recliente_altura);
        fechaNacimientoTv = findViewById(R.id.recliente_fecha_nacimiento);
        ciudadTv = findViewById(R.id.recliente_ciudad);
        edadTv = findViewById(R.id.recliente_edad);
        complexionTv = findViewById(R.id.recliente_complexion);
        programaNutricionalTv = findViewById(R.id.recliente_programa);
        registrarEditarBtn = findViewById(R.id.recliente_registrar_editar);

        hdao = new HerbalifeDAO(context);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar/Editar cliente");

        nombreTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && telefonoTxt.getText().toString().length() > 0 &&
                        alturaTxt.getText().toString().length() > 0 && fechaNacimientoTv.getText().toString().length() > 0 &&
                        ciudadTv.getText().toString().length() > 0 && edadTv.getText().toString().length() > 0 &&
                        complexionTv.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        telefonoTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && nombreTxt.getText().toString().length() > 0 &&
                        alturaTxt.getText().toString().length() > 0 && fechaNacimientoTv.getText().toString().length() > 0 &&
                        ciudadTv.getText().toString().length() > 0 && edadTv.getText().toString().length() > 0 &&
                        complexionTv.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        alturaTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && telefonoTxt.getText().toString().length() > 0 &&
                        nombreTxt.getText().toString().length() > 0 && fechaNacimientoTv.getText().toString().length() > 0 &&
                        ciudadTv.getText().toString().length() > 0 && edadTv.getText().toString().length() > 0 &&
                        complexionTv.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        fechaNacimientoTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && telefonoTxt.getText().toString().length() > 0 &&
                        alturaTxt.getText().toString().length() > 0 && nombreTxt.getText().toString().length() > 0 &&
                        ciudadTv.getText().toString().length() > 0 && edadTv.getText().toString().length() > 0 &&
                        complexionTv.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        ciudadTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && telefonoTxt.getText().toString().length() > 0 &&
                        alturaTxt.getText().toString().length() > 0 && fechaNacimientoTv.getText().toString().length() > 0 &&
                        nombreTxt.getText().toString().length() > 0 && edadTv.getText().toString().length() > 0 &&
                        complexionTv.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        edadTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && telefonoTxt.getText().toString().length() > 0 &&
                        alturaTxt.getText().toString().length() > 0 && fechaNacimientoTv.getText().toString().length() > 0 &&
                        ciudadTv.getText().toString().length() > 0 && nombreTxt.getText().toString().length() > 0 &&
                        complexionTv.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        complexionTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.length() > 0 && telefonoTxt.getText().toString().length() > 0 &&
                        alturaTxt.getText().toString().length() > 0 && fechaNacimientoTv.getText().toString().length() > 0 &&
                        ciudadTv.getText().toString().length() > 0 && edadTv.getText().toString().length() > 0 &&
                        nombreTxt.getText().toString().length() > 0);
                registrarEditarBtn.setTextColor(registrarEditarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });

        telefonoTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        alturaTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener calendarDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fechaNacimientoTv.setText(SystemUtils.getInstance().getTwoDigits(dayOfMonth) + "/" +
                        SystemUtils.getInstance().getTwoDigits(month + 1) + "/" + year);
                edadTv.setText(SystemUtils.getInstance().getEdad(calendar));
                ciudadTv.requestFocus();
            }
        };

        fechaNacimientoTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    new DatePickerDialog(context, calendarDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fechaNacimientoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, calendarDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ciudadTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ciudades = getResources().getStringArray(R.array.ciudades);
                    new AlertDialog.Builder(context).setTitle("Ciudad")
                            .setSingleChoiceItems(ciudades, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ciudadTv.setText(ciudades[which]);
                                    dialog.dismiss();
                                    alturaTxt.requestFocus();
                                    SystemUtils.getInstance().keyboard(context);
                                }
                            }).setCancelable(true).create().show();
                }
            }
        });
        ciudadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ciudades = getResources().getStringArray(R.array.ciudades);
                new AlertDialog.Builder(context).setTitle("Ciudad")
                        .setSingleChoiceItems(ciudades, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ciudadTv.setText(ciudades[which]);
                                dialog.dismiss();
                                alturaTxt.requestFocus();
                                SystemUtils.getInstance().keyboard(context);
                            }
                        }).setCancelable(true).create().show();
            }
        });
        complexionTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    complexiones = getResources().getStringArray(R.array.complexion);
                    new AlertDialog.Builder(context).setTitle("Complexión física")
                            .setSingleChoiceItems(complexiones, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    complexionTv.setText(complexiones[which]);
                                    // programa nutricional
                                    dialog.dismiss();
                                }
                            }).setCancelable(true).create().show();
                }
            }
        });
        complexionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complexiones = getResources().getStringArray(R.array.complexion);
                new AlertDialog.Builder(context).setTitle("Complexión física")
                        .setSingleChoiceItems(complexiones, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                complexionTv.setText(complexiones[which]);
                                // programa nutricional
                                dialog.dismiss();
                            }
                        }).setCancelable(true).create().show();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().mostrarDialogo(context).show();
            }
        });

        registrarEditarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validar opciones
                hdao.agregarCliente(new Cliente(nombreTxt.getText().toString(), telefonoTxt.getText().toString(),
                        fechaNacimientoTv.getText().toString(), 1, Float.parseFloat(alturaTxt.getText().toString()),
                        1, Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID),
                        0));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        SystemUtils.getInstance().mostrarDialogo(context).show();
    }
}
