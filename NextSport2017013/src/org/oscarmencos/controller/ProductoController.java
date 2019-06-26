
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import org.oscarmencos.bean.Categoria;
import org.oscarmencos.bean.Marca;
import org.oscarmencos.bean.Talla;
import org.oscarmencos.bean.Producto;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.report.GenerarReporte;
import org.oscarmencos.sistema.Principal;



public class ProductoController implements Initializable {
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;   
    private ObservableList<Categoria>  listaCategoria;
    private ObservableList<Marca>  listaMarca;
    private ObservableList<Talla>  listaTalla;
    private ObservableList<Producto>  listaProducto;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtImagen;
    @FXML private ComboBox cmbCategoria;
    @FXML private ComboBox cmbMarca;
    @FXML private ComboBox cmbTalla;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colPrecioUnitario;
    @FXML private TableColumn colPrecioDocena;
    @FXML private TableColumn colPrecioMayor;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private Button btnImagen;
    @FXML private ImageView imgProducto;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCategoria.setItems(getCategorias());
        cmbMarca.setItems(getMarcas());
        cmbTalla.setItems(getTallas());
        getProductos();
        
    }
    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colExistencia.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("existencia"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioMayor"));
         
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
      
      public ObservableList<Producto>getProductos(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
               lista.add(new Producto(resultado.getInt("codigoProducto"),
                resultado.getString("descripcion"),
                resultado.getInt("existencia"),
                resultado.getDouble("precioUnitario"),
                resultado.getDouble("precioDocena"),
                resultado.getDouble("precioMayor"),
                resultado.getString("imagen"),
                resultado.getInt("codigoCategoria"),
                resultado.getString("categoria"),
                resultado.getInt("codigoMarca"),
                resultado.getString("marca"),
                resultado.getInt("codigoTalla"),
                resultado.getString("talla")));
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaProducto = FXCollections.observableList(lista);
    }
      
       public void seleccionarElemento(){
        if (tblProductos.getSelectionModel().getSelectedItem() != null){;
        cmbCategoria.getSelectionModel().select(buscarCategoria(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoCategoria()));
        cmbMarca.getSelectionModel().select(buscarMarca(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoMarca()));
        cmbTalla.getSelectionModel().select(buscarTalla(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTalla()));
        txtDescripcion.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getDescripcion());
        txtImagen.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getImagen());
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
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NUEVO;
                break;
                
            default:
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?","Eliminar Producto",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                       try{ PreparedStatement procedimento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarProducto(?)}");
                       procedimento.setInt (1,((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                       procedimento.execute();
                       listaProducto.remove(tblProductos.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                           
                       }catch(SQLException e){
                           e.printStackTrace();
                           
                       }
                    }
                }else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una producto");
                    }
                 
        }
    }
        
        

    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;

                
                break;
            case GUARDAR:
                 agregar();
                 limpiarControles();
                 btnNuevo.setText("Nuevo");
                 btnEliminar.setText("Eliminar");
                 btnEditar.setDisable(false);
                 btnReporte.setDisable(false);
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                 break;
                 
//            
        }
            
    }
    
    
    
    public void agregar(){
        Producto registro = new Producto();
        registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
        registro.setCodigoMarca(((Marca)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
        registro.setCodigoTalla(((Talla)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
        registro.setImagen(txtImagen.getText());
        registro.setDescripcion(txtDescripcion.getText());
        

        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarProducto(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCategoria());
            procedimiento.setInt(2, registro.getCodigoMarca());
            procedimiento.setInt(3, registro.getCodigoTalla());
            procedimiento.setString(4, registro.getImagen());
            procedimiento.setString(5, registro.getDescripcion());
            procedimiento.execute();
            listaProducto.add(registro);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null){;
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnImagen.setDisable(false);
                    txtDescripcion.setEditable(true);
                    cmbCategoria.setDisable(false);
                    cmbTalla.setDisable(false);
                    cmbMarca.setDisable(false);
                        
                }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una producto");
                        }
                break;
            case ACTUALIZAR:
                actualizar ();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnImagen.setDisable(true);
                txtDescripcion.setEditable(false);
                cmbCategoria.setDisable(true);
                cmbTalla.setDisable(true);
                cmbMarca.setDisable(true);
                cargarDatos();
                break;
    }
    }
  
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarProducto(?,?,?,?,?,?)}");
            Producto registro = (Producto) tblProductos.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoCategoria(((Categoria)cmbCategoria.getSelectionModel().getSelectedItem()).getCodigoCategoria());
            registro.setCodigoMarca(((Marca)cmbMarca.getSelectionModel().getSelectedItem()).getCodigoMarca());
            registro.setCodigoTalla(((Talla)cmbTalla.getSelectionModel().getSelectedItem()).getCodigoTalla());
            registro.setImagen(txtImagen.getText());
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.setInt(2, registro.getCodigoProducto());
            procedimiento.setString(3, registro.getImagen());
            procedimiento.setInt(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getCodigoProducto());
            procedimiento.setInt(6, registro.getCodigoProducto());
            procedimiento.execute();
        }catch(SQLException e){
           e.printStackTrace();
        }
        
    }
    
    public void agregarImagen(){
        FileChooser seleccionarArchivo  = new FileChooser();
        File archivo = seleccionarArchivo.showOpenDialog(null);
        if (archivo != null){
            txtImagen.setText(archivo.getName());
            
        }
    }
    
    public void generarReporte(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
                tipoDeOperacion = operaciones.ACTUALIZAR;
                break;
                
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                tipoDeOperacion = operaciones.NINGUNO;
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnImagen.setDisable(true);
                txtDescripcion.setDisable(false);
                cmbCategoria.setDisable(true);
                cmbMarca.setDisable(true);
                cmbTalla.setDisable(true);
                break;
                
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("_NumeroDocumento",null);
        GenerarReporte.mostrarReporte("ReporteProducto.jasper", "Reporte de Productos ", parametros);
    }
    
    public void desactivarControles (){
        txtDescripcion.setEditable(false);
        cmbCategoria.setDisable(true);
        cmbTalla.setDisable(true);
        cmbMarca.setDisable(true);
        txtImagen.setDisable(false);
        btnImagen.setDisable(true);
        imgProducto.setImage(null);
    }
    
    public void activarControles (){
        txtDescripcion.setEditable(true);
        cmbCategoria.setDisable(false);
        cmbTalla.setDisable(false);
        cmbMarca.setDisable(false);
        btnImagen.setDisable(false);
    }
    
    public void limpiarControles(){
        txtDescripcion.setText("");
        cmbCategoria.getSelectionModel().clearSelection();
        cmbMarca.getSelectionModel().clearSelection();
        cmbTalla.getSelectionModel().clearSelection();
        txtImagen.setText("");
        
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

    public void setEscenarioPrincipal(Principal escenaPrincipal) {
        this.escenarioPrincipal = escenaPrincipal;
    }
    
       public void menuPrincipal(){
         escenarioPrincipal.menuPrincipal();
     
}
    
}

