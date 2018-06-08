package cl.inacap.herbalifeproject.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  clase que realiza la conexion con la base de datos
 * @Author Sebastian
 */

public class Conexion extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "herbalife.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Array de string que contiene sentencias sql para la creacion de la base de datos
     */
    private static final String[] TABLES = new String[] {
            "CREATE TABLE usuario(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, username TEXT NOT NULL, email TEXT NOT NULL, clave TEXT NOT NULL);",
            "CREATE TABLE producto(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre INTEGER NOT NULL, cantidad INTEGER NOT NULL);",
            "CREATE TABLE programa_nutricional(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, duracion INTEGER NOT NULL);",
            "CREATE TABLE programa_nutricional_producto(" +
                    "programa_nutricional_id INTEGER REFERENCES programa_nutricional(id), " +
                    "producto_id INTEGER REFERENCES producto(id));",
            "CREATE TABLE cliente(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, telefono TEXT NOT NULL, fecha_nacimiento TEXT NOT NULL, ciudad INTEGER NOT NULL, " +
                    "altura REAL NOT NULL, complexion INTEGER NOT NULL, " +
                    "usuario_id INTEGER REFERENCES usuario(id), " +
                    "programa_nutricional_id INTEGER REFERENCES programa_nutricional(id));",
            "CREATE TABLE seguimiento(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "fecha TEXT NOT NULL, peso REAL NOT NULL, grasa_total REAL NOT NULL, osea REAL NOT NULL, agua REAL NOT NULL, " +
                    "muscular REAL NOT NULL, bmr REAL NOT NULL, edad_metabolica INTEGER NOT NULL, grasa_viceral REAL NOT NULL, " +
                    "cintura REAL NOT NULL, cliente_id INTEGER REFERENCES cliente(id), " +
                    "usuario_id INTEGER REFERENCES usuario(id));"
    };

    /**
     * Establece la conexion con la base de datos local
     * @param context clase abstracta que contiene parametros del sistema android
     */
    public Conexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * ejecuta sentencias sql definidas anteriormente
     * @param db base de datos local
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            for (String sql : TABLES) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo necesario en caso de nesesitar actualizar la base de datos , su definicion es obligatoria
     * @param db base de datos local
     * @param oldVersion version anterior de la bd
     * @param newVersion version nueva de la bd
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
