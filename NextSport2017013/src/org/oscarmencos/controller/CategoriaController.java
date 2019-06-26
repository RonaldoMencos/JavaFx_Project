
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
import org.oscarmencos.bean.Categoria;
import org.oscarmencos.sistema.Principal;





public class CategoriaController implements Initializable {


    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Categoria>  listaCategoria;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbCategoria;
    @FXML private TableView tblCategorias;
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
        tblCategorias.setItems(getCategorias());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Categoria,Integer>("codigoCategoria"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Categoria,String>("descripcion"));
    }
    
    public ObservableList<Categoria>getCategorias(){
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCategoria}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Categoria(resultado.getInt("codigoCategoria"),resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaCategoria = FXCollections.observableList(lista);
    }
        
    public void seleccionarElemento(){
        if (tblCategorias.getSelectionModel().getSelectedItem() != null){;
        cmbCategoria.getSelectionModel().select(buscarCategoria(((Categoria)tblCategorias.getSelectionModel().getSelectedItem()).getCodigoCategoria()));
        txtDescripcion.setText(((Categoria)tblCategorias.getSelectionModel().getSelectedItem()).getDescripcion());
    }  else{
            JOptionPane.showMessageDialog(null, "No hay registros");
    }
    }
        
    
    public Categoria buscarCategoria(int codigoCategoria){
        Categoria resultado = null;
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCategoria(?)}");
            procedimiento.setInt(1,codigoCategoria);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Categoria(registro.getInt("codigoCategoria"),registro.getString("descripcion"));
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
                if(tblCategorias.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?","Eliminar Categoria",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                       try{ PreparedStatement procedimento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarCategoria(?)}");
                       procedimento.setInt (1,((Categoria)tblCategorias.getSelectionModel().getSelectedItem()).getCodigoCategoria());
                       procedimento.execute();
                       listaCategoria.remove(tblCategorias.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                           cargarDatos();
                       }catch(SQLException e){
                           e.printStackTrace();
                           
                       }
                    }
                }else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una categoria");
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
        Categoria registro = new Categoria();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarCategoria(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaCategoria.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblCategorias.getSelectionModel().getSelectedItem() != null){;
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    txtDescripcion.setEditable(true);
                    
                        
                }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una categoria");
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarCategoria(?,?)}");
            Categoria registro = (Categoria) tblCategorias.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.setInt(2, registro.getCodigoCategoria());
            procedimiento.execute();
        }catch(SQLException e){
           e.printStackTrace();
        }
        
    }
    

    
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        cmbCategoria.setDisable(true);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        cmbCategoria.setDisable(true);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbCategoria.setValue("");
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
