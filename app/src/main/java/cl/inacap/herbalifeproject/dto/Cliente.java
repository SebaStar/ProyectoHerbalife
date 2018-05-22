package cl.inacap.herbalifeproject.dto;

public class Cliente {

    private int id;
    private String nombre;
    private String telefono;
    private String fechaNacimiento;
    private int ciudad;
    private float altura;
    private int complexion;
    private int usuarioId;
    private int programaNutricionalId;

    public Cliente(int id, String nombre, String telefono, String fechaNacimiento, int ciudad, float altura, int complexion, int usuarioId, int programaNutricionalId) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.altura = altura;
        this.complexion = complexion;
        this.usuarioId = usuarioId;
        this.programaNutricionalId = programaNutricionalId;
    }

    public Cliente(String nombre, String telefono, String fechaNacimiento, int ciudad, float altura, int complexion, int usuarioId, int programaNutricionalId) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
        this.altura = altura;
        this.complexion = complexion;
        this.usuarioId = usuarioId;
        this.programaNutricionalId = programaNutricionalId;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getCiudad() {
        return ciudad;
    }

    public float getAltura() {
        return altura;
    }

    public int getComplexion() {
        return complexion;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getProgramaNutricionalId() {
        return programaNutricionalId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
