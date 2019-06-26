
package org.oscarmencos.controller;
import java.net.URL;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.oscarmencos.bean.TelefonoProveedor;
import org.oscarmencos.bean.Proveedor;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.sistema.Principal;

public class TelefonoProveedorController implements Initializable{


    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Proveedor>  listaProveedor;
    private ObservableList<TelefonoProveedor>  listaTelefonoProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtNumero;
    @FXML private ComboBox cmbProveedor;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    cmbProveedor.setItems(getProveedores());
    getTelefonoProveedores();
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
    
    
     public ObservableList<TelefonoProveedor>getTelefonoProveedores(){
        ArrayList<TelefonoProveedor> lista = new ArrayList<TelefonoProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTelefonoProveedores}");
           
            ResultSet resultado = procedimiento.executeQuery();
           
            while(resultado.next()){
            lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),resultado.getString("numero"),resultado.getString("descripcion"),resultado.getInt("codigoProveedor")));    
            }
             }catch(Exception e){
            e.printStackTrace();
            
           }
        return listaTelefonoProveedor = FXCollections.observableArrayList(lista);
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
            case NINGUNO:
                ventanaProveedores();
                btnEliminar.setText("Regresar");
        }  
    }
        
                public void nuevo(){
                switch(tipoDeOperacion){
                    case NINGUNO:
                        
                        activarControles();
                        txtDescripcion.setEditable(true);
                        txtNumero.setEditable(true);
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
                    TelefonoProveedor registro = new TelefonoProveedor();
                    registro.setNumero(txtNumero.getText());
                    registro.setDescripcion(txtDescripcion.getText());
                    registro.setCodigoProveedor(((Proveedor)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                        try{
                             PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?,?,?)}");
                             procedimiento.setString(1, registro.getNumero());
                             procedimiento.setString(2, registro.getDescripcion());
                             procedimiento.setInt(3, registro.getCodigoProveedor());
                             procedimiento.execute();
                             listaTelefonoProveedor.add(registro);
                        }catch(SQLException e){
                            e.printStackTrace();
        } 
    }
                 
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        txtNumero.setEditable(false);      
        cmbProveedor.setDisable(true);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        txtNumero.setEditable(true);
        cmbProveedor.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtNumero.setText("");
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

