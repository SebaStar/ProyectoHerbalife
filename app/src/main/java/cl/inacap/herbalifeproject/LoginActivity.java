package cl.inacap.herbalifeproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Usuario;
import cl.inacap.herbalifeproject.tasks.SendMailTask;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class LoginActivity extends AppCompatActivity {

    EditText usernameTxt, passwordTxt;
    Button loginBtn, registerBtn, forgotBtn;
    CheckBox noLogoutCbx;

    HerbalifeDAO hdao;
    Usuario usuario;

    Random r;
    Context context;
    String contraseñaGenerada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        if (Preferences.getPreferenceBoolean(context, Preferences.MAIN_PREF, Preferences.NO_CERRAR_SESION)) {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }
        if (Preferences.getPreferenceBoolean(context, Preferences.TEMP_PREF, Preferences.PASS_ENVIADA)) {
            startActivity(new Intent(context, RestaurarActivity.class));
            finish();
        }
        initializeComponent();
    }

    private void initializeComponent() {
        usernameTxt = findViewById(R.id.login_nombreUsuarioTxt);
        passwordTxt = findViewById(R.id.login_contraseñaTxt);
        loginBtn = findViewById(R.id.login_iniciarSesionBtn);
        registerBtn = findViewById(R.id.login_registrarBtn);
        noLogoutCbx = findViewById(R.id.login_noCerrarSesionCbx);
        forgotBtn = findViewById(R.id.login_olvidarBtn);

        hdao = new HerbalifeDAO(context);
        r = new Random();

        usernameTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        usernameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginBtn.setEnabled(s.length() > 0 && passwordTxt.getText().toString().trim().length() > 0);
                loginBtn.setTextColor(loginBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        passwordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        passwordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginBtn.setEnabled(s.length() > 0 && usernameTxt.getText().toString().trim().length() > 0);
                loginBtn.setTextColor(loginBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().keyboard(context, v, true);
                usuario = hdao.buscarUsuarioPorUsername(usernameTxt.getText().toString().trim());
                if (usuario == null)
                    usernameTxt.setError("El nombre de usuario NO existe");
                else {
                    String contraseñaEncriptada = SystemUtils.getInstance().getEncryptedPassword(passwordTxt.getText().toString().trim());
                    if (usuario.getClave().equals(contraseñaEncriptada)) {
                        Preferences.setPreferenceBoolean(context, Preferences.MAIN_PREF, Preferences.NO_CERRAR_SESION, noLogoutCbx.isChecked());
                        Preferences.setPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID, usuario.getId());
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    }
                    else
                        passwordTxt.setError("Contraseña incorrecta");
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().keyboard(context, v, true);
                startActivity(new Intent(context, RegistroActivity.class));
            }
        });
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().keyboard(context, v, true);
                contraseñaGenerada = "";
                for (int i = 0; i < 8; i++)
                    contraseñaGenerada += SystemUtils.CARACTERES.charAt(r.nextInt(SystemUtils.CARACTERES.length()));

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_enviar_mail);
                dialog.setCancelable(false);

                final EditText emailParaTxt = dialog.findViewById(R.id.dem_emailTxt);
                final Button aceptarBtn, cancelarBtn;
                aceptarBtn = dialog.findViewById(R.id.dem_aceptarBtn);
                cancelarBtn = dialog.findViewById(R.id.dem_cancelarBtn);

                emailParaTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        SystemUtils.getInstance().keyboard(context, v, !hasFocus);
                    }
                });
                emailParaTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        aceptarBtn.setEnabled(SystemUtils.getInstance().isEmailValid(emailParaTxt.getText().toString().trim()));
                    }
                });
                aceptarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.getInstance().keyboard(context, v, true);
                        if (SystemUtils.getInstance().hasInternetConnection(context)) {
                            usuario = hdao.buscarUsuarioPorEmail(emailParaTxt.getText().toString().trim());
                            if (usuario == null) {
                                new AlertDialog.Builder(context).setTitle("Email desconocido").setCancelable(false)
                                        .setMessage("El email ingresado no pertenece a ningún usuario registrado en el sistema.")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();
                            } else {
                                List enviarA = Arrays.asList(new String[]{emailParaTxt.getText().toString().trim()});
                                new SendMailTask(context, new Object[]{usuario, SystemUtils.getInstance().getEncryptedPassword(contraseñaGenerada), hdao})
                                        .execute("bot.herbalife2018@gmail.com", SystemUtils.getInstance().getPassword(), enviarA,
                                                "Recuperar contraseña", "Su nueva contraseña para Herbalife es: " + contraseñaGenerada);
                            }
                        } else
                            Toast.makeText(context, "¡No tienes conexión a Internet!", Toast.LENGTH_SHORT).show();
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
    }
}
