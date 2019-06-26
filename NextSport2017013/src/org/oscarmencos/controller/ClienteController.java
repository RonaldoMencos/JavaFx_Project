package org.oscarmencos.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.bean.Cliente;
import org.oscarmencos.sistema.Principal;

public class ClienteController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cliente> listaCliente;
            
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtDireccion;
    
    @FXML
    private TextField txtNit;    
    
    @FXML
    private ComboBox cmbCliente;
    
    @FXML
    private TableView tblClientes;
    
    @FXML
    private TableColumn colCodigo;
    
    @FXML
    private TableColumn colNombre;

    @FXML
    private TableColumn colDireccion;
    
    @FXML
    private TableColumn colNit;
    
    @FXML
    private Button btnNuevo;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnReporte;
    
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
    }
    
    public void cargarDatos(){
        tblClientes.setItems(getClientes());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
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
    
     public void seleccionarElemento(){
         if(tblClientes.getSelectionModel().getSelectedItem() != null){
        cmbCliente.getSelectionModel().select(buscarCliente(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNombre.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNombre());
        txtDireccion.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getDireccion());
        txtNit.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNit());
       
        }else{
                JOptionPane.showMessageDialog(null, "No hay registro ");
             }
        }
    
    public Cliente buscarCliente(int codigoCliente){
        Cliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("codigoCliente"),registro.getString("nombre"),registro.getString("direccion"),registro.getString("nit"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar el registro?", "Eliminar cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                    try{
                        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarCliente(?)}");
                        procedimiento.setInt(1,((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                        procedimiento.execute();
                        listaCliente.remove(tblClientes.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        cargarDatos();
                    }catch(SQLException e){
                        e.printStackTrace();
                        
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                        }
        }
    }
    
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                agregar();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                limpiarControles();
                break;               
        }
    }
    
    public void agregar(){
        Cliente registro = new Cliente();
        registro.setNombre(txtNombre.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setNit(txtNit.getText());        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarCliente(?,?,?)}");
            procedimiento.setString(1, registro.getNombre());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getNit());            
            procedimiento.execute();
            listaCliente.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblClientes.getSelectionModel().getSelectedItem()!= null){
                
                   btnEditar.setText("Actualizar");
                   btnReporte.setText("Cancelar");
                   tipoDeOperacion = operaciones.ACTUALIZAR;
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   txtNombre.setEditable(true);
                   txtDireccion.setEditable(true);
                   txtNit.setEditable(true);                   
                   
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un cliente");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarControles();
                desactivarControles();
                cargarDatos();
                break;
        }
    }
        
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarCliente(?,?,?,?)}");
            Cliente registro = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombre(txtNombre.getText());
            registro.setDireccion(txtDireccion.getText());
            registro.setNit(txtNit.getText());
            registro.setCodigoCliente(((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            procedimiento.setString(1, registro.getNombre());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getNit());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }    
    }    

   
    
   public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
        
    public void desactivarControles(){
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        txtNit.setEditable(false);
        cmbCliente.setDisable(true);
    }
    
    public void activarControles(){
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        txtNit.setEditable(true);        
        cmbCliente.setDisable(true);    
    }
    
    public void limpiarControles(){
        txtNombre.setText("");
        txtDireccion.setText("");
        txtNit.setText("");
        cmbCliente.setValue("");   
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    
    public void ventanaTelefonoClientes(){
        if (tblClientes.getSelectionModel().getSelectedItem()   != null){
            escenarioPrincipal.ventanaTelefonoClientes();
     }  else{
            JOptionPane.showMessageDialog(null, "Deben seleccionar un registro de cliente, si no hay debe agregar un registro"); 
            
            
}
       }
        public void ventanaEmailClientes(){
        if (tblClientes.getSelectionModel().getSelectedItem()  != null){
            escenarioPrincipal.ventanaEmailClientes();
     }  else{
            JOptionPane.showMessageDialog(null, "Deben seleccionar un registro de cliente, si no hay debe agregar un registro");
            
            
}
        }
       public void menuPrincipal(){
         escenarioPrincipal.menuPrincipal();
     
}
       
       
        
    
}
