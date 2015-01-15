package servicio.transacciones;

import interfacedao.maestros.IProductoDAO;
import interfacedao.transacciones.IDetalleOrdenDAO;
import interfacedao.transacciones.IOrdenDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SOrden")
public class SOrden {

	@Autowired
	private IOrdenDAO ordenDAO;

	@Autowired
	private IProductoDAO productoDAO;

	@Autowired
	private IDetalleOrdenDAO detalleOrdenDAO;

	public List<String> buscarStatus() {
		return ordenDAO.buscarStatus();
	}

	public List<Order> buscarTodasOrdenadas() {
		return ordenDAO.findAll();
	}

	public List<Order> buscarPorMarcaYFecha(String idMarca, String string,
			String string2) {
		 List<Order> ordeness = new ArrayList<Order>();
		// ordenDAO.findByOrderDateBetween(string,string2);
		List<String> productos = productoDAO.findByBrandString(idMarca);
		List<Integer> ordenes = detalleOrdenDAO.findByIdProductIdIn(productos);
		if(!ordenes.isEmpty())
		return ordenDAO.findByOrderDateBetweenAndOrderIdIn(string, string2,
				ordenes);
		else
			return ordeness;

	}

	public List<Order> buscarPorVendedorStatusYFecha(String idVendedor,
			String status, String value, String value2) {
		return ordenDAO.findBySalesmanIdAndStatusAndOrderDateBetween(
				idVendedor, status, value, value2);
	}

	public List<Order> buscarPorVendedorYFecha(String idVendedor,
			String format, String format2) {
		return ordenDAO.findBySalesmanIdAndOrderDateBetween(idVendedor, format,
				format2);
	}

	public List<Order> buscarEntreFechas(String format, String format2) {
		return ordenDAO.findByOrderDateBetween(format, format2);
	}

	public List<Order> buscarPorStatusYFecha(String status, String desde,
			String hasta) {
		return ordenDAO.findByStatusAndOrderDateBetween(status, desde, hasta);
	}

}
