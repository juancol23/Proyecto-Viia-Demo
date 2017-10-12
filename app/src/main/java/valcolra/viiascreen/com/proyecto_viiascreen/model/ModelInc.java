package valcolra.viiascreen.com.proyecto_viiascreen.model;

/**
 * Created by Vic on 3/10/2017.
 */
/*Modelo de la Incidencia*/
public class ModelInc {
    /*variables del objeto que son los mismos del servicio*/
    private String Id;
    private String area;
    private String estado;
    private String usuario;
    private String closedTask;
    private String createAt;
    private String createdTask;
    private String direccion;
    private String ubicacion;
    private String observacion1;
    private String observacion2;
    private String observacion3;
    private String servicio;
    private String urlImagen1;
    private String urlImagen2;
    private String punto;


    public ModelInc() {

    }

    /*Constructor para el adapter*/
    public ModelInc(String id, String area, String estado, String usuario, String closedTask, String createAt, String createdTask,  String direccion, String ubicacion, String observacion1, String observacion2, String observacion3, String servicio, String urlImagen1, String urlImagen2, String punto) {
        Id = id;
        this.area = area;
        this.estado = estado;
        this.usuario = usuario;
        this.closedTask = closedTask;
        this.createAt = createAt;
        this.createdTask = createdTask;
        this.direccion = direccion;
        this.ubicacion = ubicacion;
        this.observacion1 = observacion1;
        this.observacion2 = observacion2;
        this.observacion3 = observacion3;
        this.servicio = servicio;
        this.urlImagen1 = urlImagen1;
        this.urlImagen2 = urlImagen2;
        this.punto = punto;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClosedTask() {
        return closedTask;
    }

    public void setClosedTask(String closedTask) {
        this.closedTask = closedTask;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreatedTask() {
        return createdTask;
    }

    public void setCreatedTask(String createdTask) {
        this.createdTask = createdTask;
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

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
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

    public String getPunto() {
        return punto;
    }

    public void setPunto(String punto) {
        this.punto = punto;
    }
}
