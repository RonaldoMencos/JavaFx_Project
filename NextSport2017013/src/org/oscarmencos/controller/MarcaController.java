
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
import org.oscarmencos.bean.Marca;
import org.oscarmencos.sistema.Principal;





public class MarcaController implements Initializable {


    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Marca>  listaMarca;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbMarca;
    @FXML private TableView tblMarcas;
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
        tblMarcas.setItems(getMarcas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Marca,Integer>("codigoMarca"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Marca,String>("descripcion"));
    }
    
    public ObservableList<Marca>getMarcas(){
        ArrayList<Marca> lista = new ArrayList<Marca>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarMarca}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Marca(resultado.getInt("codigoMarca"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaMarca = FXCollections.observableList(lista);
    }
        
    public void seleccionarElemento(){
        if (tblMarcas.getSelectionModel().getSelectedItem() != null){;
        cmbMarca.getSelectionModel().select(buscarMarca(((Marca)tblMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca()));
        txtDescripcion.setText(((Marca)tblMarcas.getSelectionModel().getSelectedItem()).getDescripcion());
    }   else{
            JOptionPane.showMessageDialog(null, "No hay registros");
    }
    }
    
    public Marca buscarMarca(int codigoMarca){
        Marca resultado = null;
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarMarca(?)}");
            procedimiento.setInt(1,codigoMarca);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Marca(registro.getInt("codigoMarca"),registro.getString("descripcion"));
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
                if(tblMarcas.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?","Eliminar Marca",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                       try{ PreparedStatement procedimento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarMarcas(?)}");
                       procedimento.setInt (1,((Marca)tblMarcas.getSelectionModel().getSelectedItem()).getCodigoMarca());
                       procedimento.execute();
                       listaMarca.remove(tblMarcas.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                           cargarDatos();
                       }catch(SQLException e){
                           e.printStackTrace();
                           
                       }
                    }
                }else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una marca");
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
        Marca registro = new Marca();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarMarca(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaMarca.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblMarcas.getSelectionModel().getSelectedItem() != null){;
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtDescripcion.setEditable(true);
                        
                }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una marca");
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarMarca(?,?)}");
            Marca registro = (Marca) tblMarcas.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoMarca(((Marca)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.setInt(2, registro.getCodigoMarca());
            procedimiento.execute();
        }catch(SQLException e){
           e.printStackTrace();
        }
        
    }
    

    
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        cmbMarca.setDisable(true);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        cmbMarca.setDisable(true);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbMarca.setValue("");
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