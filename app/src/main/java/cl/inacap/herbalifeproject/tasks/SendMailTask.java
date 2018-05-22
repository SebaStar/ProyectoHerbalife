package cl.inacap.herbalifeproject.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import cl.inacap.herbalifeproject.RestaurarActivity;
import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Usuario;
import cl.inacap.herbalifeproject.service.GMailService;
import cl.inacap.herbalifeproject.utils.Preferences;

public class SendMailTask extends AsyncTask<Object, Object, Object> {

    private ProgressDialog dialog;
    private Context context;
    private Object[] args;
    private final String MESSAGE_FAILED = "No se pudo enviar el email. Inténtelo de nuevo.";

    public SendMailTask(Context context, Object[] args) {
        this.context = context;
        this.args = args;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Enviando mail...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            publishProgress("Procesando los datos...");
            GMailService service = new GMailService(objects[0].toString(),
                    objects[1].toString(), (List)objects[2], objects[3].toString(), objects[4].toString());
            publishProgress("Preparando email...");
            service.createEmailMessage();
            publishProgress("Enviando email...");
            service.sendEmail();
            publishProgress("Email enviado.");
        } catch (Exception e) {
            e.printStackTrace();
            return MESSAGE_FAILED;
        }
        return "Email enviado a " + ((List)objects[2]).get(0);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        dialog.setMessage(values[0].toString());
    }

    @Override
    protected void onPostExecute(Object o) {
        dialog.dismiss();
        if (!o.equals(MESSAGE_FAILED)) {
            Usuario usuario = (Usuario)args[0];
            usuario.setClave((String)args[1]);
            ((HerbalifeDAO)args[2]).modificarUsuario(usuario.getId(), usuario);
            Preferences.setPreferenceInt(context, Preferences.MAIN_PREF, Preferences.USUARIO_ID, usuario.getId());
            Preferences.setPreferenceBoolean(context, Preferences.TEMP_PREF, Preferences.PASS_ENVIADA, true);
            context.startActivity(new Intent(context, RestaurarActivity.class));
            ((Activity)context).finish();
        }
        Toast.makeText(context, o.toString(), Toast.LENGTH_SHORT).show();
    }
}
