package cl.inacap.herbalifeproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.dto.Producto;
import cl.inacap.herbalifeproject.dto.ProgramaNutricional;
import cl.inacap.herbalifeproject.dto.Seguimiento;
import cl.inacap.herbalifeproject.dto.Usuario;

/**
 * @Author sebastian
 */

public class HerbalifeDAO {

    private Conexion conex;

    /**
     * Constructor de clase
     *
     * @param context
     */
    public HerbalifeDAO(Context context) {
        conex = new Conexion(context);
    }

    // <editor-fold desc="USUARIO">

    /**
     * permite agregar usuarios a la base de datos
     * @param u
     * @return
     */
    public boolean agregarUsuario(Usuario u) {
        ContentValues values = new ContentValues();
        values.put("nombre", u.getNombre());
        values.put("username", u.getUsername());
        values.put("email", u.getEmail());
        values.put("clave", u.getClave());
        return conex.getWritableDatabase().insert("usuario", null, values) != -1;
    }

    /**
     * permite modificar usuarios de la base de datos
     * @param id
     * @param u
     * @return
     */
    public boolean modificarUsuario(int id, Usuario u) {
        ContentValues values = new ContentValues();
        values.put("nombre", u.getNombre());
        values.put("username", u.getUsername());
        values.put("email", u.getEmail());
        values.put("clave", u.getClave());
        return conex.getWritableDatabase().update("usuario", values, "id = " + id, null) != 0;
    }

    /**
     *  realiza una busqueda de un usuario especifico por su ID
     * @param id
     * @return
     */

    public Usuario buscarUsuario(int id) {
        String sql = "SELECT nombre, username, email, clave FROM usuario WHERE id = " + id;
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        return new Usuario(id, cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
    }
    /**
     *  realiza una busqueda de un usuario especifico por su nombre de usuario
     * @param username
     * @return
     */


    public Usuario buscarUsuarioPorUsername(String username) {
        String sql = "SELECT id, nombre, email, clave FROM usuario WHERE username = '" + username + "'";
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        return new Usuario(cursor.getInt(0), cursor.getString(1),
                username, cursor.getString(2), cursor.getString(3));
    }

    /**
     * realiza una busqueda de un usuario especifico por su email
     * @param email
     * @return
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        String sql = "SELECT id, nombre, username, clave FROM usuario WHERE email = '" + email + "'";
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        return new Usuario(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), email, cursor.getString(3));
    }

    /**
     * obtiene la lista de usuarios completa de la base de datos
     * @return
     */
    public List<Usuario> listarUsuarios() {
        String sql = "SELECT id, nombre, username, email, clave FROM usuario";
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        List<Usuario> usuarios = new ArrayList<>();
        while (cursor.moveToNext()) {
            usuarios.add(new Usuario(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
        cursor.close();
        return usuarios;
    }

    // </editor-fold>

    // <editor-fold desc="CLIENTE">

    /**
     * agrega un cliente
     * @param c
     * @return
     */
    public boolean agregarCliente(Cliente c) {
        ContentValues values = new ContentValues();
        values.put("nombre", c.getNombre());
        values.put("telefono", c.getTelefono());
        values.put("fecha_nacimiento", c.getFechaNacimiento());
        values.put("ciudad", c.getCiudad());
        values.put("altura", c.getAltura());
        values.put("complexion", c.getComplexion());
        values.put("usuario_id", c.getUsuarioId());
        values.put("programa_nutricional_id", c.getProgramaNutricionalId());
        return conex.getWritableDatabase().insert("cliente", null, values) != -1;
    }

    public boolean modificarCliente(int id, Cliente c) {
        ContentValues values = new ContentValues();
        values.put("nombre", c.getNombre());
        values.put("telefono", c.getTelefono());
        values.put("fecha_nacimiento", c.getFechaNacimiento());
        values.put("ciudad", c.getCiudad());
        values.put("altura", c.getAltura());
        values.put("complexion", c.getComplexion());
        values.put("usuario_id", c.getUsuarioId());
        values.put("programa_nutricional_id", c.getProgramaNutricionalId());
        return conex.getWritableDatabase().update("cliente", values, "id = " + id, null) != 0;
    }

    public Cliente buscarCliente(int id) {
        String sql = "SELECT nombre, telefono, fecha_nacimiento, ciudad, altura, complexion, usuario_id, programa_nutricional_id " +
                "FROM cliente WHERE id = " + id;
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        return new Cliente(id, cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3),
                cursor.getFloat(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
    }

    public List<Cliente> listarClientes(int usuarioId) {
        String sql = "SELECT id, nombre, telefono, fecha_nacimiento, ciudad, altura, complexion, programa_nutricional_id " +
                "FROM cliente WHERE usuario_id = " + usuarioId;
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        List<Cliente> clientes = new ArrayList<>();
        while (cursor.moveToNext()) {
            clientes.add(new Cliente(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getInt(4), cursor.getFloat(5), cursor.getInt(6), usuarioId, cursor.getInt(7)));
        }
        cursor.close();
        return clientes;
    }

    public boolean eliminarCliente(int id) {
        return conex.getWritableDatabase().delete("cliente", "id = " + id, null) != 0;
    }
    // </editor-fold>

    // <editor-fold desc="PRODUCTO">
    public boolean agregarProducto(Producto p) {
        ContentValues values = new ContentValues();
        values.put("nombre", p.getNombre());
        values.put("cantidad", p.getCantidad());
        return conex.getWritableDatabase().insert("producto", null, values) != -1;
    }

    public boolean eliminarProducto(int id) {
        return conex.getWritableDatabase().delete("producto", "id = " + id, null) != 0;
    }
    // </editor-fold>

    // <editor-fold desc="PROGRAMA NUTRICIONAL">
    public boolean agregarProgramaNutricional(ProgramaNutricional pn) {
        ContentValues values = new ContentValues();
        values.put("nombre", pn.getNombre());
        values.put("duracion", pn.getDuracion());
        return conex.getWritableDatabase().insert("programa_nutricional", null, values) != -1;
    }

    public boolean modificarProgramaNutricional(int id, ProgramaNutricional pn) {
        ContentValues values = new ContentValues();
        values.put("nombre", pn.getNombre());
        values.put("duracion", pn.getDuracion());
        return conex.getWritableDatabase().update("programa_nutricional", values, "id = " + id, null) != 0;
    }

    public ProgramaNutricional buscarProgramaNutricional(int id) {
        String sql = "SELECT nombre, duracion FROM programa_nutricional WHERE id = " + id;
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        return new ProgramaNutricional(id, cursor.getString(0), cursor.getInt(1));
    }

    public List<ProgramaNutricional> listarProgramasNutricionales() {
        String sql = "SELECT id, nombre, duracion FROM programa_nutricional";
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        List<ProgramaNutricional> programas = new ArrayList<>();
        while (cursor.moveToNext()) {
            programas.add(new ProgramaNutricional(cursor.getInt(0),
                    cursor.getString(1), cursor.getInt(2)));
        }
        cursor.close();
        return programas;
    }

    public int getUltimoIdDeProgramaNutricional() {
        String sql = "SELECT max(id) FROM programa_nutricional";
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return 0;
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    public boolean eliminarProgramaNutricional(int id) {
        return conex.getWritableDatabase().delete("programa_nutricional", "id = " + id, null) != 0;
    }
    // </editor-fold>

    // <editor-fold desc="PROGRAMA NUTRICIONAL / PRODUCTO">
    public boolean agregarPnProducto(int pnId, int productoId) {
        ContentValues values = new ContentValues();
        values.put("programa_nutricional_id", pnId);
        values.put("producto_id", productoId);
        return conex.getWritableDatabase().insert("programa_nutricional_producto", null, values) != -1;
    }

    public List<Producto> listarProductos(int pnId) {
        String sql = "SELECT p.id, p.nombre, p.cantidad, pnp.programa_nutricional_id, pnp.producto_id " +
                "FROM programa_nutricional_producto pnp, producto p WHERE pnp.programa_nutricional_id = " + pnId + " AND p.id = pnp.producto_id";
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        List<Producto> productos = new ArrayList<>();
        while (cursor.moveToNext()) {
            productos.add(new Producto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2)));
        }
        cursor.close();
        return productos;
    }

    public boolean eliminarPnProducto(int pnId, int productoId) {
        return conex.getWritableDatabase().delete("programa_nutricional_producto", "programa_nutricional_id = " + pnId +
                " AND producto_id = " + productoId, null) != 0;
    }
    // </editor-fold>

    // <editor-fold desc="SEGUIMIENTO">
    public boolean agregarSeguimiento(Seguimiento s) {
        ContentValues values = new ContentValues();
        values.put("fecha", s.getFecha());
        values.put("peso", s.getPeso());
        values.put("grasa_total", s.getGrasaTotal());
        values.put("osea", s.getOsea());
        values.put("agua", s.getAgua());
        values.put("muscular", s.getMuscular());
        values.put("brm", s.getBrm());
        values.put("edad_metabolica", s.getEdadMetabolica());
        values.put("grasa_viceral", s.getGrasaViceral());
        values.put("cintura", s.getCintura());
        values.put("cliente_id", s.getClienteId());
        values.put("usuario_id", s.getUsuarioId());
        return conex.getWritableDatabase().insert("seguimiento", null, values) != -1;
    }

    public boolean modificarSeguimiento(int id, Seguimiento s) {
        ContentValues values = new ContentValues();
        values.put("fecha", s.getFecha());
        values.put("peso", s.getPeso());
        values.put("grasa_total", s.getGrasaTotal());
        values.put("osea", s.getOsea());
        values.put("agua", s.getAgua());
        values.put("muscular", s.getMuscular());
        values.put("brm", s.getBrm());
        values.put("edad_metabolica", s.getEdadMetabolica());
        values.put("grasa_viceral", s.getGrasaViceral());
        values.put("cintura", s.getCintura());
        values.put("cliente_id", s.getClienteId());
        values.put("usuario_id", s.getUsuarioId());
        return conex.getWritableDatabase().update("seguimiento", values, "id = " + id, null) != 0;
    }

    public Seguimiento buscarSeguimiento(int id) {
        String sql = "SELECT fecha, peso, grasa_total, osea, agua, muscular, brm, edad_metabolica, grasa_viceral, cintura, cliente_id, usuario_id " +
                "FROM seguimiento WHERE id = " + id;
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        return new Seguimiento(cursor.getString(0), cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3),
                cursor.getFloat(4), cursor.getFloat(5), cursor.getFloat(6), cursor.getInt(7), cursor.getFloat(8),
                cursor.getFloat(9), cursor.getInt(10), cursor.getInt(11));
    }

    public List<Seguimiento> listarSeguimientos(int clienteId, int usuarioId) {
        String sql = "SELECT id, fecha, peso, grasa_total, osea, agua, muscular, brm, edad_metabolica, grasa_viceral, cintura " +
                "FROM seguimiento WHERE cliente_id = " + clienteId + " AND usuario_id = " + usuarioId;
        Cursor cursor = conex.getReadableDatabase().rawQuery(sql, null);
        List<Seguimiento> seguimientos = new ArrayList<>();
        while (cursor.moveToNext()) {
            seguimientos.add(new Seguimiento(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2),
                    cursor.getFloat(3), cursor.getFloat(4), cursor.getFloat(5), cursor.getFloat(6),
                    cursor.getFloat(7), cursor.getInt(8), cursor.getFloat(9), cursor.getFloat(10), clienteId, usuarioId));
        }
        cursor.close();
        return seguimientos;
    }

    public boolean eliminarSeguimiento(int id) {
        return conex.getWritableDatabase().delete("seguimiento", "id = " + id, null) != 0;
    }
    // </editor-fold>
}
