package cl.inacap.herbalifeproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Usuario;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class RestaurarActivity extends AppCompatActivity {

    TextView usernameTv;
    EditText currentPasswordTxt, passwordTxt, repeatPasswordTxt;
    Button aceptarBtn;

    HerbalifeDAO hdao;
    Usuario usuario;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurar);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {
        usernameTv = findViewById(R.id.restaurar_nombreUsuarioTv);
        currentPasswordTxt = findViewById(R.id.restaurar_contraseñaActualTxt);
        passwordTxt = findViewById(R.id.restaurar_contraseñaNuevaTxt);
        repeatPasswordTxt = findViewById(R.id.restaurar_repetirContraseñaTxt);
        aceptarBtn = findViewById(R.id.restaurar_aceptarBtn);

        hdao = new HerbalifeDAO(context);
        usuario = hdao.buscarUsuario(Preferences.getPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID));
        usernameTv.setText(usuario.getUsername());

        currentPasswordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                aceptarBtn.setEnabled(s.toString().trim().length() > 0 && passwordTxt.getText().toString().trim().length() > 0 && repeatPasswordTxt.getText().toString().trim().length() > 0);
                aceptarBtn.setTextColor(aceptarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
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
                aceptarBtn.setEnabled(s.toString().trim().length() > 0 && currentPasswordTxt.getText().toString().trim().length() > 0 && repeatPasswordTxt.getText().toString().trim().length() > 0);
                aceptarBtn.setTextColor(aceptarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
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
                aceptarBtn.setEnabled(s.toString().trim().length() > 0 && passwordTxt.getText().toString().trim().length() > 0 && currentPasswordTxt.getText().toString().trim().length() > 0);
                aceptarBtn.setTextColor(aceptarBtn.isEnabled() ? Color.WHITE : Color.rgb(111, 111, 111));
            }
        });

        currentPasswordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        aceptarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.getInstance().keyboard(context, v, true);
                String actual = SystemUtils.getInstance().getEncryptedPassword(currentPasswordTxt.getText().toString().trim()),
                        pass = passwordTxt.getText().toString().trim(), rPass = repeatPasswordTxt.getText().toString().trim();
                if (!usuario.getClave().equals(actual))
                    currentPasswordTxt.setError("Contraseña incorrecta");
                else {
                    if (pass.length() < 8)
                        passwordTxt.setError("La contraseña debe tener al menos 8 caracteres");
                    else {
                        if (!pass.equals(rPass))
                            repeatPasswordTxt.setError("Las contraseñas no coinciden");
                        else {
                            usuario.setClave(SystemUtils.getInstance().getEncryptedPassword(pass));
                            if (hdao.modificarUsuario(usuario.getId(), usuario)) {
                                Toast.makeText(context, "¡La contraseña se cambió correctamente!", Toast.LENGTH_SHORT).show();
                                Preferences.setPreferenceBoolean(context, Preferences.TEMP_PREF, Preferences.PASS_ENVIADA, false);
                                startActivity(new Intent(context, LoginActivity.class));
                                finish();
                            } else
                                Toast.makeText(context, "No se pudo cambiar la contraseña.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
