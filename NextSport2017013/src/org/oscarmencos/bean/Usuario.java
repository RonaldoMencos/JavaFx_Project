
package org.oscarmencos.bean;


public class Usuario {
    private int codigoUsuario;
    private String nombreUsuario;
    private String email;
    private String usuario;
    private String contrasena;
    private String tipoDeUsuario;

    public Usuario(int codigoUsuario, String nombreUsuario, String email, String usuario, String contrasena, String tipoDeUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.tipoDeUsuario = tipoDeUsuario;
    }

    public Usuario() {
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(String tipoUsuario) {
        this.tipoDeUsuario = tipoUsuario;
    }
public String toString(){
        return getTipoDeUsuario();
    }
    


    


  
    
    
}
