package controlador.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Action;
import modelo.maestros.Salesmen;
import modelo.maestros.SalesmenAction;
import modelo.maestros.SalesmenActionPK;
import modelo.maestros.SalesmenSub;
import modelo.maestros.SalesmenSubPK;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import arbol.CArbol;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.maestros.CGenerico;

public class CVendedor extends CGenerico {

	private static final long serialVersionUID = 7879830599305337459L;
	@Wire
	private Button btnSiguientePestanna;
	@Wire
	private Button btnAnteriorPestanna;
	@Wire
	private Tab tabBasicos;
	@Wire
	private Tab tabPermisos;
	@Wire
	private Tab tabSubordinados;
	@Wire
	private Div divVendedor;
	@Wire
	private Div botoneraVendedor;
	@Wire
	private Div catalogoVendedor;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtUsername;
	@Wire
	private Textbox txtPassword;
	@Wire
	private Textbox txtRegion;
	@Wire
	private Textbox txtEmail;
	@Wire
	private Textbox txtEmailCoordinador;
	@Wire
	private Spinner spnSerialDesde;
	@Wire
	private Spinner spnSerialHasta;
	@Wire
	private Spinner spnUltimoSerial;
	@Wire
	private Checkbox chxMovil;
	@Wire
	private Listbox ltbAccionesDisponibles;
	@Wire
	private Listbox ltbAccionesAgregadas;
	@Wire
	private Listbox ltbVendedoresDisponibles;
	@Wire
	private Listbox ltbVendedoresAgregados;
	Botonera botonera;
	@Wire
	private Groupbox gpxDatos;
	@Wire
	private Groupbox gpxRegistro;
	private CArbol cArbol = new CArbol();
	String id = "";
	Catalogo<Salesmen> catalogo;
	List<Action> accionesDisponibles = new ArrayList<Action>();
	List<SalesmenAction> accionesAgregadas = new ArrayList<SalesmenAction>();
	List<Salesmen> vendedoresDisponibles = new ArrayList<Salesmen>();
	List<SalesmenSub> vendedoresAgregados = new ArrayList<SalesmenSub>();

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
		llenarListas(null);
		mostrarCatalogo();
		gpxRegistro.setOpen(false);
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						Salesmen vendedor = catalogo
								.objetoSeleccionadoDelCatalogo();
						txtNombre.setValue(vendedor.getName());
						txtUsername.setValue(vendedor.getSalesmanId());
						txtEmailCoordinador.setValue(vendedor
								.getMailCoordinador());
						txtEmail.setValue(vendedor.getMail());
						txtRegion.setValue(vendedor.getRegion());
						spnSerialDesde.setValue(vendedor.getBottomSerial());
						spnSerialHasta.setValue(vendedor.getTopSerial());
						spnUltimoSerial.setValue(vendedor.getLastSerial());
						txtPassword.setValue(vendedor.getComments());
						txtUsername.setDisabled(true);
						// ??
						if (vendedor.getMovil() != 0)
							chxMovil.setChecked(true);
						id = vendedor.getSalesmanId();
						llenarListas(vendedor);
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}

			}

			@Override
			public void salir() {
				cerrarVentana(divVendedor, cerrar, tabs);
			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validar()) {

					Salesmen vendedor = new Salesmen();
					String usuario = txtUsername.getValue();
					String nombre = txtNombre.getValue();
					String correo = txtEmail.getValue();
					String correoCoor = txtEmailCoordinador.getValue();
					String region = txtRegion.getValue();
					String contraseña = txtPassword.getValue();
					int desde = spnSerialDesde.getValue();
					int hasta = spnSerialHasta.getValue();
					int ultimo = spnUltimoSerial.getValue();
					int movil = 1;
					if (!chxMovil.isChecked()) {
						movil = 0;
					}

					vendedor.setName(nombre);
					vendedor.setBottomSerial(desde);
					vendedor.setComments(contraseña);
					vendedor.setLastSerial(ultimo);
					vendedor.setMail(correo);
					vendedor.setMailCoordinador(correoCoor);
					vendedor.setTopSerial(hasta);
					vendedor.setRegion(region);
					vendedor.setSalesmanId(usuario);
					vendedor.setMovil((short) movil);
					servicioVendedor.guardar(vendedor);

					Salesmen vende = servicioVendedor.buscar(usuario);
					guardarAcciones(vende);
					guardarSubordinados(vende);
					limpiar();
					msj.mensajeInformacion(Mensaje.guardado);
					catalogo.actualizarLista(
							servicioVendedor.buscarTodosOrdenados(), true);
					abrirCatalogo();

				}
			}

			@Override
			public void eliminar() {
				if (gpxDatos.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<Salesmen> eliminarLista = catalogo
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
													servicioVendedor
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													catalogo.actualizarLista(
															servicioVendedor
																	.buscarTodosOrdenados(),
															true);
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (!id.equals("")) {
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
													servicioVendedor
															.eliminarClave(id);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													catalogo.actualizarLista(
															servicioVendedor
																	.buscarTodosOrdenados(),
															true);
												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void annadir() {
				abrirRegistro();
				mostrarBotones(false);

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub

			}
		};
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botoneraVendedor.appendChild(botonera);
	}

	public void mostrarBotones(boolean bol) {
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(bol);
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(3).setVisible(!bol);
		botonera.getChildren().get(5).setVisible(!bol);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
	}

	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);
	}

	public boolean validarSeleccion() {
		List<Salesmen> seleccionados = catalogo.obtenerSeleccionados();
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

	public void limpiarCampos() {
		ltbAccionesAgregadas.getItems().clear();
		ltbAccionesDisponibles.getItems().clear();
		ltbVendedoresDisponibles.getItems().clear();
		ltbVendedoresAgregados.getItems().clear();
		txtRegion.setValue("");
		txtNombre.setValue("");
		txtEmailCoordinador.setValue("");
		txtUsername.setValue("");
		txtUsername.setDisabled(false);
		txtEmail.setValue("");
		txtPassword.setValue("");
		spnSerialDesde.setValue(null);
		spnSerialHasta.setValue(null);
		spnUltimoSerial.setValue(null);
		chxMovil.setChecked(false);
		id = "";
		llenarListas(null);
	}

	public boolean camposEditando() {
		if (txtRegion.getText().compareTo("") != 0
				|| txtNombre.getText().compareTo("") != 0
				|| txtEmailCoordinador.getText().compareTo("") != 0
				|| txtUsername.getText().compareTo("") != 0
				|| txtEmail.getText().compareTo("") != 0
				|| txtPassword.getText().compareTo("") != 0
				|| spnSerialDesde.getText().compareTo("") != 0
				|| spnSerialHasta.getText().compareTo("") != 0
				|| spnUltimoSerial.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
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

	/* Validaciones de pantalla para poder realizar el guardar */
	public boolean validar() {
		if (txtNombre.getText().compareTo("") == 0
				|| txtUsername.getText().compareTo("") == 0
				|| txtEmail.getText().compareTo("") == 0
				|| txtPassword.getText().compareTo("") == 0
				|| spnSerialDesde.getText().compareTo("") == 0
				|| spnSerialHasta.getText().compareTo("") == 0
				|| spnUltimoSerial.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (txtEmailCoordinador.getText().compareTo("") != 0
					&& !Validador.validarCorreo(txtEmailCoordinador.getValue())) {
				msj.mensajeError(Mensaje.correoInvalido);
				return false;
			} else {
				if (!Validador.validarCorreo(txtEmail.getValue())) {
					msj.mensajeError(Mensaje.correoInvalido);
					return false;
				} else {
					if (servicioVendedor.buscar(txtUsername.getValue()) != null
							&& id.equals("")) {
						msj.mensajeError(Mensaje.usernameUsado);
						return false;
					} else
						return true;
				}
			}
		}

	}

	@Listen("onChange = #txtUsername")
	public void validarId() {
		if (servicioVendedor.buscar(txtUsername.getValue()) != null) {
			msj.mensajeAlerta(Mensaje.usernameUsado);
		}
	}

	/* Valida el numero telefonico */
	@Listen("onChange = #txtEmail")
	public void validarTelefono() {
		if (!Validador.validarCorreo(txtEmail.getValue())) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	/* Valida el correo electronico */
	@Listen("onChange = #txtEmailCoordinador")
	public void validarCorreo() {
		if (!Validador.validarCorreo(txtEmailCoordinador.getValue())) {
			msj.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	/* LLena las listas dado un usario */
	public void llenarListas(Salesmen usuario) {

		vendedoresDisponibles = servicioVendedor.buscarTodosOrdenados();
		accionesDisponibles = servicioAction.buscarTodos();
		if (usuario == null) {
			ltbAccionesDisponibles.setModel(new ListModelList<Action>(
					accionesDisponibles));
			ltbVendedoresDisponibles.setModel(new ListModelList<Salesmen>(
					vendedoresDisponibles));
		} else {
			// Llenar Permisologia
			accionesAgregadas = servicioVendedorAction
					.buscarAccionesDeVendedor(usuario.getSalesmanId());
			ltbAccionesAgregadas.setModel(new ListModelList<SalesmenAction>(
					accionesAgregadas));
			if (!accionesAgregadas.isEmpty()) {
				List<Integer> ids = new ArrayList<Integer>();
				for (int i = 0; i < accionesAgregadas.size(); i++) {
					int id = accionesAgregadas.get(i).getId().getActionId();
					ids.add(id);
				}
				accionesDisponibles = servicioAction
						.buscarAccionesDisponibles(ids);
				ltbAccionesDisponibles.setModel(new ListModelList<Action>(
						accionesDisponibles));
			}

			// Llenar Subordinados
			vendedoresAgregados = servicioVendedorSubordinado
					.buscarSubordinadosDeVendedor(usuario.getSalesmanId());
			ltbVendedoresAgregados.setModel(new ListModelList<SalesmenSub>(
					vendedoresAgregados));
			if (!vendedoresAgregados.isEmpty()) {
				List<String> ids = new ArrayList<String>();
				for (int i = 0; i < vendedoresAgregados.size(); i++) {
					String id = vendedoresAgregados.get(i).getId()
							.getSalesmanIdSub();
					ids.add(id);
				}
				vendedoresDisponibles = servicioVendedor
						.buscarSubordinadosDisponibles(ids);
				ltbVendedoresDisponibles.setModel(new ListModelList<Salesmen>(
						vendedoresDisponibles));
			}
		}
		listasMultiples();
	}

	public void listasMultiples() {
		ltbAccionesAgregadas.setMultiple(false);
		ltbAccionesAgregadas.setCheckmark(false);
		ltbAccionesAgregadas.setMultiple(true);
		ltbAccionesAgregadas.setCheckmark(true);

		ltbAccionesDisponibles.setMultiple(false);
		ltbAccionesDisponibles.setCheckmark(false);
		ltbAccionesDisponibles.setMultiple(true);
		ltbAccionesDisponibles.setCheckmark(true);

		ltbVendedoresAgregados.setMultiple(false);
		ltbVendedoresAgregados.setCheckmark(false);
		ltbVendedoresAgregados.setMultiple(true);
		ltbVendedoresAgregados.setCheckmark(true);

		ltbVendedoresDisponibles.setMultiple(false);
		ltbVendedoresDisponibles.setCheckmark(false);
		ltbVendedoresDisponibles.setMultiple(true);
		ltbVendedoresDisponibles.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * izquierda a la lista de la derecha
	 */
	@Listen("onClick = #pasar1")
	public void moverDerecha() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbAccionesDisponibles.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Action accion = listItem.get(i).getValue();
					accionesDisponibles.remove(accion);
					SalesmenAction agregar = new SalesmenAction();
					SalesmenActionPK pk = new SalesmenActionPK();
					pk.setActionId(accion.getActionId());
					pk.setSalesmanId(servicioAction
							.buscar(accion.getActionId()).getName());
					agregar.setId(pk);
					accionesAgregadas.add(agregar);
					ltbAccionesAgregadas
							.setModel(new ListModelList<SalesmenAction>(
									accionesAgregadas));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbAccionesDisponibles.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbAccionesAgregadas.setMultiple(false);
		ltbAccionesAgregadas.setCheckmark(false);
		ltbAccionesAgregadas.setMultiple(true);
		ltbAccionesAgregadas.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar2")
	public void moverIzquierda() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbAccionesAgregadas.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					SalesmenAction accion = listItem2.get(i).getValue();
					accionesAgregadas.remove(accion);
					Action acc = servicioAction.buscar(accion.getId()
							.getActionId());
					accionesDisponibles.add(acc);
					ltbAccionesDisponibles.setModel(new ListModelList<Action>(
							accionesDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbAccionesAgregadas.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbAccionesDisponibles.setMultiple(false);
		ltbAccionesDisponibles.setCheckmark(false);
		ltbAccionesDisponibles.setMultiple(true);
		ltbAccionesDisponibles.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * izquierda a la lista de la derecha
	 */
	@Listen("onClick = #pasar11")
	public void moverDerecha2() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbVendedoresDisponibles.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Salesmen vendedor = listItem.get(i).getValue();
					vendedoresDisponibles.remove(vendedor);
					SalesmenSub agregar = new SalesmenSub();
					SalesmenSubPK pk = new SalesmenSubPK();
					pk.setSalesmanId(servicioVendedor.buscar(
							vendedor.getSalesmanId()).getName());
					pk.setSalesmanIdSub(vendedor.getSalesmanId());
					agregar.setId(pk);
					vendedoresAgregados.add(agregar);
					ltbVendedoresAgregados
							.setModel(new ListModelList<SalesmenSub>(
									vendedoresAgregados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbVendedoresDisponibles.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbVendedoresAgregados.setMultiple(false);
		ltbVendedoresAgregados.setCheckmark(false);
		ltbVendedoresAgregados.setMultiple(true);
		ltbVendedoresAgregados.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar22")
	public void moverIzquierda2() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbVendedoresAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					SalesmenSub sub = listItem2.get(i).getValue();
					vendedoresAgregados.remove(sub);
					Salesmen men = servicioVendedor.buscar(sub.getId()
							.getSalesmanIdSub());
					vendedoresDisponibles.add(men);
					ltbVendedoresDisponibles
							.setModel(new ListModelList<Salesmen>(
									vendedoresDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbVendedoresAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbVendedoresDisponibles.setMultiple(false);
		ltbVendedoresDisponibles.setCheckmark(false);
		ltbVendedoresDisponibles.setMultiple(true);
		ltbVendedoresDisponibles.setCheckmark(true);
	}

	/* Abre la pestanna de datos de usuario */
	@Listen("onClick = #btnSiguientePestanna")
	public void siguientePestanna() {
		tabPermisos.setSelected(true);
	}

	/* Abre la pestanna de datos basicos */
	@Listen("onClick = #btnAnteriorPestanna")
	public void anteriorPestanna() {
		tabBasicos.setSelected(true);
	}

	/* Abre la pestanna de datos de usuario */
	@Listen("onClick = #btnSiguientePestanna2")
	public void siguientePestanna2() {
		tabSubordinados.setSelected(true);
	}

	/* Abre la pestanna de datos basicos */
	@Listen("onClick = #btnAnteriorPestanna2")
	public void anteriorPestanna2() {
		tabPermisos.setSelected(true);
	}

	@Listen("onClick = #chxMovil")
	public void msjCheck() {
		msj.mensajeAlerta(Mensaje.movil);
	}

	public void mostrarCatalogo() {
		final List<Salesmen> vendedores = servicioVendedor
				.buscarTodosOrdenados();
		catalogo = new Catalogo<Salesmen>(catalogoVendedor, "Salesmen",
				vendedores, false, false, false, "Username", "Nombre",
				"Region", "Email") {

			@Override
			protected List<Salesmen> buscar(List<String> valores) {

				List<Salesmen> user = new ArrayList<Salesmen>();

				for (Salesmen vende : vendedores) {
					if (vende.getSalesmanId().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& vende.getName().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& vende.getRegion().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& vende.getMail().toLowerCase()
									.contains(valores.get(3).toLowerCase())) {
						user.add(vende);
					}
				}
				return user;
			}

			@Override
			protected String[] crearRegistros(Salesmen vendedor) {
				String[] registros = new String[4];
				registros[0] = vendedor.getSalesmanId();
				registros[1] = vendedor.getName();
				registros[2] = vendedor.getRegion();
				registros[3] = vendedor.getMail();
				return registros;
			}

		};
		catalogo.setParent(catalogoVendedor);
	}

	protected void guardarSubordinados(Salesmen vendedor) {
		List<SalesmenSub> acteriores = servicioVendedorSubordinado
				.buscarSubordinadosDeVendedor(vendedor.getSalesmanId());
		servicioVendedorSubordinado.eliminarVarios(acteriores);
		List<SalesmenSub> subordinados = new ArrayList<SalesmenSub>();
		for (int i = 0; i < ltbVendedoresAgregados.getItemCount(); i++) {
			Listitem listItem = ltbVendedoresAgregados.getItemAtIndex(i);
			String idSub = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			SalesmenSub agregar = new SalesmenSub();
			SalesmenSubPK pk = new SalesmenSubPK();
			pk.setSalesmanId(vendedor.getSalesmanId());
			pk.setSalesmanIdSub(idSub);
			agregar.setId(pk);
			subordinados.add(agregar);
		}
		servicioVendedorSubordinado.guardar(subordinados);

	}

	protected void guardarAcciones(Salesmen vendedor) {

		List<SalesmenAction> acteriores = servicioVendedorAction
				.buscarAccionesDeVendedor(vendedor.getSalesmanId());
		servicioVendedorAction.eliminarVarios(acteriores);
		List<SalesmenAction> acciones = new ArrayList<SalesmenAction>();
		for (int i = 0; i < ltbAccionesAgregadas.getItemCount(); i++) {
			Listitem listItem = ltbAccionesAgregadas.getItemAtIndex(i);
			int idAccion = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			SalesmenAction agregar = new SalesmenAction();
			SalesmenActionPK pk = new SalesmenActionPK();
			pk.setActionId(idAccion);
			pk.setSalesmanId(vendedor.getSalesmanId());
			agregar.setId(pk);
			acciones.add(agregar);
		}
		servicioVendedorAction.guardar(acciones);

	}
}
