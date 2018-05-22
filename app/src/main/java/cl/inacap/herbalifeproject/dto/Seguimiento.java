package cl.inacap.herbalifeproject.dto;

public class Seguimiento {

    private int id;
    private String fecha;
    private float peso;
    private float grasaTotal;
    private float osea;
    private float agua;
    private float muscular;
    private float brm;
    private int edadMetabolica;
    private float grasaViceral;
    private float cintura;
    private int clienteId;
    private int usuarioId;

    public Seguimiento(int id, String fecha, float peso, float grasaTotal, float osea, float agua, float muscular, float brm, int edadMetabolica, float grasaViceral, float cintura, int clienteId, int usuarioId) {
        this.id = id;
        this.fecha = fecha;
        this.peso = peso;
        this.grasaTotal = grasaTotal;
        this.osea = osea;
        this.agua = agua;
        this.muscular = muscular;
        this.brm = brm;
        this.edadMetabolica = edadMetabolica;
        this.grasaViceral = grasaViceral;
        this.cintura = cintura;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
    }

    public Seguimiento(String fecha, float peso, float grasaTotal, float osea, float agua, float muscular, float brm, int edadMetabolica, float grasaViceral, float cintura, int clienteId, int usuarioId) {
        this.fecha = fecha;
        this.peso = peso;
        this.grasaTotal = grasaTotal;
        this.osea = osea;
        this.agua = agua;
        this.muscular = muscular;
        this.brm = brm;
        this.edadMetabolica = edadMetabolica;
        this.grasaViceral = grasaViceral;
        this.cintura = cintura;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public float getPeso() {
        return peso;
    }

    public float getGrasaTotal() {
        return grasaTotal;
    }

    public float getOsea() {
        return osea;
    }

    public float getAgua() {
        return agua;
    }

    public float getMuscular() {
        return muscular;
    }

    public float getBrm() {
        return brm;
    }

    public int getEdadMetabolica() {
        return edadMetabolica;
    }

    public float getGrasaViceral() {
        return grasaViceral;
    }

    public float getCintura() {
        return cintura;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}
