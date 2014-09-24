package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.configuracion.System;
import modelo.maestros.Product;
import modelo.maestros.Salesmen;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CInactivarItem extends CGenerico {

	private static final long serialVersionUID = 3394240712277711246L;
	@Wire
	private Label lblCodigo;
	@Wire
	private Label lblDescripcion;
	@Wire
	private Label lblEstado;
	@Wire
	private Div botoneraInactivarItem;
	@Wire
	private Div divInactivarItem;
	@Wire
	private Radio rdoSI;
	@Wire
	private Radio rdoNo;
	@Wire
	private Div divCatalogoProducto;

	private String nombre;
	Catalogo<Product> catalogoProducto;
	String idItem = "";

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				nombre = (String) map.get("nombre");
				map.clear();
				map = null;
			}
		}
		// actualizarEstado();
		Botonera botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divInactivarItem, nombre, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				lblDescripcion.setValue("");
				lblCodigo.setValue("");
				lblEstado.setValue("");
				rdoNo.setChecked(false);
				rdoSI.setChecked(false);
				idItem = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					Product item = servicioProducto.buscar(idItem);

					if (rdoSI.isChecked())
						item.setEstado(true);
					else
						item.setEstado(false);
					servicioProducto.guardar(item);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
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
		botoneraInactivarItem.appendChild(botonera);
	}

	protected boolean validar() {
		if (idItem.equals("")) {
			msj.mensajeError(Mensaje.seleccionarItem);
			return false;
		} else {
			if (!rdoSI.isChecked() && !rdoNo.isChecked()) {
				msj.mensajeError(Mensaje.seleccionarEstado);
				return false;
			}
			else
				return true;
		}
	}

	@Listen("onClick = #btnBuscarItem")
	public void mostrarCatalogoItem() {
		final List<Product> productos = servicioProducto.buscarTodos();
		catalogoProducto = new Catalogo<Product>(divCatalogoProducto, "Items",
				productos, true, false, false, "Codigo", "Descripcion", "Marca") {

			@Override
			protected List<Product> buscar(List<String> valores) {

				List<Product> lista = new ArrayList<Product>();

				for (Product item : productos) {
					if (item.getProductId().startsWith(valores.get(0))
							&& item.getDescription().trim()
									.startsWith(valores.get(1))
							&& item.getBrand().trim()
									.startsWith(valores.get(2))) {
						lista.add(item);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Product item) {
				String[] registros = new String[3];
				registros[0] = item.getProductId();
				registros[1] = item.getDescription();
				registros[2] = item.getBrand();
				return registros;
			}
		};
		catalogoProducto.setParent(divCatalogoProducto);
		catalogoProducto.doModal();

	}

	@Listen("onSeleccion = #divCatalogoProducto")
	public void seleccionar() {
		Product item = catalogoProducto.objetoSeleccionadoDelCatalogo();
		idItem = item.getProductId();
		lblCodigo.setValue(item.getProductId());
		lblDescripcion.setValue(item.getDescription());
		if (item.isEstado())
			lblEstado.setValue("Activo");
		else
			lblEstado.setValue("Inactivo");

		catalogoProducto.setParent(null);
	}
}
