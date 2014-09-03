package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Cupo;
import modelo.maestros.CupoPK;
import modelo.maestros.Product;
import modelo.maestros.Salesmen;
import modelo.seguridad.Grupo;
import modelo.seguridad.Usuario;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import servicio.maestros.SProducto;
import servicio.transacciones.SCupo;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

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

	List<String> marcasDisponibles = new ArrayList<String>();

	List<Cupo> marcasFinales = new ArrayList<Cupo>();

	List<Product> itemsDisponibles = new ArrayList<Product>();

	List<Cupo> itemsRestringidos = new ArrayList<Cupo>();

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				cerrar = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}
		mostrarCatalogo();
		listaProduct = servicioProducto.buscarMarcas();
		cmbMarca.setModel(new ListModelList<String>(listaProduct));

		//llenarListas();
		listasMultiples();
		Botonera botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divCupo, cerrar, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

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
		botoneraCupo.appendChild(botonera);
	}

	public void mostrarCatalogo() {

		// final List<Cupo> cupos = servicioCupo.buscarNinguno();

		cupos = new ArrayList<Cupo>();
		catalogo = new Catalogo<Cupo>(catalogoCupo, "", cupos, false, false,
				true, "Codigo", "Descripcion", "Desde", "Hasta", "Cantidad",
				"Consumido", "Disponible") {

			@Override
			protected List<Cupo> buscar(List<String> valores) {

				List<Cupo> cuposFiltro = new ArrayList<Cupo>();

				for (Cupo cupo : cupos) {
					if (cupo.getId().getProducto().toLowerCase()
							.startsWith(valores.get(0))
							&& servicioProducto
									.buscar(cupo.getId().getProducto())
									.getDescription().toLowerCase()
									.startsWith(valores.get(1))
							&& cupo.getDesde().toLowerCase()
									.startsWith(valores.get(2))
							&& cupo.getHasta().toLowerCase()
									.startsWith(valores.get(3))
							&& String.valueOf(cupo.getCantidad()).toLowerCase()
									.startsWith(valores.get(4))
							&& String.valueOf(cupo.getConsumido())
									.toLowerCase().startsWith(valores.get(5))
							&& String.valueOf(cupo.getRestante()).toLowerCase()
									.startsWith(valores.get(6))) {
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
				.buscarTodosOrdenados();
		catalogoVendedor = new Catalogo<Salesmen>(divCatalogoVendedor,
				"Vendedores", vendedores, true, false, false, "Codigo",
				"Nombre", "Region") {

			@Override
			protected List<Salesmen> buscar(List<String> valores) {

				List<Salesmen> lista = new ArrayList<Salesmen>();

				for (Salesmen vendedor : vendedores) {
					if (vendedor.getSalesmanId().toLowerCase()
							.startsWith(valores.get(0))
							&& vendedor.getName().trim().toLowerCase()
									.startsWith(valores.get(1))
							&& vendedor.getRegion().trim().toLowerCase()
									.startsWith(valores.get(2))) {
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
				cupos = servicioCupo.buscarPorVendedoryMarca(idVendedor,
						idMarca);

				List<Product> items = servicioProducto.buscarPorMarca(idMarca);
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
				catalogo.actualizarLista(cupos);
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

		itemsDisponibles = servicioProducto.buscarTodos();

		itemsRestringidos = servicioCupo.buscarItemsRestringidos();

		List<String> marcasRestringidas = new ArrayList<String>();
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
	}

	@Listen("onClick = #pasar1Item")
	public void derechaItem() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbItems.getItems();
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
					for (int j = 0; j < ltbItemsAgregados
							.getItemCount(); j++) {
						Listitem listItemj = ltbItemsAgregados
								.getItemAtIndex(j);
						System.out.println(((listItemj
								.getChildren().get(3))).getFirstChild());
						String idItem = ((Textbox) ((listItemj
								.getChildren().get(3))).getFirstChild())
								.getValue();
						System.out.println(idItem);
						System.out.println("posicion : " + j);
						System.out.println("tamaño :" +ltbItemsAgregados
							.getItemCount());
						
						Date fechaDesde = new Date();
						Date fechaHasta = new Date();
						String desde="";
						String hasta="";
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
					ltbItemsAgregados
							.setModel(new ListModelList<Cupo>(
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
	
//	@Listen("onClick = #btnLimpiarVendedor")
//	public void derechaEspecialista() {
//		
//	}
//	@Listen("onClick = #btnLimpiarMarca")
//	public void derechaEspecialista() {
//		
//	}
//	@Listen("onClick = #pasar1Marca")
//	public void derechaEspecialista() {
//		
//	}
}
