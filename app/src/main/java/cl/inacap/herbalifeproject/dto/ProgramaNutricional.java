package cl.inacap.herbalifeproject.dto;

public class ProgramaNutricional {

    private int id;
    private String nombre;
    private int duracion;

    public ProgramaNutricional(int id, String nombre, int duracion) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public ProgramaNutricional(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }
}
