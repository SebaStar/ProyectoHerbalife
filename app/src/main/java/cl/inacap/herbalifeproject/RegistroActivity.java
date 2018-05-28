package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Usuario;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class RegistroActivity extends AppCompatActivity {

    EditText nombreTxt, usernameTxt, passwordTxt, repeatPasswordTxt, emailTxt;
    Button registrarseBtn;

    HerbalifeDAO hdao;
    Usuario usuario, usuarioMail;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        nombreTxt = findViewById(R.id.registro_nombre);
        usernameTxt = findViewById(R.id.registro_username);
        passwordTxt = findViewById(R.id.registro_password);
        repeatPasswordTxt = findViewById(R.id.registro_confirmarPass);
        emailTxt = findViewById(R.id.registro_email);
        registrarseBtn = findViewById(R.id.registro_registrarseBtn);

        hdao = new HerbalifeDAO(context);

        nombreTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarseBtn.setEnabled(s.toString().trim().length() > 0 && usernameTxt.getText().toString().trim().length() > 0 &&
                        passwordTxt.getText().toString().trim().length() > 0 && repeatPasswordTxt.getText().toString().trim().length() > 0 &&
                        emailTxt.getText().toString().trim().length() > 0);
                registrarseBtn.setTextColor(registrarseBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
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
                registrarseBtn.setEnabled(s.toString().trim().length() > 0 && nombreTxt.getText().toString().trim().length() > 0 &&
                        passwordTxt.getText().toString().trim().length() > 0 && repeatPasswordTxt.getText().toString().trim().length() > 0 &&
                        emailTxt.getText().toString().trim().length() > 0);
                registrarseBtn.setTextColor(registrarseBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
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
                registrarseBtn.setEnabled(s.toString().trim().length() > 0 && usernameTxt.getText().toString().trim().length() > 0 &&
                        nombreTxt.getText().toString().trim().length() > 0 && repeatPasswordTxt.getText().toString().trim().length() > 0 &&
                        emailTxt.getText().toString().trim().length() > 0);
                registrarseBtn.setTextColor(registrarseBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        repeatPasswordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarseBtn.setEnabled(s.toString().trim().length() > 0 && usernameTxt.getText().toString().trim().length() > 0 &&
                        passwordTxt.getText().toString().trim().length() > 0 && nombreTxt.getText().toString().trim().length() > 0 &&
                        emailTxt.getText().toString().trim().length() > 0);
                registrarseBtn.setTextColor(registrarseBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });
        emailTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registrarseBtn.setEnabled(s.toString().trim().length() > 0 && usernameTxt.getText().toString().trim().length() > 0 &&
                        passwordTxt.getText().toString().trim().length() > 0 && repeatPasswordTxt.getText().toString().trim().length() > 0 &&
                        nombreTxt.getText().toString().trim().length() > 0);
                registrarseBtn.setTextColor(registrarseBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });

        nombreTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        usernameTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        passwordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        repeatPasswordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });
        emailTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                SystemUtils.getInstance().keyboard(context, v, !hasFocus);
            }
        });

        // REVISAR
        registrarseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().keyboard(context, v, true);
                String pass = passwordTxt.getText().toString().trim(), rPass = repeatPasswordTxt.getText().toString().trim(),
                        username = usernameTxt.getText().toString().trim(), email = emailTxt.getText().toString().trim();

                String errorUsername = null, errorPass = null, errorRepetirPass = null, errorEmail = null;
                usuario = hdao.buscarUsuarioPorUsername(username);
                usuarioMail = hdao.buscarUsuarioPorEmail(email);
                if (usuario == null && usuarioMail == null) {
                    if (pass.length() < 8)
                        errorPass = "La contraseña debe tener al menos 8 caracteres";
                    else {
                        if (!pass.equals(rPass))
                            errorRepetirPass = "Las contraseñas no coinciden";
                        else {
                            if (hdao.agregarUsuario(new Usuario(nombreTxt.getText().toString(), username, email, SystemUtils.getInstance().getEncryptedPassword(pass)))) {
                                Toast.makeText(context, "¡Registrado con éxito!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                                Toast.makeText(context, "No se pudo registrar el usuario al sistema.", Toast.LENGTH_SHORT).show();
                        }
                        errorEmail = SystemUtils.getInstance().isEmailValid(email) ? null : "Formato de email no válido.";
                    }
                } else {
                    errorUsername = usuario != null ? "Este nombre de usuario está registrado" : null;
                    errorEmail = usuarioMail != null ? "Este email está registrado" : null;
                }
                usernameTxt.setError(errorUsername);
                passwordTxt.setError(errorPass);
                repeatPasswordTxt.setError(errorRepetirPass);
                emailTxt.setError(errorEmail);
            }
        });
    }

    @Override
    public void onBackPressed() {
        SystemUtils.getInstance().mostrarDialogo(context).show();
    }
}
