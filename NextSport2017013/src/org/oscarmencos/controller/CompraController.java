
package org.oscarmencos.controller;
import java.util.Date;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;
import eu.schudt.javafx.controls.calendar.DatePicker;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.oscarmencos.bean.Compra;
import org.oscarmencos.bean.Proveedor;
import org.oscarmencos.db.Conexion;
import org.oscarmencos.sistema.Principal;

public class CompraController implements Initializable{

    private enum operaciones {NUEVO,GUARDAR,EDITAR,ELIMINAR,CAMCELAR,NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Compra> listaCompra;
    private ObservableList<Proveedor> listaProveedor;
    private DatePicker fecha;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private GridPane grdFecha;
    @FXML private ComboBox cmbProveedor;
    @FXML private TableView tblCompras;
    @FXML private TextField txtNumeroDocumento;
    @FXML private TextField txtDescripcion;
    @FXML private TableColumn colNumeroDocumento;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colTotal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbProveedor.setItems(getProveedores());
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        fecha.getStylesheets().add("/org/oscarmencos/resource/DatePicker.css");
        grdFecha.add(fecha,0,0);
    }
    
    public void cargarDatos(){
        tblCompras.setItems(getCompras());
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("numeroDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Proveedor,Date>("fecha"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("total"));
    }
    
    public ObservableList<Proveedor>getProveedores(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProveedores}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
            lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                    resultado.getString("contactoPrincipal"),
                    resultado.getString("paginaWeb"),
                    resultado.getString("direccion"),
                    resultado.getString("nit"),
                    resultado.getString("razonSocial")));    
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaProveedor = FXCollections.observableList(lista);
    }
    
    public ObservableList<Compra>getCompras(){
        ArrayList<Compra> lista = new ArrayList<Compra>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCompras}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Compra(resultado.getInt("numeroDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDate("fecha"),
                        resultado.getDouble("total"),
                        resultado.getInt("codigoProveedor"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb"),
                        resultado.getString("direccion"),
                        resultado.getString("nit"),
                        resultado.getString("razonSocial")));
            }
        }catch(Exception e){
            e.printStackTrace();
            }
           
        
        return listaCompra = FXCollections.observableList(lista);
    }
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                txtNumeroDocumento.setEditable(false);
                txtDescripcion.setEditable(false);
                grdFecha.setDisable(false);
                cmbProveedor.setDisable(false);
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                txtNumeroDocumento.setEditable(false);
                txtDescripcion.setEditable(false);
                grdFecha.setDisable(true);
                cmbProveedor.setDisable(true);
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                guardar ();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        try {
            Compra nuevaCompra = new Compra();
            nuevaCompra.setFecha(fecha.getSelectedDate());
            nuevaCompra.setDescripcion(txtDescripcion.getText());
            Proveedor proveedor = (Proveedor)cmbProveedor.getSelectionModel().getSelectedItem();
            nuevaCompra.setCodigoProveedor(proveedor.getCodigoProveedor());
            nuevaCompra.setNit(proveedor.getNit());
            nuevaCompra.setPaginaWeb(proveedor.getPaginaWeb());
            nuevaCompra.setContactoPrincipal(proveedor.getContactoPrincipal());
            nuevaCompra.setDireccion(proveedor.getDireccion());
            nuevaCompra.setRazonSocial(proveedor.getRazonSocial());
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{sp_AgregarCompra(?,?,?,?)");
            procedimiento.setString(1, nuevaCompra.getDescripcion());
            procedimiento.setDate(2, new java.sql.Date(nuevaCompra.getFecha().getTime()));
            procedimiento.setDouble(3, nuevaCompra.getTotal());
            procedimiento.setInt(4, nuevaCompra.getCodigoProveedor());
            procedimiento.execute();
            listaCompra.add(nuevaCompra);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void limpiarControles(){
        txtNumeroDocumento.setText("");
        txtDescripcion.setText("");
        cmbProveedor.setValue("");
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
        public void detalleCompra(){
            if(tblCompras.getSelectionModel().getSelectedItem() != null){
                escenarioPrincipal.ventanaDetalleCompras((Compra)tblCompras.getSelectionModel().getSelectedItem());
            }else
                JOptionPane.showMessageDialog(null, "Debe seleccionar una compra","sd",0);
        }
           public void menuPrincipal(){
         escenarioPrincipal.menuPrincipal();
     
}
}
