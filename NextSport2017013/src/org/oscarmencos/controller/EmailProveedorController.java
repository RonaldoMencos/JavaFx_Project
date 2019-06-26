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
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.oscarmencos.bean.EmailProveedor;
import org.oscarmencos.bean.Proveedor;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.sistema.Principal;

public class EmailProveedorController implements Initializable {
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Proveedor>  listaProveedor;
    private ObservableList<EmailProveedor>  listaEmailProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbProveedor;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    cmbProveedor.setItems(getProveedores());
    getEmailProveedores();
    }
    public ObservableList<Proveedor>getProveedores(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProveedores}");
           
            ResultSet resultado = procedimiento.executeQuery();
           
            while(resultado.next()){
            lista.add(new Proveedor(resultado.getInt("codigoProveedor"),resultado.getString("contactoPrincipal"),resultado.getString("paginaWeb"),resultado.getString("direccion"),resultado.getString("nit"),resultado.getString("razonSocial")));    
            }
             }catch(Exception e){
            e.printStackTrace();
            
           }
        return listaProveedor = FXCollections.observableArrayList(lista);
            }
    
    
     public ObservableList<EmailProveedor>getEmailProveedores(){
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarEmailProveedores}");
           
            ResultSet resultado = procedimiento.executeQuery();
           
            while(resultado.next()){
            lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),resultado.getString("email"),resultado.getString("descripcion"),resultado.getInt("codigoProveedor"),resultado.getString("proveedor")));    
            }
             }catch(Exception e){
            e.printStackTrace();
            
           }
        return listaEmailProveedor = FXCollections.observableArrayList(lista);
            }
    
          
    
     public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                
                desactivarControles ();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Regresar");
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                ventanaProveedores();
                btnEliminar.setText("Regresar");
        }  
    }
        
                public void nuevo(){
                switch(tipoDeOperacion){
                    case NINGUNO:
                        
                        activarControles();
                        txtDescripcion.setEditable(true);
                        txtEmail.setEditable(true);
                        btnNuevo.setText("Guardar");
                        btnEliminar.setText("Cancelar");
                        tipoDeOperacion = operaciones.GUARDAR;

                
                    break;
                    case GUARDAR:
                        agregar();
                        limpiarControles();
                        btnNuevo.setText("Nuevo");
                        btnEliminar.setText("Regresar");
                        tipoDeOperacion = operaciones.NINGUNO;
                        break;
                 
        }
                }
                
                 public void agregar(){
                    EmailProveedor registro = new EmailProveedor();
                    registro.setEmail(txtEmail.getText());
                    registro.setDescripcion(txtDescripcion.getText());
                    registro.setCodigoProveedor(((Proveedor)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                        try{
                             PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarEmailProveedor(?,?,?)}");
                             procedimiento.setString(1, registro.getEmail());
                             procedimiento.setString(2, registro.getDescripcion());
                             procedimiento.setInt(3, registro.getCodigoProveedor());
                             procedimiento.execute();
                             listaEmailProveedor.add(registro);
                        }catch(SQLException e){
                            e.printStackTrace();
        } 
    }
                 
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        txtEmail.setEditable(false);      
        cmbProveedor.setDisable(true);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        txtEmail.setEditable(true);
        cmbProveedor.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtEmail.setText("");
        cmbProveedor.getSelectionModel().clearSelection();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
   public void ventanaProveedores(){
         escenarioPrincipal.ventanaProveedores();
     }

    }


    

