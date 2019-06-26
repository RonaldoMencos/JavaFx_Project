
package org.oscarmencos.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.oscarmencos.bean.Compra;
import org.oscarmencos.sistema.Principal;

public class DetalleCompraController implements Initializable {

   private Principal escenarioPrincipal;
   private Compra compra;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

     public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

   

    

    
}
