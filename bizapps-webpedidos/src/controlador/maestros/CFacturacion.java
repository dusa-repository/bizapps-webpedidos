package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.CustomerFacturacion;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CFacturacion extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox txtFacturacion;
	@Wire
	private Textbox txtWarehouse;
	@Wire
	private Div botoneraFacturacion;
	@Wire
	private Div catalogoFacturacion;
	@Wire
	private Div divFacturacion;
	String id = null;
	@Wire
	private Groupbox gpxDatos;
	@Wire
	private Groupbox gpxRegistro;
	Catalogo<CustomerFacturacion> catalogo;
	protected List<CustomerFacturacion> listaGeneral = new ArrayList<CustomerFacturacion>();
	Botonera botonera;

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
		txtFacturacion.setFocus(true);
		mostrarCatalogo();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						CustomerFacturacion grupo = catalogo
								.objetoSeleccionadoDelCatalogo();
						txtFacturacion
								.setValue(grupo.getWarehouseFacturacion());
						txtWarehouse.setValue(grupo.getWarehouse());
						id = grupo.getWarehouseFacturacion();
						txtFacturacion.setDisabled(true);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}
			}

			@Override
			public void salir() {
				cerrarVentana(divFacturacion, cerrar, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validar()) {
					CustomerFacturacion customer = new CustomerFacturacion();
					String facturacion = txtFacturacion.getValue();
					String valoresWare = txtWarehouse.getValue();
					String warehouse = "";
					if (!valoresWare.equals("")) {
						String valores[] = valoresWare.split(",");
						int j = 0;
						while (j < valores.length) {
							warehouse += valores[j].trim().toUpperCase();
							if (j != valores.length - 1)
								warehouse += ",";
							j++;
						}
					}
					customer.setWarehouseFacturacion(facturacion.toUpperCase());
					customer.setWarehouse(warehouse);
					servicioCustomerFacturacion.guardar(customer);
					limpiar();
					msj.mensajeInformacion(Mensaje.guardado);
					listaGeneral = servicioCustomerFacturacion
							.buscarTodosOrdenados();
					catalogo.actualizarLista(listaGeneral, true);
					abrirCatalogo();
				}
			}

			@Override
			public void eliminar() {
				if (gpxDatos.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<CustomerFacturacion> eliminarLista = catalogo
								.obtenerSeleccionados();
						Messagebox
								.show("¿Desea Eliminar los "
										+ eliminarLista.size() + " Registros?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioCustomerFacturacion
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioCustomerFacturacion
															.buscarTodosOrdenados();
													catalogo.actualizarLista(
															listaGeneral, true);
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (id != null) {
						Messagebox
								.show(Mensaje.deseaEliminar,
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioCustomerFacturacion
															.eliminarClave(id);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioCustomerFacturacion
															.buscarTodosOrdenados();
													catalogo.actualizarLista(
															listaGeneral, true);
												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
			}

			@Override
			public void buscar() {
				abrirCatalogo();
			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub

			}

			@Override
			public void annadir() {
				abrirRegistro();
				mostrarBotones(false);
			}
		};
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botoneraFacturacion.appendChild(botonera);
	}

	@Listen("onOpen = #gpxDatos")
	public void abrirCatalogo() {
		gpxDatos.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatos.setOpen(false);
								gpxRegistro.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatos.setOpen(true);
									gpxRegistro.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatos.setOpen(true);
			gpxRegistro.setOpen(false);
			mostrarBotones(true);
		}
	}

	protected boolean validar() {
		if (txtFacturacion.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			return true;
		}
	}

	@Listen("onChange = #txtFacturacion")
	public void validarId() {
		if (txtFacturacion.getValue() != null)
			if (servicioCustomerFacturacion.buscar(txtFacturacion.getValue()) != null) {
				msj.mensajeAlerta("El Warehouse de Facturacion ya esta siendo usado por otro registro");
				txtFacturacion.setValue("");
			}
	}

	protected void limpiarCampos() {
		txtWarehouse.setValue("");
		txtFacturacion.setValue("");
		txtFacturacion.setDisabled(false);
		id = null;
	}

	public boolean camposEditando() {
		if (txtFacturacion.getText().compareTo("") != 0
				|| txtWarehouse.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);
	}

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(2).setVisible(bol);
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(3).setVisible(!bol);
		botonera.getChildren().get(5).setVisible(!bol);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
	}

	protected boolean validarSeleccion() {
		List<CustomerFacturacion> seleccionados = catalogo
				.obtenerSeleccionados();
		if (seleccionados == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (seleccionados.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoItem);
				return false;
			} else {
				return true;
			}
		}
	}

	private void mostrarCatalogo() {
		listaGeneral = servicioCustomerFacturacion.buscarTodosOrdenados();
		catalogo = new Catalogo<CustomerFacturacion>(catalogoFacturacion,
				"Catalogo de Facturaciones", listaGeneral, false, false, false,
				"Warehouse Facturacion", "Warehouse") {

			@Override
			protected List<CustomerFacturacion> buscar(List<String> valores) {

				List<CustomerFacturacion> user = new ArrayList<CustomerFacturacion>();

				for (CustomerFacturacion vende : listaGeneral) {
					if (vende.getWarehouseFacturacion().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& vende.getWarehouse().toLowerCase()
									.contains(valores.get(1).toLowerCase())) {
						user.add(vende);
					}
				}
				return user;
			}

			@Override
			protected String[] crearRegistros(CustomerFacturacion vendedor) {
				String[] registros = new String[2];
				registros[0] = vendedor.getWarehouseFacturacion();
				registros[1] = vendedor.getWarehouse();
				return registros;
			}

		};
		catalogo.setParent(catalogoFacturacion);
	}

}
