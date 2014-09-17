package controlador.reporte;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.Case;

import modelo.maestros.Cupo;
import modelo.maestros.Salesmen;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.maestros.CGenerico;

public class CReportes extends CGenerico {

	@Wire
	private Div divReporte;
	@Wire
	private Textbox txtVendedor;
	@Wire
	private Combobox cmbMarca;
	@Wire
	private Div botoneraReporte;
	@Wire
	private Div divCatalogoVendedor2;
	private String nombre;
	protected Connection conexion;
	private String tipo;
	Catalogo<Salesmen> catalogoVendedor;
	private String idVendedor = "";

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
		List<String> listaProduct = new ArrayList<String>();
		listaProduct.add("Todas");
		listaProduct.addAll(servicioProducto.buscarMarcas());
		cmbMarca.setModel(new ListModelList<String>(listaProduct));

		switch (nombre) {
		case "Cupos por Vendedor/Marca/Item":
			tipo = "1";
			break;
		case "Cupos por Marca/Vendedor/Item":
			tipo = "2";
			break;
		case "Cupos por Marca/Item/Vendedor":
			tipo = "3";
			break;
		default:
			break;
		}
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divReporte, nombre, tabs);
			}

			@Override
			public void limpiar() {

				limpiarCampos();

			}

			@Override
			public void guardar() {

			}

			@Override
			public void eliminar() {

			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

				if (validar()) {
					String marca = cmbMarca.getValue();
					List<Cupo> cupos = new ArrayList<Cupo>();
					String vendedor = idVendedor;
					if (!idVendedor.equals("Todos") && !marca.equals("Todas")) {
						cupos = servicioCupo.buscarPorVendedoryMarca(
								idVendedor, marca);
					} else {
						if (marca.equals("Todas") && idVendedor.equals("Todos")) {
							cupos = servicioCupo.buscarTodos();
						} else {
							if (idVendedor.equals("Todos")) {
								cupos = servicioCupo.buscarCuposMarca(marca);
							} else {
								cupos = servicioCupo
										.buscarCuposVendedor(vendedor);
							}
						}
					}

					if (marca.equals("Todas"))
						marca = "";
					if (txtVendedor.getValue().equals("Todos"))
						vendedor = "";
					if (!cupos.isEmpty()) {
						Clients.evalJavaScript("window.open('/bizapps-webpedidos/Generador?valor1="
								+ tipo
								+ "&valor2="
								+ marca
								+ "&valor3="
								+ vendedor
								+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
					} else
						msj.mensajeAlerta(Mensaje.noHayRegistros);

				}

			}

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void annadir() {
				// TODO Auto-generated method stub

			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub

			}
		};
		Button salir = (Button) botonera.getChildren().get(7);
		Button reporte = (Button) botonera.getChildren().get(6);
		Button limpiar = (Button) botonera.getChildren().get(5);
		botonera.getChildren().remove(7);
		botonera.getChildren().remove(6);
		botonera.getChildren().remove(5);
		botonera.appendChild(reporte);
		botonera.appendChild(limpiar);
		botonera.appendChild(salir);
		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botoneraReporte.appendChild(botonera);
	}

	@Listen("onClick = #btnBuscarVendedor")
	public void mostrarCatalogoVendedor() {
		final List<Salesmen> vendedores = new ArrayList<Salesmen>();
		Salesmen vendedorTodo = new Salesmen();
		vendedorTodo.setSalesmanId("Todos");
		vendedorTodo.setRegion("Todos");
		vendedorTodo.setName("Todos");
		vendedores.add(vendedorTodo);
		vendedores.addAll(servicioVendedor.buscarTodosOrdenados());
		catalogoVendedor = new Catalogo<Salesmen>(divCatalogoVendedor2,
				"Vendedores", vendedores, true, false, false, "Codigo",
				"Nombre", "Region") {

			@Override
			protected List<Salesmen> buscar(List<String> valores) {

				List<Salesmen> lista = new ArrayList<Salesmen>();

				for (Salesmen vendedor : vendedores) {
					if ((vendedor.getSalesmanId().toLowerCase()
							.startsWith(valores.get(0)) || vendedor
							.getSalesmanId().toUpperCase()
							.startsWith(valores.get(0)))
							&& (vendedor.getName().trim().toLowerCase()
									.startsWith(valores.get(1)) || vendedor
									.getName().trim().toUpperCase()
									.startsWith(valores.get(1)))
							&& (vendedor.getRegion().trim().toLowerCase()
									.startsWith(valores.get(2)) || vendedor
									.getRegion().trim().toUpperCase()
									.startsWith(valores.get(2)))) {
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
		catalogoVendedor.setParent(divCatalogoVendedor2);
		catalogoVendedor.doModal();

	}

	@Listen("onSeleccion = #divCatalogoVendedor2")
	public void seleccionar() {
		Salesmen vendedor = catalogoVendedor.objetoSeleccionadoDelCatalogo();
		txtVendedor.setValue(vendedor.getName());
		idVendedor = vendedor.getSalesmanId();
		catalogoVendedor.setParent(null);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (cmbMarca.getText().compareTo("") == 0
				|| txtVendedor.getText().compareTo("") == 0) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public void limpiarCampos() {
		idVendedor = "";
		cmbMarca.setValue("");
		txtVendedor.setValue("");
	}

	public byte[] reporte(String part1, String part2, String part3) {
		byte[] fichero = null;

		conexion = null;
		try {

			ClassLoader cl = this.getClass().getClassLoader();
			InputStream fis = null;

			Map parameters = new HashMap();

			parameters.put("marca", part2);
			parameters.put("vendedor", part3);

			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			String url = "jdbc:sqlserver://localhost:1433;DatabaseName=dusa_web_pedido";
			String user = "client";
			String password = "123";

			switch (part1) {
			case "1":
				fis = (cl.getResourceAsStream("/reporte/restriccion01.jasper"));
				break;
			case "2":
				fis = (cl.getResourceAsStream("/reporte/restriccion02.jasper"));
				break;
			case "3":
				fis = (cl.getResourceAsStream("/reporte/restriccion03.jasper"));
				break;
			default:
				break;
			}
			conexion = java.sql.DriverManager
					.getConnection(url, user, password);
			try {

				if (fichero == null) {
					fichero = JasperRunManager.runReportToPdf(fis, parameters,
							conexion);
				}

			} catch (JRException ex) {
				ex.printStackTrace();
				System.out.println(ex.toString());
			}

			if (conexion != null) {
				conexion.close();
			}

		} catch (SQLException e) {
			System.out.println("Error de conexi�n: " + e.getMessage());
			System.exit(4);
		}
		return fichero;

	}

}
