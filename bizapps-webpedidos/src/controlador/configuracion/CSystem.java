package controlador.configuracion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.configuracion.System;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CSystem extends CGenerico {

	private static final long serialVersionUID = 3394240712277711246L;
	@Wire
	private Textbox txtMotivo;
	@Wire
	private Textbox txtDestinatario;
	@Wire
	private Label lblEstado;
	@Wire
	private Div botoneraSystem;
	@Wire
	private Div divSystem;
	@Wire
	private Radio rdoSI;
	@Wire
	private Radio rdoNo;

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
		actualizarEstado();
		Botonera botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divSystem, titulo, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				txtDestinatario.setValue("");
				txtMotivo.setValue("");
				rdoNo.setChecked(false);
				rdoSI.setChecked(false);
			}

			@Override
			public void guardar() {
				if (validar()) {
					String estado = "A";
					if (rdoNo.isChecked())
						estado = "I";
					String usuario = nombreUsuarioSesion();
					String fechaL = formatoFechaRara.format(fecha);
					String hora = horaAuditoria;
					String motivo = txtMotivo.getValue();
					String destinatarios = txtDestinatario.getValue();
					System system = new System();
					system.setId(1);
					system.setDestinatarios(destinatarios);
					system.setEstado(estado);
					system.setFecha(fechaL);
					system.setHora(hora);
					system.setMotivo(motivo);
					system.setUsuario(usuario);
					servicioSystem.guardar(system);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					actualizarEstado();
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
		botoneraSystem.appendChild(botonera);
	}

	protected void actualizarEstado() {
		System actual = servicioSystem.buscar(1);
		if (actual != null) {
			if (actual.getEstado().equals("A"))
				lblEstado.setValue("Activo");
			else
				lblEstado.setValue("Inactivo");
			
			txtDestinatario.setValue(actual.getDestinatarios());
			txtMotivo.setValue(actual.getMotivo());
		}
	}

	protected boolean validar() {
		if (txtDestinatario.getText().compareTo("") == 0
				|| txtMotivo.getText().compareTo("") == 0
				|| (!rdoSI.isChecked() && !rdoNo.isChecked())) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

}
