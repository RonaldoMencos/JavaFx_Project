
package org.oscarmencos.bean;


public class TelefonoProveedor {
    private int codigoTelefonoProveedor;
    private String numero;
    private String descripcion;
    private int codigoProveedor;

    public TelefonoProveedor(int codigoTelefonoProveedor, String numero, String descripcion, int codigoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
        this.numero = numero;
        this.descripcion = descripcion;
        this.codigoProveedor = codigoProveedor;
    }

    public TelefonoProveedor() {
    }

    public int getCodigoTelefonoProveedor() {
        return codigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int codigoTelefonoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
    
    
}
