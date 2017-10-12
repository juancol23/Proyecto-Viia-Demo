package valcolra.viiascreen.com.proyecto_viiascreen.model;

/**
 * Created by Vic on 29/09/2017.
 */

/*Modelo del checking*/

public class ModelChecking {
    /*variables del objeto que son los mismos del servicio*/
    private String Id;
    private String area;
    private String closedCheck;
    private String CreatedAt;
    private String estado;
    private String observacion1;
    private String observacion2;
    private String punto;
    private String usuario;
    private String urlImagen1;
    private String urlImagen2;
    private String responsable;

    public ModelChecking() {
    }

    /*Constructor para el adapter*/
    public ModelChecking(String id, String area, String closedCheck, String createdAt, String estado, String observacion1, String observacion2, String punto, String usuario, String urlImagen1, String urlImagen2, String responsable) {
        Id = id;
        this.area = area;
        this.closedCheck = closedCheck;
        this.CreatedAt = createdAt;
        this.estado = estado;
        this.observacion1 = observacion1;
        this.observacion2 = observacion2;
        this.punto = punto;
        this.usuario = usuario;
        this.urlImagen1 = urlImagen1;
        this.urlImagen2 = urlImagen2;
        this.responsable = responsable;
    }

    /*Encapsulamiento GET and SET*/
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getClosedCheck() {
        return closedCheck;
    }

    public void setClosedCheck(String closedCheck) {
        this.closedCheck = closedCheck;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUrlImagen1() {
        return urlImagen1;
    }

    public void setUrlImagen1(String urlImagen1) {
        this.urlImagen1 = urlImagen1;
    }

    public String getUrlImagen2() {
        return urlImagen2;
    }

    public void setUrlImagen2(String urlImagen2) {
        this.urlImagen2 = urlImagen2;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
