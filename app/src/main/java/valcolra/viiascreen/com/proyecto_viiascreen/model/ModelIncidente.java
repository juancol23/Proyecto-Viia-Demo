package valcolra.viiascreen.com.proyecto_viiascreen.model;

/**
 * Created by Vic on 25/09/2017.
 */
public class ModelIncidente {
    // Atributos
    private String imagen;
    private String imagen2;
    private String Punto;
    private String fecha;
    private String usuario;
    private String estado;
    private String servicio;
    private String observacion;
    private String Id;
    private String area;
    private String responsable;
    private String cierre;


    public ModelIncidente() {
    }

    public ModelIncidente(String punto, String fecha, String usuario, String imagen) {
        Punto = punto;
        this.fecha = fecha;
        this.usuario = usuario;
        this.imagen = imagen;
    }
    /*Constructor para el adapter*/
    public ModelIncidente(String imagen, String imagen2, String punto, String fecha, String usuario, String estado, String  Id, String  observacion, String  area, String  responsable, String  cierre) {
        this.imagen = imagen;
        this.imagen2 = imagen2;
        Punto = punto;
        this.fecha = fecha;
        this.usuario = usuario;
        this.estado = estado;
        this.Id = Id;
        this.observacion = observacion;
        this.area = area;
        this.responsable = responsable;
        this.cierre = cierre;

    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCierre() {
        return cierre;
    }

    public void setCierre(String cierre) {
        this.cierre = cierre;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    /*Encapsulamiento GET and SET*/
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPunto() {
        return Punto;
    }

    public void setPunto(String punto) {
        Punto = punto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
