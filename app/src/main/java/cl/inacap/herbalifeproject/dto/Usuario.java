package cl.inacap.herbalifeproject.dto;

public class Usuario {

    private int id;
    private String nombre;
    private String username;
    private String email;
    private String clave;

    public Usuario(int id, String nombre, String username, String email, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.clave = clave;
    }

    public Usuario(String nombre, String username, String email, String clave) {
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
