
package org.oscarmencos.sistema;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.oscarmencos.bean.Compra;
import org.oscarmencos.controller.CategoriaController;
import org.oscarmencos.controller.ClienteController;
import org.oscarmencos.controller.CompraController;
import org.oscarmencos.controller.DetalleCompraController;
import org.oscarmencos.controller.EmailClienteController;
import org.oscarmencos.controller.EmailProveedorController;
import org.oscarmencos.controller.LoginController;
import org.oscarmencos.controller.MarcaController;
import org.oscarmencos.controller.TallaController;
import org.oscarmencos.controller.MenuPrincipalController;
import org.oscarmencos.controller.ProductoController;
import org.oscarmencos.controller.ProveedorController;
import org.oscarmencos.controller.RegistroController;
import org.oscarmencos.controller.TelefonoClienteController;
import org.oscarmencos.controller.TelefonoProveedorController;



public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/oscarmencos/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        this.escenarioPrincipal = escenarioPrincipal;
        escenarioPrincipal.setTitle("NexSportApplication");
        ventanaLogin();
        escenarioPrincipal.show();
        
             
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void ventanaLogin (){
        try{
            LoginController loginController = (LoginController)cambiarEscena ("LoginView.fxml",600,400);
            loginController.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
            
        }
    }
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",403,300);
            menuPrincipal.setEscenarioPrincipal(this);    
    }catch (Exception e){   
        e.printStackTrace();
    }
       
    }
    public void ventanaCategorias (){
        try{
            CategoriaController categoriaController = (CategoriaController)cambiarEscena ("CategoriaView.fxml",600,470);
            categoriaController.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaMarcas (){
        try{
            MarcaController marcaController = (MarcaController) cambiarEscena("MarcaView.fxml", 600, 500);
            marcaController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTallas (){
        try{
            TallaController tallaController = (TallaController) cambiarEscena("TallaView.fxml", 600, 500);
            tallaController.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductos(){
            try {
                ProductoController productoController = (ProductoController) cambiarEscena ("ProductosView.fxml",600,600);
                productoController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
        public void ventanaProveedores(){
            try {
                ProveedorController proveedorController = (ProveedorController) cambiarEscena ("ProveedoresView.fxml",600,600);
                proveedorController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
         public void ventanaClientes(){
            try {
                ClienteController clienteController = (ClienteController) cambiarEscena ("ClientesView.fxml",600,550);
                clienteController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         
         public void ventanaEmailProveedores(){
            try {
                EmailProveedorController emailProveedorController = (EmailProveedorController) cambiarEscena ("EmailProveedorView.fxml",500,300);
                emailProveedorController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         
         public void ventanaTelefonoProveedores(){
            try {
                TelefonoProveedorController telefonoProveedorController = (TelefonoProveedorController) cambiarEscena ("TelefonoProveedorView.fxml",500,300);
                telefonoProveedorController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         
         public void ventanaTelefonoClientes(){
            try {
                TelefonoClienteController telefonoClienteController = (TelefonoClienteController) cambiarEscena ("TelefonoClienteView.fxml",500,300);
                telefonoClienteController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         
         public void ventanaEmailClientes(){
            try {
                EmailClienteController emailClienteController = (EmailClienteController) cambiarEscena ("EmailClienteView.fxml",500,300);
                emailClienteController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         public void ventanaCompras(){
             try {
                CompraController compraController = (CompraController) cambiarEscena ("ComprasView.fxml",600,500);
                compraController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         
         public void ventanaDetalleCompras(Compra compra){
             try {
                DetalleCompraController detalleCompraController = (DetalleCompraController) cambiarEscena ("DetalleComprasView.fxml",600,500);
                detalleCompraController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         
         public void ventanaRegistro(){
             try {
                RegistroController registroController = (RegistroController) cambiarEscena ("RegistroView.fxml",700,400);
                registroController.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
         

    public Initializable cambiarEscena(String fxml,int ancho,int alto)throws Exception{
            Initializable resultado = null;
            FXMLLoader cargadorFXML = new FXMLLoader();
            InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
            cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
            cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
            escena = new Scene((AnchorPane )cargadorFXML.load(archivo),ancho,alto);
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.sizeToScene();
            resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }

   


}
