package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Order;
import modelo.maestros.Salesmen;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CConsulta extends CGenerico {

	@Wire
	private Div botoneraConsulta;
	@Wire
	private Div catalogoConsulta;
	@Wire
	private Textbox txtVendedor;
	@Wire
	private Textbox txtMarca;
	@Wire
	private Div divConsulta;
	@Wire
	private Div divCatalogoVendedor;
	@Wire
	private Button btnBuscarVendedor;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Combobox cmbStatus;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Row rowVendedor;
	@Wire
	private Row rowMarca;
	@Wire
	private Row rowStatus;

	Catalogo<Order> catalogo = null;
	Catalogo<Salesmen> catalogoVendedor;
	String idVendedor = "";
	String idMarca = "";
	String status = "";

	private List<String> listaProduct = new ArrayList<String>();
	private List<String> listaStatus = new ArrayList<String>();
	private List<Order> ordenes = new ArrayList<Order>();
	private String titulo = "";
	private String tipo = "";

	public String getTitulo() {
		return titulo;
	}

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				titulo = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}

		switch (titulo) {
		case "Ordenes General por Marcas":
			rowMarca.setVisible(true);
			rowStatus.setVisible(false);
			rowVendedor.setVisible(false);
			tipo = "marca";
			break;
		case "Ordenes por Vendedor":
			rowMarca.setVisible(false);
			rowStatus.setVisible(true);
			rowVendedor.setVisible(true);
			tipo = "vendedor";
			break;

		}
		mostrarCatalogo();

		listaProduct.add("TODAS");
		listaProduct.addAll(servicioProducto.buscarMarcas());
		cmbMarca.setModel(new ListModelList<String>(listaProduct));

		// = servicioOrden.buscarStatus();
		listaStatus.add("TODOS");
		listaStatus.add("PEN");
		listaStatus.add("PRO");
		cmbStatus.setModel(new ListModelList<String>(listaStatus));

		Botonera botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divConsulta, titulo, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {

				dtbDesde.setValue(fecha);
				dtbHasta.setValue(fecha);
				txtVendedor.setValue("");
				idVendedor = "";
				idMarca = "";
				cmbMarca.setValue("");
				cmbMarca.setTooltiptext("Seleccione una Marca");
				cmbStatus.setValue("");
				cmbStatus.setTooltiptext("Seleccione un Status");
				List<Order> cuposb = new ArrayList<Order>();
				catalogo.actualizarLista(cuposb, false);
			}

			@Override
			public void guardar() {
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
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraConsulta.appendChild(botonera);
	}

	public void mostrarCatalogo() {

		ordenes = new ArrayList<Order>();
		catalogo = new Catalogo<Order>(catalogoConsulta, "", ordenes, false,
				false, true, "Nro.", "Fecha", "Fecha Req.", "Vendedor",
				"Status") {

			@Override
			protected List<Order> buscar(List<String> valores) {

				List<Order> ordenFiltro = new ArrayList<Order>();

				for (Order orden : ordenes) {
					if (String.valueOf(orden.getOrderId()).toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& orden.getOrderDate().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& orden.getRequiredDate().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& servicioVendedor.buscar(orden.getSalesmanId())
									.getName().toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& orden.getStatus().toLowerCase()
									.contains(valores.get(4).toLowerCase())) {
						ordenFiltro.add(orden);
					}
				}
				return ordenFiltro;
			}

			@Override
			protected String[] crearRegistros(Order objeto) {
				String[] registros = new String[5];
				registros[0] = String.valueOf(objeto.getOrderId());
				registros[1] = objeto.getOrderDate();
				registros[2] = objeto.getRequiredDate();
				registros[3] = servicioVendedor.buscar(objeto.getSalesmanId())
						.getName();
				registros[4] = objeto.getStatus();
				return registros;
			}

		};
		catalogo.setParent(catalogoConsulta);
	}

	@Listen("onClick = #btnBuscarVendedor")
	public void mostrarCatalogoVendedor() {
		final List<Salesmen> vendedores = new ArrayList<Salesmen>();
		Salesmen vendedorTodo = new Salesmen();
		vendedorTodo.setSalesmanId("TODOS");
		vendedorTodo.setRegion("TODOS");
		vendedorTodo.setName("TODOS");
		vendedores.add(vendedorTodo);
		vendedores.addAll(servicioVendedor.buscarActivos());
		catalogoVendedor = new Catalogo<Salesmen>(divCatalogoVendedor,
				"Vendedores", vendedores, true, false, false, "Codigo",
				"Nombre", "Region") {

			@Override
			protected List<Salesmen> buscar(List<String> valores) {

				List<Salesmen> lista = new ArrayList<Salesmen>();

				for (Salesmen vendedor : vendedores) {
					if (vendedor.getSalesmanId().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& vendedor.getName().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& vendedor.getRegion().toLowerCase()
									.contains(valores.get(2).toLowerCase())) {
						lista.add(vendedor);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(Salesmen vendedor) {
				String[] registros = new String[3];
				registros[0] = vendedor.getSalesmanId();
				registros[1] = vendedor.getName();
				registros[2] = vendedor.getRegion();
				return registros;
			}
		};
		catalogoVendedor.setParent(divCatalogoVendedor);
		catalogoVendedor.doModal();

	}

	@Listen("onSeleccion = #divCatalogoVendedor")
	public void seleccionar() {
		Salesmen vendedor = catalogoVendedor.objetoSeleccionadoDelCatalogo();
		txtVendedor.setValue(vendedor.getName());
		idVendedor = vendedor.getSalesmanId();
		catalogoVendedor.setParent(null);
	}

	@Listen("onDoubleClick = #catalogoConsulta")
	public void seleccionarItem() {

		if (catalogo.objetoSeleccionadoDelCatalogo() != null) {
			Order order = catalogo.objetoSeleccionadoDelCatalogo();
			catalogo.limpiarSeleccion();

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("orden", order);
			Sessions.getCurrent().setAttribute("detalle", map);
			Window window = (Window) Executions.createComponents(
					"/vistas/transacciones/VDetalleOrden.zul", null, null);
			window.doModal();
		}
	}

	@Listen("onClick = #btnRefrescar")
	public void refrescar() {
		if (tipo.equals("marca")) {
			if (!idMarca.equals("")) {
				if (validarFecha()) {
					if (idMarca.equals("TODAS")) {
						ordenes = servicioOrden.buscarEntreFechas(
								formatoFechaRara.format(dtbDesde.getValue()),
								formatoFechaRara.format(dtbHasta.getValue()));
					} else
						ordenes = servicioOrden.buscarPorMarcaYFecha(idMarca,
								formatoFechaRara.format(dtbDesde.getValue()),
								formatoFechaRara.format(dtbHasta.getValue()));

					if (ordenes.isEmpty())
						msj.mensajeInformacion(Mensaje.noHayRegistros);
					catalogo.actualizarLista(ordenes, false);
				}
			} else
				msj.mensajeError(Mensaje.seleccionarMarca);

		} else {
			if (tipo.equals("vendedor")) {
				if (!idVendedor.equals("")) {
					if (!status.equals("")) {
						if (validarFecha()) {
							String desde = formatoFechaRara.format(dtbDesde
									.getValue());
							String hasta = formatoFechaRara.format(dtbHasta
									.getValue());
							if (status.equals("TODOS")
									&& idVendedor.equals("TODOS")) {
								ordenes = servicioOrden.buscarEntreFechas(
										desde, hasta);
							} else if (status.equals("TODOS")
									&& !idVendedor.equals("TODOS")) {
								ordenes = servicioOrden
										.buscarPorVendedorYFecha(idVendedor,
												desde, hasta);
							} else if (!status.equals("TODOS")
									&& idVendedor.equals("TODOS")) {
								ordenes = servicioOrden.buscarPorStatusYFecha(
										status, desde, hasta);
							} else
								ordenes = servicioOrden
										.buscarPorVendedorStatusYFecha(
												idVendedor, status, desde,
												hasta);

							if (ordenes.isEmpty())
								msj.mensajeInformacion(Mensaje.noHayRegistros);
							catalogo.actualizarLista(ordenes, false);
						}
					} else
						msj.mensajeError(Mensaje.seleccionarStatus);
				} else
					msj.mensajeError(Mensaje.seleccionarVendedor);
			}
		}
	}

	private boolean validarFecha() {
		if (dtbDesde.getValue().after(dtbHasta.getValue())) {
			msj.mensajeError(Mensaje.fechaPosterior);
			return false;
		} else
			return true;
	}

	@Listen("onSelect = #cmbMarca")
	public void marcar() {
		idMarca = cmbMarca.getValue();
	}

	@Listen("onSelect = #cmbStatus")
	public void marcarStatus() {
		status = cmbStatus.getValue();
	}

	// public void recibirLista(Catalogo<Cupo> catalogo2, List<Cupo> cupos2,
	// String idVendedor2, String idMarca2, SCupo servicioCupo2,
	// SProducto servicioProducto2) {
	// catalogo = catalogo2;
	// cupos = cupos2;
	// idVendedor = idVendedor2;
	// idMarca = idMarca2;
	// servicioCupo = servicioCupo2;
	// servicioProducto = servicioProducto2;
	// refrescar();
	// }

}
