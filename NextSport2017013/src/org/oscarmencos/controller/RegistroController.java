package org.oscarmencos.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.oscarmencos.sistema.Principal;

public class RegistroController implements Initializable{
    private ObservableList<String> tipoUsuario = FXCollections.observableArrayList("Admin", "Root", "Invitado", "SA");
    private enum operaciones{ACCEDER, REGISTRO, CANCELAR, REPORTE, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Usuario> listaUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField pssContrasena;
    @FXML private ComboBox cmbTipoDeUsuario;
    @FXML private Button btnRegistro;
    @FXML private Button btnRegresar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     cmbTipoDeUsuario.setItems(tipoUsuario);
    }
    
    public ObservableList<Usuario> getTipos(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarUsuarios}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Usuario(resultado.getInt("codigoUsuario"), resultado.getString("nombreUsuario"), resultado.getString("email"), resultado.getString("usuario"), resultado.getString("contrasena"), resultado.getString("tipoDeUsuario")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaUsuario = FXCollections.observableArrayList(lista);
    }
    
    public void Agregar(){
        String nombre = txtNombreUsuario.getText();
        String email = txtEmail.getText();
        String usuario = txtUsuario.getText();
        String password = pssContrasena.getText();
        String tipo = ((String)cmbTipoDeUsuario.getSelectionModel().getSelectedItem());
        if(nombre.equals("")||email.equals("")||usuario.equals("")||password.equals("")||tipo.equals("")){
            JOptionPane.showMessageDialog(null, "Debe rellenar todps los campos" + "\n " + "Debe llenar todos los campos", "Vuelva a registrarse", JOptionPane.ERROR_MESSAGE);
                    
        }else{
            try{
                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarUsuario(?,?,?,?,?)}");
                procedimiento.setString(1, email);
                procedimiento.setString(2, usuario);
                procedimiento.setString(3, nombre);
                procedimiento.setString(4, password);
                procedimiento.setString(5, tipo);
                procedimiento.execute();
                limpiarControles();
                JOptionPane.showMessageDialog(null, "!Felicidades se a registrado con exito!", "Registro terminado", JOptionPane.INFORMATION_MESSAGE);
                ventanaRegistro();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case REGISTRO:
                limpiarControles();
                btnRegistro.setText("Nuevo Registro");
                btnRegresar.setText("Regresar");
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                
            default:
                ventanaLogin();
        }
    }
    
    public void ventanaRegistro(){
        escenarioPrincipal.ventanaRegistro();
    }

    

    
    public void limpiarControles(){
        txtNombreUsuario.setText("");
        txtEmail.setText("");
        txtUsuario.setText("");
        pssContrasena.setText("");
        cmbTipoDeUsuario.setValue(null);
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaLogin(){
         escenarioPrincipal.ventanaLogin();
    }
    
}


