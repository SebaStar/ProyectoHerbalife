package cl.inacap.herbalifeproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String MAIN_PREF = "herbalife_pref_data";
    public static final String TEMP_PREF = "temp_data";

    public static final String NO_CERRAR_SESION = "no_cerrar_sesion";
    public static final String USUARIO_ID = "usuario_id";

    public static final String PASS_ENVIADA = "password_enviada";

    public static boolean getPreferenceBoolean(Context context, String pref, String key) {
        SharedPreferences preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void setPreferenceBoolean(Context context, String pref, String key, boolean b) {
        SharedPreferences preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, b).apply();
    }

    public static String getPreferenceString(Context context, String pref, String key) {
        SharedPreferences preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setPreferenceString(Context context, String pref, String key, String s) {
        SharedPreferences preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        preferences.edit().putString(key, s).apply();
    }

    public static int getPreferenceInt(Context context, String pref, String key) {
        SharedPreferences preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        return preferences.getInt(key, -1);
    }

    public static void setPreferenceInt(Context context, String pref, String key, int i) {
        SharedPreferences preferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, i).apply();
    }
}
