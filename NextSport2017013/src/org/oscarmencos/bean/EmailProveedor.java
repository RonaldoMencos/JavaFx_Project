
package org.oscarmencos.bean;

public class EmailProveedor {
    private int CodigoEmailProveedor;
    private String email;
    private String descripcion;
    private int CodigoProveedor;
    private String proveedor;

    public EmailProveedor(int CodigoEmailProveedor, String email, String descripcion, int CodigoProveedor, String proveedor) {
        this.CodigoEmailProveedor = CodigoEmailProveedor;
        this.email = email;
        this.descripcion = descripcion;
        this.CodigoProveedor = CodigoProveedor;
        this.proveedor = proveedor;
    }

    public EmailProveedor() {
    }

    public int getCodigoEmailProveedor() {
        return CodigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int CodigoEmailProveedor) {
        this.CodigoEmailProveedor = CodigoEmailProveedor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoProveedor() {
        return CodigoProveedor;
    }

    public void setCodigoProveedor(int CodigoProveedor) {
        this.CodigoProveedor = CodigoProveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
  

    
    
}