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

/**
 * esta clase contiene varios metodos utilizados por las difernetes vistas de la aplicacion
 * @Author camilo
 */

public class SystemUtils {

    private static SystemUtils instance;

    /**
     * implementacion de singleton para retornar la instacia creada
     * @return instancia actual
     */
    public static SystemUtils getInstance() {
        if (instance == null)
            instance = new SystemUtils();
        return instance;
    }

    /**
     *
     * despliega el teclado de manera forzosa al seleccionar un cuadro de texto
     * @param context clase abstracta que contiene parametros del sistema android
     */
    public void keyboard(Context context) {
        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * muestra u oculta el teclado cuando un cuadro te texto pierde el foco
     * @param context clase abstracta que contiene parametros del sistema android
     * @param v control actual
     * @param hide determina si hay que mostrar u ocultar el teclado
     * @return retorna un booleano que muestra o esconde el teclado en base al foco
     */
    public boolean keyboard(Context context, View v, boolean hide) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return hide ? imm.hideSoftInputFromWindow(v.getWindowToken(), 0) :
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    public static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    /**
     * encriptador arteanal
     * @return retorna la contraseña en texto plano
     */
    public String getPassword() {
        char[] pass = new char[] { CARACTERES.charAt(7), CARACTERES.charAt(30), CARACTERES.charAt(43), CARACTERES.charAt(27),
                CARACTERES.charAt(26), CARACTERES.charAt(37), CARACTERES.charAt(34), CARACTERES.charAt(31), CARACTERES.charAt(30),
                CARACTERES.charAt(13), CARACTERES.charAt(46), CARACTERES.charAt(45), CARACTERES.charAt(43), CARACTERES.charAt(34),
                CARACTERES.charAt(45), CARACTERES.charAt(34), CARACTERES.charAt(40), CARACTERES.charAt(39) };
        return String.valueOf(pass);
    }

    /**
     * revisa que exista conexion a intenet
     * @param context clase abstracta que contiene parametros del sistema android
     * @return retorna un booleano indicando si existe conexion a internet
     */
    public boolean hasInternetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     *  se encarga de encriptar la contraseña
     * @param password string que contiene la contraseña sin encriptar
     * @return retorna la contraseña encriptada en md5
     */

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

    /**
     *  obtiene 2 digitos de un numero
     * @param numero contiene el numero que se va a convertir
     * @return retorna 2 digitos del numero inicial
     */
    public String getTwoDigits(int numero) {
        return numero < 10 ? "0" + numero : String.valueOf(numero);
    }

    /**
     *  ontiene la edad en base a la fecha ingresada
     * @param fechaNacimiento contiene la fecha de nacimiento
     * @return retorna la edad
     */

    public String getEdad(Calendar fechaNacimiento) {
        Calendar hoy = Calendar.getInstance();
        int años = hoy.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        if (fechaNacimiento.get(Calendar.MONTH) > hoy.get(Calendar.MONTH) || (fechaNacimiento.get(Calendar.MONTH) == hoy.get(Calendar.MONTH) &&
                fechaNacimiento.get(Calendar.DAY_OF_MONTH) > hoy.get(Calendar.DAY_OF_MONTH)))
            años--;
        return String.valueOf(años);
    }

    /**
     *  crea un dialogo de alerta para evitar perdias de datos
     * @param context clase abstracta que contiene parametros del sistema android
     * @return retorna el dialogo creado
     */

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

    /**
     * revisa que el email ingresado sea valido
     * @param email string que contiene el email
     * @return retorna un booleano indicando si es valido
     */

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

    /**
     * calcula el indice de masa corporal
     * @param peso float en el cual se recibe el peso
     * @param estatura float en el cual se recibe la altura
     * @return retorna el imc
     */

    public float getImc(float peso, float estatura) {
        return peso / (float) Math.pow(estatura, estatura);
    }

    /**
     * cambia el mensaje de saludo en base a la hora del dia en la que se encuentre
     * @return retorna el saludo en base a la hora
     */
    public String getMensajeBienvenida() {
        Calendar ahora = Calendar.getInstance();
        int hora = ahora.get(Calendar.HOUR_OF_DAY);

        if (hora >= 6 && hora < 12)
            return "Buenos días";
        else if (hora >= 12 && hora < 20)
            return "Buenas tardes";
        else
            return "Buenas noches";
    }
}
