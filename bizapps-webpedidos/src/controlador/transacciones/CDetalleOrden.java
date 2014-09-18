package controlador.transacciones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.Action;
import modelo.maestros.Customer;
import modelo.maestros.Order;
import modelo.maestros.OrdersDetail;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import componente.Botonera;

import controlador.maestros.CGenerico;

public class CDetalleOrden extends CGenerico {
	@Wire
	private Div botoneraDetalle;
	@Wire
	private Label lblCliente;
	@Wire
	private Window wdwDetalle;
	@Wire
	private Label lblOrden;
	@Wire
	private Label lblFecha;
	@Wire
	private Label lblStatus;
	@Wire
	private Label lblHora;
	@Wire
	private Label lblMonto;
	@Wire
	private Label lblVendedor;
	@Wire
	private Label lblComentarios;
	@Wire
	private Label lblFechaRequerida;
	@Wire
	private Listbox ltbDetalle;
	@Wire
	private Label lblStatusChange;

	@Override
	public void inicializar() throws IOException {

		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("detalle");
		if (map != null) {
			if (map.get("orden") != null) {
				Order orden = (Order) map.get("orden");
				
				lblFecha.setValue(orden.getOrderDate());
				Customer cliente = servicioCliente.buscar(
						orden.getCustomerId());
				if (cliente!=null)
				lblCliente.setValue(cliente.getName());
				lblOrden.setValue(String.valueOf(orden.getOrderId()));
				lblFechaRequerida.setValue(orden.getRequiredDate());
				lblHora.setValue(orden.getOrderTime());
				lblMonto.setValue(String.valueOf(orden.getAmount()));
				lblStatus.setValue(orden.getStatus());
				lblVendedor.setValue(servicioVendedor.buscar(
						orden.getSalesmanId()).getName());
				lblComentarios.setValue(orden.getComments());
				lblStatusChange.setValue(orden.getStatusChangedAt());
				List<OrdersDetail> lista = new ArrayList<OrdersDetail>();
				lista = servicioDetalleOrden.buscarPorOrden(orden.getOrderId());
				ltbDetalle.setModel(new ListModelList<OrdersDetail>(lista));

				
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
				wdwDetalle.onClose();

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

			}

			@Override
			public void eliminar() {
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
		botonera.getChildren().get(5).setVisible(false);
		botoneraDetalle.appendChild(botonera);
	}

}
