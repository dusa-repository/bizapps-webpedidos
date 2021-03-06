package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import modelo.maestros.Cupo;
import modelo.maestros.CupoPK;
import modelo.maestros.Product;
import modelo.maestros.Salesmen;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import servicio.maestros.SProducto;
import servicio.transacciones.SCupo;

import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CCupo extends CGenerico {

	@Wire
	private Div botoneraCupo;
	@Wire
	private Div catalogoCupo;
	@Wire
	private Textbox txtVendedor;
	@Wire
	private Textbox txtMarca;
	@Wire
	private Div divCupo;
	@Wire
	private Div divCatalogoVendedor;
	@Wire
	private Button btnBuscarVendedor;
	@Wire
	private Button btnGuardarArchivo;
	@Wire
	private Combobox cmbMarca;
	Catalogo<Cupo> catalogo = null;
	Catalogo<Salesmen> catalogoVendedor;
	String idVendedor = "";
	String idMarca = "";
	private List<String> listaProduct = new ArrayList<String>();
	List<Cupo> cupos = new ArrayList<Cupo>();
	@Wire
	private Listbox ltbMarcas;
	@Wire
	private Listbox ltbMarcasAgregadas;
	@Wire
	private Listbox ltbItems;
	@Wire
	private Listbox ltbItemsAgregados;
	@Wire
	private Label lblNombreArchivo;
	@Wire
	private org.zkoss.zul.Row rowArchivo;
	@Wire
	private Textbox txtBuscadorProducto;
	@Wire
	private Textbox txtBuscadorRestringido;
	private Media mediaArchivo;
	private String errorLongitud = "La siguiente ubicacion excede el limite establecido de longitud:";
	private String valorNoEncontrado = "Valor no encontrado, ";
	private String archivoConError = "Existe un error en el siguiente archivo adjunto: ";
	private String archivoVacio = "El siguiente archivo no posee registros, por lo tanto no fue importado.";

	List<String> marcasDisponibles = new ArrayList<String>();

	List<Cupo> marcasFinales = new ArrayList<Cupo>();

	List<Product> itemsDisponibles = new ArrayList<Product>();

	List<Cupo> itemsRestringidos = new ArrayList<Cupo>();

	List<String> marcasRestringidas = new ArrayList<String>();
	private boolean errorGeneral = false;
	Buscar<Product> buscarProductos;
	Buscar<Cupo> buscarRestringidos;

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
		buscadorProductos();
		buscadorRestringidos();
		mostrarCatalogo();
		// listaProduct = servicioProducto.buscarMarcas();
		List<String> lista = new ArrayList<String>();
		lista.add("TODAS");
		lista.addAll(servicioProducto.buscarMarcas());
		cmbMarca.setModel(new ListModelList<String>(lista));

		llenarListas();

		Botonera botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divCupo, titulo, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				errorGeneral = false;
				marcasFinales.clear();
				llenarListas();
				txtVendedor.setValue("");
				idVendedor = "";
				idMarca = "";
				cmbMarca.setValue("");
				cmbMarca.setTooltiptext("Seleccione una Marca");
				List<Cupo> cuposb = new ArrayList<Cupo>();
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
		botoneraCupo.appendChild(botonera);
	}

	private void buscadorRestringidos() {
		buscarRestringidos = new Buscar<Cupo>(ltbItemsAgregados,
				txtBuscadorRestringido) {
			@Override
			protected List<Cupo> buscar(String valor) {
				List<Cupo> cuposFiltrados = new ArrayList<Cupo>();
				List<Cupo> cuposJ = servicioCupo.filtroCodigoProducto(valor);
				for (int i = 0; i < itemsRestringidos.size(); i++) {
					Cupo cupi = itemsRestringidos.get(i);
					for (int j = 0; j < cuposJ.size(); j++) {
						if (cupi.getId().getProducto()
								.equals(cuposJ.get(j).getId().getProducto()))
							cuposFiltrados.add(cupi);
					}
				}
				return cuposFiltrados;
			}
		};
	}

	private void buscadorProductos() {
		buscarProductos = new Buscar<Product>(ltbItems, txtBuscadorProducto) {
			@Override
			protected List<Product> buscar(String valor) {
				List<Product> productosFiltrados = new ArrayList<Product>();
				List<Product> productosJ = servicioProducto.filtroCodigo(valor);
				for (int i = 0; i < itemsDisponibles.size(); i++) {
					Product item = itemsDisponibles.get(i);
					for (int j = 0; j < productosJ.size(); j++) {
						if (item.getProductId().equals(
								productosJ.get(j).getProductId()))
							productosFiltrados.add(item);
					}
				}
				return productosFiltrados;
			}
		};
	}

	public void mostrarCatalogo() {
		cupos = new ArrayList<Cupo>();
		catalogo = new Catalogo<Cupo>(catalogoCupo, "", cupos, false, false,
				true, "Codigo", "Descripcion", "Desde", "Hasta", "Cantidad",
				"Consumido", "Disponible") {

			@Override
			protected List<Cupo> buscar(List<String> valores) {

				List<Cupo> cuposFiltro = new ArrayList<Cupo>();

				for (Cupo cupo : cupos) {
					if (cupo.getId().getProducto().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& servicioProducto
									.buscar(cupo.getId().getProducto())
									.getDescription().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& cupo.getDesde().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& cupo.getHasta().toLowerCase()
									.contains(valores.get(3).toLowerCase())
							&& String.valueOf(cupo.getCantidad()).toLowerCase()
									.contains(valores.get(4).toLowerCase())
							&& String.valueOf(cupo.getConsumido()).contains(
									valores.get(5).toLowerCase())
							&& String.valueOf(cupo.getRestante()).contains(
									valores.get(6))) {
						cuposFiltro.add(cupo);
					}
				}
				return cuposFiltro;
			}

			@Override
			protected String[] crearRegistros(Cupo objeto) {
				String[] registros = new String[7];
				registros[0] = objeto.getId().getProducto();
				registros[1] = servicioProducto.buscar(
						objeto.getId().getProducto()).getDescription();
				registros[2] = objeto.getDesde();
				registros[3] = objeto.getHasta();
				registros[4] = String.valueOf(objeto.getCantidad());
				registros[5] = String.valueOf(objeto.getConsumido());
				registros[6] = String.valueOf(objeto.getRestante());
				return registros;
			}

		};
		catalogo.setParent(catalogoCupo);
	}

	@Listen("onClick = #btnBuscarVendedor")
	public void mostrarCatalogoVendedor() {
		final List<Salesmen> vendedores = servicioVendedor
				.buscarActivos();
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

	@Listen("onDoubleClick = #catalogoCupo")
	public void seleccionarItem() {

		if (catalogo.objetoSeleccionadoDelCatalogo() != null) {
			Cupo cupo = catalogo.objetoSeleccionadoDelCatalogo();
			idMarca = cupo.getId().getMarca();
			catalogo.limpiarSeleccion();
			if (!idVendedor.equals("") || !idMarca.equals("")) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("idProducto", cupo.getId().getProducto());
				map.put("idVendedor", idVendedor);
				map.put("idMarca", idMarca);
				map.put("cantidad", cupo.getCantidad());
				map.put("consumido", cupo.getConsumido());
				map.put("desde", cupo.getDesde());
				map.put("hasta", cupo.getHasta());
				map.put("catalogo", catalogo);
				map.put("lista", cupos);
				map.put("vendedor", servicioVendedor.buscar(idVendedor)
						.getName());
				Sessions.getCurrent().setAttribute("asignacion", map);
				Window window = (Window) Executions.createComponents(
						"/vistas/transacciones/VAsignacion.zul", null, null);
				window.doModal();
			} else
				msj.mensajeAlerta(Mensaje.seleccionarMarcaYVendedor);
		}
	}

	@Listen("onClick = #btnRefrescar")
	public void refrescar() {
		if (!idVendedor.equals("")) {
			if (!idMarca.equals("")) {
				List<String> itemsFaltan = new ArrayList<String>();
				if (!idMarca.equals("TODAS"))
					cupos = servicioCupo.buscarPorVendedoryMarca(idVendedor,
							idMarca);
				else
					cupos = servicioCupo.buscarCuposVendedor(idVendedor);

				List<Product> items = servicioProducto.buscarPorMarcaActivo(idMarca);
				if (idMarca.equals("TODAS"))
					items = servicioProducto.buscarActivos();
				for (int i = 0; i < items.size(); i++) {
					String id = items.get(i).getProductId();
					itemsFaltan.add(id);
				}
				if (!cupos.isEmpty()) {
					for (int j = 0; j < itemsFaltan.size(); j++) {
						String id = itemsFaltan.get(j);
						for (int i = 0; i < cupos.size(); i++) {
							if (id.equals(cupos.get(i).getId().getProducto())) {
								itemsFaltan.remove(id);
								j--;
								i = cupos.size();
							}
						}
					}
				}
				for (int i = 0; i < itemsFaltan.size(); i++) {
					Product item = servicioProducto.buscar(itemsFaltan.get(i));
					Cupo cupoNew = new Cupo();
					cupoNew.setCantidad(0);
					cupoNew.setConsumido(0);
					cupoNew.setRestante(0);
					cupoNew.setDescription(item.getDescription());
					cupoNew.setDesde("");
					cupoNew.setHasta("");
					CupoPK pk = new CupoPK();
					pk.setVendedor(idVendedor);
					pk.setMarca(idMarca);
					pk.setProducto(itemsFaltan.get(i));
					cupoNew.setId(pk);
					cupos.add(cupoNew);
				}
				catalogo.actualizarLista(cupos, false);
			} else
				msj.mensajeError(Mensaje.seleccionarMarca);
		} else
			msj.mensajeError(Mensaje.seleccionarVendedor);
	}

	@Listen("onSelect = #cmbMarca")
	public void marcar() {
		idMarca = cmbMarca.getValue();
	}

	public void recibirLista(Catalogo<Cupo> catalogo2, List<Cupo> cupos2,
			String idVendedor2, String idMarca2, SCupo servicioCupo2,
			SProducto servicioProducto2) {
		catalogo = catalogo2;
		cupos = cupos2;
		idVendedor = idVendedor2;
		idMarca = idMarca2;
		servicioCupo = servicioCupo2;
		servicioProducto = servicioProducto2;
		refrescar();
	}

	public void llenarListas() {
		marcasDisponibles = servicioProducto.buscarMarcas();

		itemsDisponibles = servicioProducto.buscarActivos();

		itemsRestringidos = servicioCupo.buscarItemsRestringidos();

		marcasRestringidas = servicioCupo.buscarMarcasConCero();

		List<Product> itemsPorMarca = new ArrayList<Product>();

		for (int i = 0; i < marcasRestringidas.size(); i++) {
			itemsPorMarca = servicioProducto.buscarPorMarca(marcasRestringidas
					.get(i));
			for (int j = 0; j < itemsPorMarca.size(); j++) {
				CupoPK pk = new CupoPK();
				pk.setMarca(marcasRestringidas.get(i));
				pk.setProducto(itemsPorMarca.get(j).getProductId());
				pk.setVendedor("0");
				if (servicioCupo.buscar(pk) == null) {
					marcasRestringidas.remove(i);
					i--;
					j = itemsPorMarca.size();
				}
			}

		}

		for (int i = 0; i < marcasDisponibles.size(); i++) {
			for (int j = 0; j < marcasRestringidas.size(); j++) {
				if (marcasDisponibles.get(i).equals(marcasRestringidas.get(j))) {
					marcasDisponibles.remove(i);
					i--;
					j = marcasRestringidas.size();
				}
			}
		}

		for (int i = 0; i < marcasRestringidas.size(); i++) {
			Cupo cupoNew = new Cupo();
			cupoNew.setCantidad(0);
			cupoNew.setConsumido(0);
			cupoNew.setRestante(0);
			cupoNew.setDescription("");
			cupoNew.setDesde(servicioCupo
					.buscarPorVendedoryMarca("0", marcasRestringidas.get(i))
					.get(0).getDesde());
			cupoNew.setHasta(servicioCupo
					.buscarPorVendedoryMarca("0", marcasRestringidas.get(i))
					.get(0).getHasta());
			CupoPK pk = new CupoPK();
			pk.setVendedor("");
			pk.setMarca(marcasRestringidas.get(i));
			pk.setProducto("");
			cupoNew.setId(pk);
			marcasFinales.add(cupoNew);
		}

		ltbMarcas.setModel(new ListModelList<String>(marcasDisponibles));
		ltbMarcasAgregadas.setModel(new ListModelList<Cupo>(marcasFinales));

		for (int i = 0; i < itemsDisponibles.size(); i++) {
			for (int j = 0; j < itemsRestringidos.size(); j++) {
				if (itemsDisponibles.get(i).getProductId()
						.equals(itemsRestringidos.get(j).getId().getProducto())) {
					itemsDisponibles.remove(i);
					j = itemsRestringidos.size();
					i--;
				}
			}
		}

		ltbItems.setModel(new ListModelList<Product>(itemsDisponibles));
		ltbItemsAgregados.setModel(new ListModelList<Cupo>(itemsRestringidos));

		listasMultiples();
	}

	@Listen("onClick = #pasar1Item")
	public void derechaItem() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbItems.getItems();
		ltbItemsAgregados.renderAll();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Product item = listItem.get(i).getValue();
					itemsDisponibles.remove(item);
					Cupo cupoNew = new Cupo();
					cupoNew.setCantidad(0);
					cupoNew.setConsumido(0);
					cupoNew.setRestante(0);
					cupoNew.setDescription("");
					cupoNew.setDesde(formatoFechaRara.format(fechaHora));
					cupoNew.setHasta(formatoFechaRara.format(fechaHora));
					CupoPK pk = new CupoPK();
					pk.setVendedor("");
					pk.setMarca(item.getBrand());
					pk.setProducto(item.getProductId());
					cupoNew.setId(pk);
					itemsRestringidos.clear();
					for (int j = 0; j < ltbItemsAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbItemsAgregados
								.getItemAtIndex(j);
						String idItem = ((Textbox) ((listItemj.getChildren()
								.get(4))).getFirstChild()).getValue();
						Date fechaDesde = new Date();
						Date fechaHasta = new Date();
						String desde = "";
						String hasta = "";
						if (((Datebox) ((listItemj.getChildren().get(2)))
								.getFirstChild()).getValue() != null) {
							fechaDesde = ((Datebox) ((listItemj.getChildren()
									.get(2))).getFirstChild()).getValue();
							desde = formatoFechaRara.format(fechaDesde);

						}
						if (((Datebox) ((listItemj.getChildren().get(3)))
								.getFirstChild()).getValue() != null) {
							fechaHasta = ((Datebox) ((listItemj.getChildren()
									.get(3))).getFirstChild()).getValue();
							hasta = formatoFechaRara.format(fechaHasta);

						}
						Product produc = servicioProducto.buscar(idItem);
						Cupo cupo = new Cupo();
						cupo.setCantidad(0);
						cupo.setConsumido(0);
						cupo.setRestante(0);
						cupo.setDescription(produc.getDescription());
						cupo.setDesde(desde);
						cupo.setHasta(hasta);
						CupoPK pk2 = new CupoPK();
						pk2.setVendedor("");
						pk2.setMarca(produc.getBrand());
						pk2.setProducto(idItem);
						cupo.setId(pk2);
						itemsRestringidos.add(cupo);
					}
					itemsRestringidos.add(cupoNew);
					ltbItemsAgregados.setModel(new ListModelList<Cupo>(
							itemsRestringidos));
					ltbItemsAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbItems.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Item")
	public void izquierdaItem() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbItemsAgregados.getItems();
		ltbItems.renderAll();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Cupo cupo = listItem2.get(i).getValue();
					itemsRestringidos.remove(cupo);
					itemsDisponibles.add(servicioProducto.buscar(cupo.getId()
							.getProducto()));
					ltbItems.setModel(new ListModelList<Product>(
							itemsDisponibles));
					listitemEliminar.add(listItem2.get(i));
					for (int j = 0; j < ltbMarcasAgregadas.getItemCount(); j++) {
						Listitem listItemj = ltbMarcasAgregadas
								.getItemAtIndex(j);
						Cupo cupo2 = listItemj.getValue();
						if (cupo.getId().getMarca()
								.equals(cupo2.getId().getMarca())) {
							ltbMarcasAgregadas.removeItemAt(j);
							j = ltbMarcasAgregadas.getItemCount();
							marcasFinales.remove(cupo2);
							marcasRestringidas.remove(cupo2.getId().getMarca());
							marcasDisponibles.add(cupo2.getId().getMarca());
							ltbMarcas.setModel(new ListModelList<String>(
									marcasDisponibles));
						}
					}
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbItemsAgregados.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar1Marca")
	public void derechaEspecialista() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbMarcas.getItems();
		ltbMarcasAgregadas.renderAll();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					String marca = listItem.get(i).getValue();
					marcasDisponibles.remove(marca);
					Cupo cupoNew = new Cupo();
					cupoNew.setCantidad(0);
					cupoNew.setConsumido(0);
					cupoNew.setRestante(0);
					cupoNew.setDescription("");
					cupoNew.setDesde(formatoFechaRara.format(fechaHora));
					cupoNew.setHasta(formatoFechaRara.format(fechaHora));
					CupoPK pk = new CupoPK();
					pk.setVendedor("");
					pk.setMarca(marca);
					pk.setProducto("");
					cupoNew.setId(pk);
					marcasFinales.clear();
					for (int j = 0; j < ltbMarcasAgregadas.getItemCount(); j++) {
						Listitem listItemj = ltbMarcasAgregadas
								.getItemAtIndex(j);
						String marcaj = ((Textbox) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						Date fechaDesde = new Date();
						Date fechaHasta = new Date();
						String desde = "";
						String hasta = "";
						if (((Datebox) ((listItemj.getChildren().get(1)))
								.getFirstChild()).getValue() != null) {
							fechaDesde = ((Datebox) ((listItemj.getChildren()
									.get(1))).getFirstChild()).getValue();
							desde = formatoFechaRara.format(fechaDesde);

						}
						if (((Datebox) ((listItemj.getChildren().get(2)))
								.getFirstChild()).getValue() != null) {
							fechaHasta = ((Datebox) ((listItemj.getChildren()
									.get(2))).getFirstChild()).getValue();
							hasta = formatoFechaRara.format(fechaHasta);

						}
						Cupo cupo = new Cupo();
						cupo.setCantidad(0);
						cupo.setConsumido(0);
						cupo.setRestante(0);
						cupo.setDescription("");
						cupo.setDesde(desde);
						cupo.setHasta(hasta);
						CupoPK pk2 = new CupoPK();
						pk2.setVendedor("");
						pk2.setMarca(marcaj);
						pk2.setProducto("");
						cupo.setId(pk2);
						marcasFinales.add(cupo);
					}
					marcasFinales.add(cupoNew);
					ltbMarcasAgregadas.setModel(new ListModelList<Cupo>(
							marcasFinales));
					ltbMarcasAgregadas.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbMarcas.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Marca")
	public void izquierdaEspecialista() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbMarcasAgregadas.getItems();
		ltbMarcas.renderAll();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Cupo cupo = listItem2.get(i).getValue();
					marcasFinales.remove(cupo);
					marcasDisponibles.add(cupo.getId().getMarca());
					ltbMarcas.setModel(new ListModelList<String>(
							marcasDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbMarcasAgregadas.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	public void listasMultiples() {

		ltbItems.setMultiple(false);
		ltbItems.setCheckmark(false);
		ltbItems.setMultiple(true);
		ltbItems.setCheckmark(true);

		ltbItemsAgregados.setMultiple(false);
		ltbItemsAgregados.setCheckmark(false);
		ltbItemsAgregados.setMultiple(true);
		ltbItemsAgregados.setCheckmark(true);

		ltbMarcas.setMultiple(false);
		ltbMarcas.setCheckmark(false);
		ltbMarcas.setMultiple(true);
		ltbMarcas.setCheckmark(true);

		ltbMarcasAgregadas.setMultiple(false);
		ltbMarcasAgregadas.setCheckmark(false);
		ltbMarcasAgregadas.setMultiple(true);
		ltbMarcasAgregadas.setCheckmark(true);
	}

	@Listen("onClick = #btnLimpiarVendedor")
	public void limpiarVendedor() {

		if (!idVendedor.equals("")) {
			Salesmen vendedor = servicioVendedor.buscar(idVendedor);
			Messagebox.show(
					"�Desea limpiar los cupos del Vendedor "
							+ vendedor.getName() + " ?", "Alerta",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								List<Cupo> eliminarLista = servicioCupo
										.buscarCuposVendedor(idVendedor);
								servicioCupo.eliminarVarios(eliminarLista);
								msj.mensajeInformacion(Mensaje.limpiado);
								if (cmbMarca.getValue().compareTo("") != 0)
									refrescar();
							}
						}
					});
		} else
			msj.mensajeError(Mensaje.seleccionarVendedor);

	}

	@Listen("onClick = #btnLimpiarMarca")
	public void limpiarMarca() {
		if (cmbMarca.getValue().compareTo("") != 0) {

			idMarca = cmbMarca.getValue();
			Messagebox.show("�Desea limpiar los Cupos de la Marca " + idMarca
					+ " ?", "Alerta", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								List<Cupo> eliminarLista = servicioCupo
										.buscarCuposMarca(idMarca);
								servicioCupo.eliminarVarios(eliminarLista);
								msj.mensajeInformacion(Mensaje.limpiado);
								if (!idVendedor.equals(""))
									refrescar();
							}
						}
					});
		} else
			msj.mensajeError(Mensaje.seleccionarMarca);
	}

	@Listen("onClick = #btnLimpiarTodo")
	public void limpiarTodo() {

		Messagebox.show("�Desea limpiar todos los Cupos?", "Alerta",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							// List<Cupo> eliminarLista = servicioCupo
							// .buscarCuposActivos();
							List<Cupo> eliminarLista = servicioCupo
									.buscarTodos();
							servicioCupo.eliminarVarios(eliminarLista);
							llenarListas();
							msj.mensajeInformacion(Mensaje.limpiado);
							if (!idVendedor.equals("")
									&& cmbMarca.getValue().compareTo("") != 0)
								refrescar();
						}
					}
				});
	}

	public void guardarMarcas() {
		List<Cupo> guardarLista = new ArrayList<Cupo>();

		ltbMarcasAgregadas.renderAll();

		List<Cupo> eliminar = new ArrayList<Cupo>();

		for (int i = 0; i < marcasRestringidas.size(); i++) {
			List<Product> productos = servicioProducto
					.buscarPorMarca(marcasRestringidas.get(i));

			for (int j = 0; j < productos.size(); j++) {
				CupoPK pk = new CupoPK();
				pk.setMarca(marcasRestringidas.get(i));
				pk.setProducto(productos.get(j).getProductId());
				pk.setVendedor("0");
				Cupo cupo = servicioCupo.buscar(pk);
				if (cupo != null)
					eliminar.add(cupo);
			}
		}
		servicioCupo.eliminarVarios(eliminar);

		for (int j = 0; j < ltbMarcasAgregadas.getItemCount(); j++) {
			Listitem listItemj = ltbMarcasAgregadas.getItemAtIndex(j);
			String marcaj = ((Textbox) ((listItemj.getChildren().get(3)))
					.getFirstChild()).getValue();
			Date fechaDesde = new Date();
			Date fechaHasta = new Date();
			String desde = "";
			String hasta = "";
			if (((Datebox) ((listItemj.getChildren().get(1))).getFirstChild())
					.getValue() != null) {
				fechaDesde = ((Datebox) ((listItemj.getChildren().get(1)))
						.getFirstChild()).getValue();
				desde = formatoFechaRara.format(fechaDesde);

			}
			if (((Datebox) ((listItemj.getChildren().get(2))).getFirstChild())
					.getValue() != null) {
				fechaHasta = ((Datebox) ((listItemj.getChildren().get(2)))
						.getFirstChild()).getValue();
				hasta = formatoFechaRara.format(fechaHasta);

			}

			List<Product> listProductos = servicioProducto
					.buscarPorMarca(marcaj);
			for (int i = 0; i < listProductos.size(); i++) {
				Cupo cupo = new Cupo();
				cupo.setCantidad(0);
				cupo.setConsumido(0);
				cupo.setRestante(0);
				cupo.setDescription(listProductos.get(i).getDescription());
				cupo.setDesde(desde);
				cupo.setHasta(hasta);
				CupoPK pk2 = new CupoPK();
				pk2.setVendedor("0");
				pk2.setMarca(marcaj);
				pk2.setProducto(listProductos.get(i).getProductId());
				cupo.setId(pk2);

				guardarLista.add(cupo);
			}
		}
		servicioCupo.guardarVarios(guardarLista);
	}

	public void guardarItems() {

		List<Cupo> guardarLista = new ArrayList<Cupo>();

		List<Cupo> eliminar = new ArrayList<Cupo>();

		eliminar = servicioCupo.buscarItemsRestringidos();
		servicioCupo.eliminarVarios(eliminar);

		ltbItemsAgregados.renderAll();
		for (int j = 0; j < ltbItemsAgregados.getItemCount(); j++) {
			Listitem listItemj = ltbItemsAgregados.getItemAtIndex(j);
			String idItem = ((Textbox) ((listItemj.getChildren().get(4)))
					.getFirstChild()).getValue();
			Date fechaDesde = new Date();
			Date fechaHasta = new Date();
			String desde = "";
			String hasta = "";
			if (((Datebox) ((listItemj.getChildren().get(2))).getFirstChild())
					.getValue() != null) {
				fechaDesde = ((Datebox) ((listItemj.getChildren().get(2)))
						.getFirstChild()).getValue();
				desde = formatoFechaRara.format(fechaDesde);

			}
			if (((Datebox) ((listItemj.getChildren().get(3))).getFirstChild())
					.getValue() != null) {
				fechaHasta = ((Datebox) ((listItemj.getChildren().get(3)))
						.getFirstChild()).getValue();
				hasta = formatoFechaRara.format(fechaHasta);

			}
			Product produc = servicioProducto.buscar(idItem);
			Cupo cupo = new Cupo();
			cupo.setCantidad(0);
			cupo.setConsumido(0);
			cupo.setRestante(0);
			cupo.setDescription(produc.getDescription());
			cupo.setDesde(desde);
			cupo.setHasta(hasta);
			CupoPK pk2 = new CupoPK();
			pk2.setVendedor("0");
			pk2.setMarca(produc.getBrand());
			pk2.setProducto(idItem);
			cupo.setId(pk2);
			guardarLista.add(cupo);
		}

		servicioCupo.guardarVarios(guardarLista);

	}

	@Listen("onClick = #btnGuardarMarca")
	public void guardarMarca() {
		guardarMarcas();
		marcasFinales.clear();
		llenarListas();
		msj.mensajeInformacion(Mensaje.guardado);
	}

	@Listen("onClick = #btnGuardarItem")
	public void guardarItem() {
		guardarItems();
		llenarListas();
		msj.mensajeInformacion(Mensaje.guardado);
	}

	@Listen("onUpload = #btnImportarArchivo")
	public void cargarArchivo(UploadEvent event) {
		mediaArchivo = event.getMedia();
		System.out.println(mediaArchivo.getContentType());
		if (mediaArchivo != null) {
			if (validarArchivo()) {
				lblNombreArchivo.setValue(mediaArchivo.getName());
				final A rm = new A("Remover Archivo");
				rm.addEventListener(Events.ON_CLICK,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								lblNombreArchivo.setValue("");
								rm.detach();
								mediaArchivo = null;
								btnGuardarArchivo.setVisible(false);
							}
						});
				rowArchivo.appendChild(rm);
				btnGuardarArchivo.setVisible(true);
			}
		}
	}

	protected void importarArchivo() {

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(mediaArchivo.getStreamData());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		if (rowIterator.hasNext()) {
			List<Cupo> cupos = new ArrayList<Cupo>();
			int contadorRow = 0;
			boolean error = false;
			boolean errorLong = false;
			boolean entro = false;
			while (rowIterator.hasNext() && !error && !errorLong) {
				contadorRow = contadorRow + 1;
				Row row = rowIterator.next();
				if (!entro) {
					contadorRow = contadorRow + 1;
					row = rowIterator.next();
					entro = true;
				}
				Salesmen vendedor = new Salesmen();
				Product producto = new Product();
				Cupo cupo = new Cupo();
				Double vendedorReferencia = (double) 0;
				String vendedorRef = null;
				Double marcaReferencia = (double) 0;
				String marcaRef = null;
				Double productoReferencia = (double) 0;
				String productoRef = null;
				Double desde = null;
				Double hasta = null;
				Double cantidad = null;
				Double consumido = null;
				Double descripcionReferencia = (double) 0;
				String descripcionRef = null;
				Iterator<Cell> cellIterator = row.cellIterator();
				int contadorCell = 0;
				while (cellIterator.hasNext() && !error && !errorLong) {
					contadorCell = contadorCell + 1;
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						vendedorRef = obtenerStringCualquiera(cell,
								vendedorReferencia, vendedorRef);
						if (vendedorRef != null && vendedorRef.length() > 3)
							errorLong = true;
						break;
					case 2:
						marcaRef = obtenerStringCualquiera(cell,
								marcaReferencia, marcaRef);
						if (marcaRef != null && marcaRef.length() > 3)
							errorLong = true;
						break;
					case 3:
						productoRef = obtenerStringCualquiera(cell,
								productoReferencia, productoRef);
						if (productoRef != null && productoRef.length() > 50)
							errorLong = true;
						break;
					case 5:
						if (cell.getCellType() == 0) {
							desde = cell.getNumericCellValue();
							if (String.valueOf(desde.longValue()).length() > 8) {
								errorLong = true;
							}
						} else
							error = true;
						break;
					case 6:
						if (cell.getCellType() == 0) {
							hasta = cell.getNumericCellValue();
							if (String.valueOf(hasta.longValue()).length() > 8)
								errorLong = true;
						} else
							error = true;
						break;
					case 7:
						if (cell.getCellType() == 0) {
							cantidad = cell.getNumericCellValue();
						} else
							error = true;
						break;
					case 8:
						if (cell.getCellType() == 0) {
							consumido = cell.getNumericCellValue();
						} else
							error = true;
						break;
					case 4:
						descripcionRef = obtenerStringCualquiera(cell,
								descripcionReferencia, descripcionRef);
						if (descripcionRef != null
								&& descripcionRef.length() > 35)
							errorLong = true;
						break;
					default:
						break;
					}
				}
				if (!errorLong) {
					if (!error && vendedorRef != null && marcaRef != null
							&& productoRef != null && descripcionRef != null
							&& desde != null && hasta != null
							&& cantidad != null && consumido != null) {
						if (cantidad.intValue() >= consumido.intValue()) {
							vendedor = servicioVendedor.buscar(vendedorRef);
							if (vendedor != null || vendedorRef.equals("0")) {
								producto = servicioProducto.buscarPorIdYMarca(
										productoRef, marcaRef);
								if (producto != null) {
									CupoPK clave = new CupoPK();
									clave.setMarca(marcaRef);
									clave.setProducto(productoRef);
									clave.setVendedor(vendedorRef);
									cupo.setId(clave);
									cupo.setDesde(String.valueOf(desde
											.longValue()));
									cupo.setHasta(String.valueOf(hasta
											.longValue()));
									cupo.setDescription(descripcionRef);
									cupo.setConsumido(consumido.intValue());
									cupo.setCantidad(cantidad.intValue());
									cupo.setRestante(cantidad.intValue()
											- consumido.intValue());
									cupos.add(cupo);
								} else {
									msj.mensajeError(valorNoEncontrado
											+ " el vendedor: "
											+ vendedorRef
											+ " no ha sido encontrado, por lo tanto no ha sido importado el archivo."
											+ " Fila: " + contadorRow
											+ ". Columna: " + contadorCell);
									error = true;
								}
							} else {
								msj.mensajeError(valorNoEncontrado
										+ " el producto con ID: "
										+ productoRef
										+ " y Marca:"
										+ marcaRef
										+ " no ha sido encontrado, por lo tanto no ha sido importado el archivo"
										+ " Fila: " + contadorRow
										+ ". Columna: " + contadorCell);
								error = true;
							}
						} else {
							msj.mensajeError("La cantidad disponible debe ser mayor a la cantidad consumida. "
									+ lblNombreArchivo.getValue()
									+ ". Fila: "
									+ contadorRow);
							error = true;
						}
					} else {
						msj.mensajeError(archivoConError
								+ lblNombreArchivo.getValue() + ". Fila: "
								+ contadorRow + ". Columna: " + contadorCell);
						error = true;
					}
				} else {
					msj.mensajeError(errorLongitud
							+ lblNombreArchivo.getValue()
							+ ". Fila: "
							+ contadorRow
							+ ". Columna: "
							+ contadorCell
							+ ". Longitudes permitidas: campo1 3, campo2 3, campo3 50, campo4 8, campo5 8, campo9 35");
					errorLong = true;
					error = true;
				}
			}
			if (!error)
				servicioCupo.guardarVarios(cupos);
			else
				errorGeneral = true;
		} else {
			msj.mensajeAlerta(archivoVacio + " " + lblNombreArchivo.getValue());
			errorGeneral = true;
		}

	}

	protected boolean validarArchivo() {
		if (mediaArchivo != null && !Validador.validarExcel(mediaArchivo)) {
			msj.mensajeError(Mensaje.archivoExcel);
			return false;
		} else
			return true;
	}

	private String obtenerStringCualquiera(Cell cell, Double idReferencia,
			String idRef) {
		if (cell.getCellType() == 0) {
			idReferencia = cell.getNumericCellValue();
			if (idReferencia != null)
				return idRef = String.valueOf(idReferencia.longValue());
			else
				return null;
		} else {
			if (cell.getCellType() == 1) {
				if (!cell.getStringCellValue().equals("NULL"))
					return idRef = cell.getStringCellValue();
				else
					return null;
			}
			return null;
		}
	}

	@Listen("onClick = #btnGuardarArchivo")
	public void guardarArchivo() {
		importarArchivo();
		if (!errorGeneral) {
			lblNombreArchivo.setValue("");
			A rm = (A) rowArchivo.getChildren().get(4);
			rm.detach();
			mediaArchivo = null;
			btnGuardarArchivo.setVisible(false);
			msj.mensajeInformacion(Mensaje.guardado);
			llenarListas();
			errorGeneral=false;
		}
	}

	@Listen("onClick = #btnDescargarArchivo")
	public void descargar() {
		List<Salesmen> vendedores = servicioVendedor.buscarTodosNoCero();
		List<Product> productos = servicioProducto.buscarActivos();
		if (!vendedores.isEmpty()) {
			if (!productos.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				String s = ";";
				// String r = "";
				String h = "";
				h += "Codigo_Vendedor" + s;
				h += "Vendedor" + s;
				h += "Codigo_Marca" + s;
				h += "Codigo_Item" + s;
				h += "Descripcion_Item" + s;
				h += "Desde" + s;
				h += "Hasta" + s;
				h += "Cantidad" + s;
				h += "Consumido" + s;
				sb.append(h + "\n");
				for (int i = 0; i < vendedores.size(); i++) {
					for (int j = 0; j < productos.size(); j++) {
						// String h = "";
						h = "";
						h += vendedores.get(i).getSalesmanId() + s;
						h += vendedores.get(i).getName() + s;
						h += productos.get(j).getBrand() + s;
						h += productos.get(j).getProductId() + s;
						h += productos.get(j).getDescription() + s;
						h += " " + s;
						h += " " + s;
						h += 0 + s;
						h += 0 + s;
						sb.append(h + "\n");
					}
				}
				Filedownload.save(sb.toString().getBytes(), "text/plain",
						"Formato.csv");

				msj.mensajeInformacion("Archivo descargado con Exito");

			} else
				msj.mensajeError("No existen Productos Registrados");
		} else
			msj.mensajeError("No existen Vendedores Registrados");
	}
}
