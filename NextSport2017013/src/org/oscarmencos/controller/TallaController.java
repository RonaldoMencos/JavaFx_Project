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
import org.oscarmencos.bean.Talla;
import org.oscarmencos.sistema.Principal;





public class TallaController implements Initializable {


    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Talla>  listaTalla;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbTalla;
    @FXML private TableView tblTallas;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblTallas.setItems(getTallas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Talla,Integer>("codigoTalla"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Talla,String>("descripcion"));
    }
    
    public ObservableList<Talla>getTallas(){
        ArrayList<Talla> lista = new ArrayList<Talla>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTallas}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Talla(resultado.getInt("codigoTalla"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaTalla = FXCollections.observableList(lista);
    }
        
    public void seleccionarElemento(){
        if (tblTallas.getSelectionModel().getSelectedItem() != null){;
        cmbTalla.getSelectionModel().select(buscarTalla(((Talla)tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla()));
        txtDescripcion.setText(((Talla)tblTallas.getSelectionModel().getSelectedItem()).getDescripcion());
        }
        else{
            JOptionPane.showMessageDialog(null, "No hay registros");
    }
    }
    
    public Talla buscarTalla(int codigoTalla){
        Talla resultado = null;
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscaTalla(?)}");
            procedimiento.setInt(1,codigoTalla);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Talla(registro.getInt("codigoTalla"),registro.getString("descripcion"));
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
                if(tblTallas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?","Eliminar Talla",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                       try{ PreparedStatement procedimento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarTallas(?)}");
                       procedimento.setInt (1,((Talla)tblTallas.getSelectionModel().getSelectedItem()).getCodigoTalla());
                       procedimento.execute();
                       listaTalla.remove(tblTallas.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                           cargarDatos();
                       }catch(SQLException e){
                           e.printStackTrace();
                           
                       }
                    }
                }else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una talla");
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
                 desactivarControles ();
                 btnNuevo.setText("Nuevo");
                 btnEliminar.setText("Eliminar");
                 btnEditar.setDisable(false);
                 btnReporte.setDisable(false);
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                 break;
                 
//            case CANCELAR:
//                activarControles();
//                limpiarControles();
//                btnNuevo.setText("Guardar");
//                btnEliminar.setText("Cancelar");
//                btnEditar.setDisable(true);
//                btnReporte.setDisable(true);
//                tipoDeOperacion = operaciones.GUARDAR;
//                tipoDeOperacion = operaciones.CANCELAR;
//                break;
        }
            
    }
    
    
    
    public void agregar(){
        Talla registro = new Talla();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTalla(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaTalla.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblTallas.getSelectionModel().getSelectedItem() != null){;
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtDescripcion.setEditable(true);
                        
                }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una talla");
                        }
                break;
            case ACTUALIZAR:
                actualizar ();
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarTalla(?,?)}");
            Talla registro = (Talla) tblTallas.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoTalla(((Talla)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.setInt(2, registro.getCodigoTalla());
            procedimiento.execute();
        }catch(SQLException e){
           e.printStackTrace();
        }
        
    }
    

    
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        cmbTalla.setDisable(true);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        cmbTalla.setDisable(true);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbTalla.setValue("");
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
    
       public void menuPrincipal(){
         escenarioPrincipal.menuPrincipal();
     
}
}
