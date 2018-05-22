package cl.inacap.herbalifeproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUtils {

    private static SystemUtils instance;

    public static SystemUtils getInstance() {
        if (instance == null)
            instance = new SystemUtils();
        return instance;
    }

    public void keyboard(Context context) {
        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
    }

    public boolean keyboard(Context context, View v, boolean hide) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return hide ? imm.hideSoftInputFromWindow(v.getWindowToken(), 0) :
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    public static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public String getPassword() {
        char[] pass = new char[] { CARACTERES.charAt(7), CARACTERES.charAt(30), CARACTERES.charAt(43), CARACTERES.charAt(27),
                CARACTERES.charAt(26), CARACTERES.charAt(37), CARACTERES.charAt(34), CARACTERES.charAt(31), CARACTERES.charAt(30),
                CARACTERES.charAt(13), CARACTERES.charAt(46), CARACTERES.charAt(45), CARACTERES.charAt(43), CARACTERES.charAt(34),
                CARACTERES.charAt(45), CARACTERES.charAt(34), CARACTERES.charAt(40), CARACTERES.charAt(39) };
        return String.valueOf(pass);
    }

    public boolean hasInternetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public String getEncryptedPassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] bytes = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }

    public String getTwoDigits(int numero) {
        return numero < 10 ? "0" + numero : String.valueOf(numero);
    }

    public String getEdad(Calendar fechaNacimiento) {
        Calendar hoy = Calendar.getInstance();
        int años = hoy.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        if (fechaNacimiento.get(Calendar.MONTH) > hoy.get(Calendar.MONTH) || (fechaNacimiento.get(Calendar.MONTH) == hoy.get(Calendar.MONTH) &&
                fechaNacimiento.get(Calendar.DAY_OF_MONTH) > hoy.get(Calendar.DAY_OF_MONTH)))
            años--;
        return String.valueOf(años);
    }

    public AlertDialog mostrarDialogo(final Context context) {
        final AlertDialog dialogo = new AlertDialog.Builder(context)
                .setTitle("Salir").setMessage("¿Estás seguro de que quieres salir sin guardar?").setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogo.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.rgb(255, 68, 68));
            }
        });
        return dialogo;
    }

    public boolean isEmailValid(String email) {
        /*
            ^ Indica el principio de una cadena
            $ Indica el final de una cadena
            () Un agrupamiento de parte de una expresión
            [] Un conjunto de caracteres de la expresión
            {} Indica un número o intervalo de longitud de la expresión
            . Cualquier caracter salvo el salto de línea
            ? 0-1 ocurrencias de la expresión
            + 1-n ocurrencias de la expresión
            * 0-n ocurrencias de la expresión
            \ Para escribir un caracter especial como los anteriores y que sea tratado como un literal
            | Para indicar una disyunción lógica (para elegir entre dos valores: a|b se tiene que cumplir al menos uno de los dos)
         */
        Pattern patron = Pattern.compile("^[_a-z0-9-]+(-[_a-z0-9])*@[a-z]+(\\.[a-z]{2,})$");
        return patron.matcher(email).find();
    }
}
