package org.oscarmencos.bean;


public class Cliente {
    private int codigoCliente;
    private String nombre;
    private String direccion;
    private String nit;

    public Cliente(int codigoCliente, String nombre, String direccion, String nit) {
        this.codigoCliente = codigoCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nit = nit;
    }

    
    

    public Cliente() {
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
        public String toString(){
        return getCodigoCliente() + "|";
    }
   
}
