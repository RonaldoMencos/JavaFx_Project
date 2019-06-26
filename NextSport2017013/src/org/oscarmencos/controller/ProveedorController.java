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
import org.oscarmencos.bean.Proveedor;
import org.oscarmencos.sistema.Principal;





public class ProveedorController implements Initializable {


    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Proveedor>  listaProveedor;
    @FXML private TextField txtContactoPrincipal;
    @FXML private TextField txtPaginaWeb;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtNit;
    @FXML private TextField txtEmail;
    @FXML private TextField txtRazonSocial;
    @FXML private ComboBox cmbProveedor;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colContactoPrincipal;
    @FXML private TableColumn colPaginaWeb;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colRazonSocial;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();

    }
    
    public void cargarDatos(){
         tblProveedores.setItems(getProveedores());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("paginaWeb"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("direccion"));
        colNit.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("nit"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("razonSocial"));
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
                return listaProveedor = FXCollections.observableList(lista);
    }
    
    
        
    public void seleccionarElemento(){
        if (tblProveedores.getSelectionModel().getSelectedItem() != null){;
        cmbProveedor.getSelectionModel().select(buscarProveedor(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtContactoPrincipal.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWeb.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
        txtDireccion.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getDireccion());
        txtNit.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getNit());
        txtRazonSocial.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
    }  else{
            JOptionPane.showMessageDialog(null, "No hay registros");
    }
    }
        
    
    public Proveedor buscarProveedor(int codigoProveedor){
        Proveedor resultado = null;
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1,codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Proveedor(registro.getInt("codigoProveedor"),registro.getString("contactoPrincipal"),registro.getString("paginaWeb"),registro.getString("direccion"),registro.getString("nit"),registro.getString("razonSocial"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
    
      
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles ();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?","Eliminar proveedor",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){                      
                    eliminarProveedor();
                }else {
                        JOptionPane.showMessageDialog(null, "Debe eliminar email con el mismo numero de proveedor");
     
                            }
                 
        }
    }
    }
    
    public void eliminarProveedor(){
        try{ PreparedStatement procedimento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                       procedimento.setInt (1,((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                       procedimento.execute();
                       listaProveedor.remove(tblProveedores.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                           cargarDatos();
                       }catch(SQLException e){
                           e.printStackTrace();
                           
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
                 desactivarControles ();
                 btnNuevo.setText("Nuevo");
                 btnEliminar.setText("Eliminar");
                 btnEditar.setDisable(false);
                 btnReporte.setDisable(false);
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                 break;
                 
        }
    }
    
    
    public void agregar(){
        Proveedor registro = new Proveedor();
        registro.setContactoPrincipal(txtContactoPrincipal.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setNit(txtNit.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?,?)}");
            procedimiento.setString(1, registro.getContactoPrincipal());
            procedimiento.setString(2, registro.getPaginaWeb());
            procedimiento.setString(3, registro.getDireccion());
            procedimiento.setString(4, registro.getNit());
            procedimiento.setString(5, registro.getRazonSocial());
            procedimiento.execute();
            listaProveedor.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    
    }
   
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null){;
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtContactoPrincipal.setEditable(true);
                    txtPaginaWeb.setEditable(true);
                    txtDireccion.setEditable(true);
                    txtNit.setEditable(true);
                    txtRazonSocial.setEditable(true);
                        
                }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una proveedor");
                        }
                break;
            case ACTUALIZAR:
                actualizar ();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                txtContactoPrincipal.setEditable(false);
                txtPaginaWeb.setEditable(false);
                txtDireccion.setEditable(false);
                txtNit.setEditable(false);
                txtRazonSocial.setEditable(false);
                limpiarControles();
                desactivarControles();
                cargarDatos();
                break;
    }
    }
  
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarProveedor(?,?,?,?,?,?)}");
            Proveedor registro = (Proveedor) tblProveedores.getSelectionModel().getSelectedItem();
            registro.setContactoPrincipal(txtContactoPrincipal.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            registro.setDireccion(txtDireccion.getText());
            registro.setNit(txtNit.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setCodigoProveedor(((Proveedor)cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setString(1, registro.getContactoPrincipal());
            procedimiento.setString(2, registro.getPaginaWeb());
            procedimiento.setString(3, registro.getDireccion());
            procedimiento.setString(4, registro.getNit());
            procedimiento.setString(5, registro.getRazonSocial());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
        }catch(SQLException e){
           e.printStackTrace();
        }
        
    }
    

    
    public void desactivarControles (){
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
        txtDireccion.setEditable(false);
        txtNit.setEditable(false);
        txtRazonSocial.setEditable(false);
        cmbProveedor.setDisable(true);
    }
    
    public void activarControles (){
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
        txtDireccion.setEditable(true);
        txtNit.setEditable(true);
        txtRazonSocial.setEditable(true);
        cmbProveedor.setDisable(true);
    }
    
    public void limpiarControles(){
        txtContactoPrincipal.setText("");
        txtPaginaWeb.setText("");
        txtDireccion.setText("");
        txtNit.setText("");
        txtRazonSocial.setText("");
        cmbProveedor.setValue("");
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                 desactivarControles ();
                 limpiarControles();
                 btnEditar.setText("Editar");
                 btnReporte.setText("Reporte");
                 btnNuevo.setDisable(false);
                 btnEliminar.setDisable(false);
                 tipoDeOperacion = operaciones.NINGUNO;
                 break;
        }
    }
    
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaEmailProveedores(){
        if (tblProveedores.getSelectionModel().getSelectedItem()  != null){
         JOptionPane.showMessageDialog(null, "\"Deben seleccionar un registro de proveedor, si no hay debe agregar un registro");
     }  else{
            escenarioPrincipal.ventanaEmailProveedores();
            
}
    
}
    
    public void ventanaTelefonoProveedores(){
        if (tblProveedores.getSelectionModel().getSelectedItem()  != null){
         JOptionPane.showMessageDialog(null, "Deben seleccionar un registro de proveedor, si no hay debe agregar un registro");
     }  else{
            escenarioPrincipal.ventanaTelefonoProveedores();
            
}
    
}
    
    public void menuPrincipal(){
         escenarioPrincipal.menuPrincipal();
    
    }
}
