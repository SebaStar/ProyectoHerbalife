package cl.inacap.herbalifeproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Usuario;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class RegistroActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {

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

        nombreTxt.addTextChangedListener(this);
        usernameTxt.addTextChangedListener(this);
        passwordTxt.addTextChangedListener(this);
        repeatPasswordTxt.addTextChangedListener(this);
        emailTxt.addTextChangedListener(this);

        nombreTxt.setOnFocusChangeListener(this);
        usernameTxt.setOnFocusChangeListener(this);
        passwordTxt.setOnFocusChangeListener(this);
        repeatPasswordTxt.setOnFocusChangeListener(this);
        emailTxt.setOnFocusChangeListener(this);

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
                        errorPass = "La contraseña debe tener al menos 8 caracteres.";
                    else {
                        if (!pass.equals(rPass) || !SystemUtils.getInstance().isEmailValid(email)) {
                            errorRepetirPass = pass.equals(rPass) ? null : "Las contraseñas no coinciden.";
                            errorEmail = SystemUtils.getInstance().isEmailValid(email) ? null : "Formato de email no válido.";
                        }
                        else {
                            if (hdao.agregarUsuario(new Usuario(nombreTxt.getText().toString(), username, email, SystemUtils.getInstance().getEncryptedPassword(pass))))
                                Toast.makeText(context, "¡Registrado con éxito!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context, "No se pudo registrar el usuario al sistema.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        registrarseBtn.setEnabled(!nombreTxt.getText().toString().trim().isEmpty() && !usernameTxt.getText().toString().trim().isEmpty() &&
                !passwordTxt.getText().toString().trim().isEmpty() && !repeatPasswordTxt.getText().toString().trim().isEmpty() &&
                !emailTxt.getText().toString().trim().isEmpty());
        registrarseBtn.setTextColor(registrarseBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        SystemUtils.getInstance().keyboard(context, v, !hasFocus);
    }
}
