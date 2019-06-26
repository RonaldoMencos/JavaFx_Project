
package org.oscarmencos.bean;
import java.util.Date;

public class Compra {
    private int numeroDocumento;
    private String descripcion;
    private Date fecha;
    private double total;
    private int codigoProveedor;
    private String contactoPrincipal;
    private String paginaWeb;
    private String direccion;
    private String nit;
    private String razonSocial;

    public Compra(int numeroDocumento, String descripcion, Date fecha, double total, int codigoProveedor, String contactoPrincipal, String paginaWeb, String direccion, String nit, String razonSocial) {
        this.numeroDocumento = numeroDocumento;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.total = total;
        this.codigoProveedor = codigoProveedor;
        this.contactoPrincipal = contactoPrincipal;
        this.paginaWeb = paginaWeb;
        this.direccion = direccion;
        this.nit = nit;
        this.razonSocial = razonSocial;
    }

    

    public Compra() {
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    
    
  
    
}
