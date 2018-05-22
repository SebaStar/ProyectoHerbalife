package cl.inacap.herbalifeproject.dto;

public class Producto implements java.io.Serializable {

    private int id;
    private int nombre;
    private int cantidad;

    public Producto(int id, int nombre, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Producto(int nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public int getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}
