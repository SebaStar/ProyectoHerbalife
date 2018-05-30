package cl.inacap.herbalifeproject.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "herbalife.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Array de string que contiene sentencias sql para la creacion de la base de datos
     */
    private static final String[] TABLES = new String[] {
            "CREATE TABLE usuario(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, username TEXT, email TEXT, clave TEXT);",
            "CREATE TABLE producto(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre INTEGER, cantidad INTEGER);",
            "CREATE TABLE programa_nutricional(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, duracion INTEGER);",
            "CREATE TABLE programa_nutricional_producto(" +
                    "programa_nutricional_id INTEGER REFERENCES programa_nutricional(id), " +
                    "producto_id INTEGER REFERENCES producto(id));",
            "CREATE TABLE cliente(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, telefono TEXT, fecha_nacimiento TEXT, ciudad INTEGER, " +
                    "altura REAL, complexion INTEGER, " +
                    "usuario_id INTEGER REFERENCES usuario(id), " +
                    "programa_nutricional_id INTEGER REFERENCES programa_nutricional(id));",
            "CREATE TABLE seguimiento(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "fecha TEXT, peso REAL, grasa_total REAL, osea REAL, agua REAL, " +
                    "muscular REAL, brm REAL, edad_metabolica INTEGER, grasa_viceral REAL, " +
                    "cintura REAL, cliente_id INTEGER REFERENCES cliente(id), " +
                    "usuario_id INTEGER REFERENCES usuario(id));"
    };

    public Conexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
