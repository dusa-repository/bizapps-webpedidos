package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Cupo;
import modelo.maestros.CupoPK;
import modelo.maestros.Product;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CAsignacion extends CGenerico {
	
	@Wire
	private Div botoneraAsignacion;
	@Wire
	private Label lblProducto;
	@Wire
	private Window wdwAsignacion;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Spinner spnCantidad;
	@Wire
	private Spinner spnConsumido;
	@Wire
	private Label lblVendedor;
	@Wire
	private Label lblMarca;
	
	String idProducto ="";
	String idVendedor ="";
	String idMarca ="";
	private CCupo controlador = new CCupo();
	Catalogo<Cupo> catalogo;
	List<Cupo> cupos = new ArrayList<Cupo>();

	@Override
	public void inicializar() throws IOException {
		
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("asignacion");
		if (map != null) {
			if (map.get("idProducto") != null) {
				idProducto =  (String)  map.get("idProducto");
				idVendedor =  (String)  map.get("idVendedor");
				idMarca =  (String)  map.get("idMarca");
				catalogo = (Catalogo<Cupo>) map.get("catalogo");
				cupos =  (List<Cupo>) map.get("lista");
				int cantidad =  (Integer)  map.get("cantidad");
				int consumido =  (Integer)  map.get("consumido");		
				String vendedor =  (String)  map.get("vendedor");
				Product item = servicioProducto.buscar(idProducto); 
				lblProducto.setValue(idProducto+" ,   "+item.getDescription());
				lblMarca.setValue(idMarca);
				lblVendedor.setValue(vendedor);
				spnCantidad.setValue(cantidad);
				spnConsumido.setValue(consumido);
				map.clear();
				map = null;
			}
		}
		
		Botonera botonera = new Botonera() {
			
			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void salir() {
				wdwAsignacion.onClose();
				
			}
			
			@Override
			public void reporte() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void limpiar() {
			dtbDesde.setValue(fecha);
			dtbHasta.setValue(fecha);
			spnCantidad.setValue(0);
			spnConsumido.setValue(0);
			
				
			}
			
			@Override
			public void guardar() {
			
				if(validar())
				{
					Date fechaDesde = dtbDesde.getValue();
					Date fechaHasta = dtbHasta.getValue();
					String fechaD = String.valueOf(formatoFechaRara.format(fechaDesde));
					String fechaH = String.valueOf(formatoFechaRara.format(fechaHasta));
					int cantidad = spnCantidad.getValue();
					int consumido = spnConsumido.getValue();
					Product item = servicioProducto.buscar(idProducto); 
					
					Cupo cupo = new Cupo();
					cupo.setCantidad(cantidad);
					cupo.setConsumido(consumido);
					cupo.setRestante(cantidad-consumido);
					cupo.setDesde(fechaD);
					cupo.setHasta(fechaH);
					cupo.setDescription(item.getDescription());
					
					CupoPK pk = new CupoPK();
					pk.setProducto(idProducto);
					pk.setVendedor(idVendedor);
					pk.setMarca(idMarca);
					cupo.setId(pk);
					
					servicioCupo.guardar(cupo);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					salir();
					controlador.recibirLista(catalogo, cupos, idVendedor, idMarca, servicioCupo, servicioProducto);
					
				}
				
			}
			
			@Override
			public void eliminar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void buscar() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void ayuda() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void annadir() {
				// TODO Auto-generated method stub
				
			}
		};
		
		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botoneraAsignacion.appendChild(botonera);
	}
	
	
	public boolean validar()
	{
		if (spnCantidad.getText().compareTo("") == 0
				|| spnCantidad.getText().compareTo("") == 0
				|| dtbDesde.getText().compareTo("") == 0
				|| dtbHasta.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

}
