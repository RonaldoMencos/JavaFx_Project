
package org.oscarmencos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.oscarmencos.sistema.Principal;


public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML private Button btnCerrar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaCategorias(){
        escenarioPrincipal.ventanaCategorias();
    }
    
    public void ventanaMarcas(){
        escenarioPrincipal.ventanaMarcas();
    }
    
     public void ventanaTallas(){
        escenarioPrincipal.ventanaTallas();
    }
     
     public void ventanaProductos(){
         escenarioPrincipal.ventanaProductos();
     }
    
     public void ventanaProveedores(){
         escenarioPrincipal.ventanaProveedores();
     }
     
     public void ventanaClientes(){
         escenarioPrincipal.ventanaClientes();
     }
     
     public void ventanaCompras(){
         escenarioPrincipal.ventanaCompras();
     }
     public void cerrar(){
         escenarioPrincipal.ventanaLogin();
     }
}
