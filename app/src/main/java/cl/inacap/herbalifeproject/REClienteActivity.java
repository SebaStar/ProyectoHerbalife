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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.dto.ProgramaNutricional;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;
import cl.inacap.herbalifeproject.view.ClearableEditText;

public class REClienteActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText nombreTxt, telefonoTxt, alturaTxt;
    TextView fechaNacimientoTv, ciudadTv, edadTv, complexionTv, programaNutricionalTv;
    Button registrarEditarBtn;

    HerbalifeDAO hdao;
    List<ProgramaNutricional> programasNutricionales = new ArrayList<>();
    String[] complexiones, ciudades, programas;

    Context context;
    int ciudad = -1,complexion = -1, programaNutricional = -1;

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
        final int modo = getIntent().getIntExtra(Solicitud.MODO_ID, 0);
        final int usuarioId = Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(modo == 0 ? "Registrar cliente" : "Editar cliente");

        ciudades = getResources().getStringArray(R.array.ciudades);
        complexiones = getResources().getStringArray(R.array.complexion);
        programasNutricionales = hdao.listarProgramasNutricionales();

        if (!programasNutricionales.isEmpty()) {
            programas = new String[programasNutricionales.size()];
            for (int i = 0; i < programasNutricionales.size(); i++)
                programas[i] = programasNutricionales.get(i).getNombre();
        }

        if (modo == 1) {
            Cliente c = hdao.buscarCliente(getIntent().getIntExtra(Solicitud.CLIENTE_ID, 0));
            ciudad = c.getCiudad();
            complexion = c.getComplexion();
            programaNutricional = c.getProgramaNutricionalId() - 1;

            nombreTxt.setText(c.getNombre());
            telefonoTxt.setText(c.getTelefono());
            alturaTxt.setText(String.valueOf(c.getAltura()));
            fechaNacimientoTv.setText(c.getFechaNacimiento());
            ciudadTv.setText(ciudades[ciudad]);
            Calendar calEdad = Calendar.getInstance();
            String[] fechaSplit = c.getFechaNacimiento().split("/");
            calEdad.set(Integer.parseInt(fechaSplit[2]), Integer.parseInt(fechaSplit[1]), Integer.parseInt(fechaSplit[0]));
            edadTv.setText(SystemUtils.getInstance().getEdad(calEdad));
            complexionTv.setText(complexiones[complexion]);
            programaNutricionalTv.setText(programasNutricionales.get(programaNutricional + 1).getNombre());
        }

        registrarEditarBtn.setText(modo == 0 ? "Registrar" : "Editar");
        nombreTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && telefonoTxt.getText().toString().trim().length() > 0 &&
                        alturaTxt.getText().toString().trim().length() > 0 && fechaNacimientoTv.getText().toString().trim().length() > 0 &&
                        ciudadTv.getText().toString().trim().length() > 0 && edadTv.getText().toString().trim().length() > 0 &&
                        complexionTv.getText().toString().trim().length() > 0);
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
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && nombreTxt.getText().toString().trim().length() > 0 &&
                        alturaTxt.getText().toString().trim().length() > 0 && fechaNacimientoTv.getText().toString().trim().length() > 0 &&
                        ciudadTv.getText().toString().trim().length() > 0 && edadTv.getText().toString().trim().length() > 0 &&
                        complexionTv.getText().toString().trim().length() > 0);
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
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && telefonoTxt.getText().toString().trim().length() > 0 &&
                        nombreTxt.getText().toString().trim().length() > 0 && fechaNacimientoTv.getText().toString().trim().length() > 0 &&
                        ciudadTv.getText().toString().trim().length() > 0 && edadTv.getText().toString().trim().length() > 0 &&
                        complexionTv.getText().toString().trim().length() > 0);
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
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && telefonoTxt.getText().toString().trim().length() > 0 &&
                        alturaTxt.getText().toString().trim().length() > 0 && nombreTxt.getText().toString().trim().length() > 0 &&
                        ciudadTv.getText().toString().trim().length() > 0 && edadTv.getText().toString().trim().length() > 0 &&
                        complexionTv.getText().toString().trim().length() > 0);
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
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && telefonoTxt.getText().toString().trim().length() > 0 &&
                        alturaTxt.getText().toString().trim().length() > 0 && fechaNacimientoTv.getText().toString().trim().length() > 0 &&
                        nombreTxt.getText().toString().trim().length() > 0 && edadTv.getText().toString().trim().length() > 0 &&
                        complexionTv.getText().toString().trim().length() > 0);
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
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && telefonoTxt.getText().toString().trim().length() > 0 &&
                        alturaTxt.getText().toString().trim().length() > 0 && fechaNacimientoTv.getText().toString().trim().length() > 0 &&
                        ciudadTv.getText().toString().trim().length() > 0 && nombreTxt.getText().toString().trim().length() > 0 &&
                        complexionTv.getText().toString().trim().length() > 0);
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
                registrarEditarBtn.setEnabled(s.toString().trim().length() > 0 && telefonoTxt.getText().toString().trim().length() > 0 &&
                        alturaTxt.getText().toString().trim().length() > 0 && fechaNacimientoTv.getText().toString().trim().length() > 0 &&
                        ciudadTv.getText().toString().trim().length() > 0 && edadTv.getText().toString().trim().length() > 0 &&
                        nombreTxt.getText().toString().trim().length() > 0);
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
                    new AlertDialog.Builder(context).setTitle("Ciudad")
                            .setSingleChoiceItems(ciudades, ciudad, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ciudad = which;
                                    ciudadTv.setText(ciudades[ciudad]);
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
                new AlertDialog.Builder(context).setTitle("Ciudad")
                        .setSingleChoiceItems(ciudades, ciudad, new DialogInterface.OnClickListener() {
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
                    new AlertDialog.Builder(context).setTitle("Complexión física")
                            .setSingleChoiceItems(complexiones, complexion, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    complexion = which;
                                    complexionTv.setText(complexiones[complexion]);
                                    programaNutricionalTv.requestFocus();
                                    dialog.dismiss();
                                }
                            }).setCancelable(true).create().show();
                }
            }
        });
        complexionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Complexión física")
                        .setSingleChoiceItems(complexiones, complexion, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                complexion = which;
                                complexionTv.setText(complexiones[complexion]);
                                programaNutricionalTv.requestFocus();
                                dialog.dismiss();
                            }
                        }).setCancelable(true).create().show();
            }
        });
        programaNutricionalTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new AlertDialog.Builder(context).setTitle("Programa nutricional")
                            .setSingleChoiceItems(programas, programaNutricional, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    programaNutricional = which;
                                    programaNutricionalTv.setText(programas[programaNutricional]);
                                    dialog.dismiss();
                                }
                            }).setCancelable(true).create().show();
                }
            }
        });
        programaNutricionalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Programa nutricional")
                        .setSingleChoiceItems(programas, programaNutricional, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                programaNutricional = which;
                                programaNutricionalTv.setText(programas[programaNutricional]);
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
                if (modo == 0) {
                    if (hdao.agregarCliente(new Cliente(nombreTxt.getText().toString(), telefonoTxt.getText().toString(),
                            fechaNacimientoTv.getText().toString(), ciudad, Float.parseFloat(alturaTxt.getText().toString()),
                            complexion, usuarioId, programaNutricional + 1)))
                        Toast.makeText(context, "El cliente se registró correctamente.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "No se pudo registrar el cliente.", Toast.LENGTH_SHORT).show();
                } else {
                    if (hdao.modificarCliente(getIntent().getIntExtra(Solicitud.CLIENTE_ID, 0), new Cliente(nombreTxt.getText().toString(),
                            telefonoTxt.getText().toString(), fechaNacimientoTv.getText().toString(), ciudad, Float.parseFloat(alturaTxt.getText().toString()),
                            complexion, usuarioId, programaNutricional + 1)))
                        Toast.makeText(context, "El cliente se modificó correctamente.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "No se pudo modificar el cliente.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        SystemUtils.getInstance().mostrarDialogo(context).show();
    }
}
