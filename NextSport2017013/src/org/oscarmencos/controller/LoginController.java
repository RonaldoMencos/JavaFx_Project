package org.oscarmencos.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.oscarmencos.bean.Usuario;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.report.GenerarReporte;
import org.oscarmencos.sistema.Principal;

public class LoginController implements Initializable{
    private ObservableList<String> tipoUsuario = FXCollections.observableArrayList("Admin", "Root", "Invitado", "SA");
    private Principal escenarioPrincipal;
    private ObservableList<Usuario> listaUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private PasswordField pssContrasena;
    @FXML private ComboBox cmbTipoDeUsuario;
    @FXML private Button btnIngresar;
    @FXML private Button btnRegistrar;
    @FXML private Button btnCancelar;
    @FXML private Button btnReporte;
    @FXML private Button btnCerrar;
        

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbTipoDeUsuario.setItems(tipoUsuario);
        btnReporte.setDisable(true);
    }
   
    
    public void Login(){
        String usuario = txtNombreUsuario.getText();
        String password = pssContrasena.getText();
        String tipo = ((String)cmbTipoDeUsuario.getSelectionModel().getSelectedItem());
        if((usuario.equals("")||password.equals(""))){
            JOptionPane.showMessageDialog(null, "No puede dejar campos vacios"
                    + "\n " + "Debe llenar todos los campos", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
                        
        }else{
        try{
           PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareStatement("{call sp_Usuarios(?,?,?)}");
           procedimiento.setString(1, usuario);
           procedimiento.setString(2, password);
           procedimiento.setString(3, tipo);
           ResultSet rst = procedimiento.executeQuery();
           int cont=0;
           while(rst.next()){
               cont=cont+1;
           } 
               if(cont==1){
                   JOptionPane.showMessageDialog(null, "Ha iniciado sesión correctamente con tipo "+tipo);
                   if(tipo.equals("Root")||tipo.equals("Admin")){
                       btnReporte.setDisable(false);
                       int respuesta=JOptionPane.showConfirmDialog(null, "Desea ir al menu principal?", "Menu Principal", JOptionPane.YES_NO_OPTION);
                       if(respuesta==JOptionPane.YES_OPTION){
                           MenuPrincipal();
                       }
                   }else{
                       btnReporte.setDisable(true);
                       MenuPrincipal();
                   }

                   
                   
               }else{
                   JOptionPane.showMessageDialog(null, "El usuario no existe y/o el usuario y la contraseña son incorreto(s)");
               }

        }catch(SQLException e){
            e.printStackTrace();
        }
        }
        
    }
    
    public void MenuPrincipal(){
      escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaRegistro(){
      escenarioPrincipal.ventanaRegistro();
    }
    
    public void reporte(){
        imprimirReporte();
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("_NumeroDocumento", null);
        GenerarReporte.mostrarReporte("ReporteUsuarios.jasper", "Reporte de Usuarios", parametros);
    }
    
    public void cancelar(){
        btnReporte.setDisable(true);
        txtNombreUsuario.setText("");
        pssContrasena.setText("");
        cmbTipoDeUsuario.setValue(null);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void cerrar(){
        System.exit(0);
    }
    
}

