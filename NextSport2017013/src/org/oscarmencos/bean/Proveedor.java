package org.oscarmencos.bean;


public class Proveedor {
    private int codigoProveedor;
    private String contactoPrincipal;
    private String paginaWeb;
    private String direccion;    
    private String nit;     
    private String razonSocial;
                     
    
    public Proveedor(int codigoProveedor, String contactoPrincipal,String paginaWeb,String direccion, String nit, String razonSocial) {
        this.codigoProveedor = codigoProveedor;
        this.contactoPrincipal = contactoPrincipal;
        this.paginaWeb = paginaWeb;
        this.direccion = direccion;
        this.nit = nit;
        this.razonSocial = razonSocial;
    }

    public Proveedor() {
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
     
   
    
    public String toString(){
        return getCodigoProveedor() + "|" + getContactoPrincipal();
    }
    
    
}

