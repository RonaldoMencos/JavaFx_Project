
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
import org.oscarmencos.bean.TelefonoCliente;
import org.oscarmencos.bean.Cliente;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.sistema.Principal;

public class TelefonoClienteController implements Initializable {

    
     private enum operaciones{NUEVO,GUARDAR,ELIMINAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Cliente>  listaCliente;
    private ObservableList<TelefonoCliente>  listaTelefonoCliente;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtNumero;
    @FXML private ComboBox cmbCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    cmbCliente.setItems(getClientes());
    getTelefonoClientes();
    }
    public ObservableList<Cliente>getClientes(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarClientes}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"),resultado.getString("nombre"),resultado.getString("direccion"),resultado.getString("nit")));
            }
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCliente = FXCollections.observableArrayList(lista);
    }

    
    
     public ObservableList<TelefonoCliente>getTelefonoClientes(){
        ArrayList<TelefonoCliente> lista = new ArrayList<TelefonoCliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTelefonoClientes}");
           
            ResultSet resultado = procedimiento.executeQuery();
           
            while(resultado.next()){
            lista.add(new TelefonoCliente(resultado.getInt("codigoTelefonoCliente"),resultado.getString("numero"),resultado.getString("descripcion"),resultado.getInt("codigoCliente")));    
            }
             }catch(Exception e){
            e.printStackTrace();
            
           }
        return listaTelefonoCliente = FXCollections.observableArrayList(lista);
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
                ventanaClientes();
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
                    TelefonoCliente registro = new TelefonoCliente();
                    registro.setNumero(txtNumero.getText());
                    registro.setDescripcion(txtDescripcion.getText());
                    registro.setCodigoCliente(((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
                        try{
                             PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTelefonoCliente(?,?,?)}");
                             procedimiento.setString(1, registro.getNumero());
                             procedimiento.setString(2, registro.getDescripcion());
                             procedimiento.setInt(3, registro.getCodigoCliente());
                             procedimiento.execute();
                             listaTelefonoCliente.add(registro);
                        }catch(SQLException e){
                            e.printStackTrace();
        } 
    }
                 
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        txtNumero.setEditable(false);      
        cmbCliente.setDisable(true);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        txtNumero.setEditable(true);
        cmbCliente.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        txtNumero.setText("");
        cmbCliente.getSelectionModel().clearSelection();
    }
    
    
   public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
 
   public void ventanaClientes(){
         escenarioPrincipal.ventanaClientes();
     }

    }

