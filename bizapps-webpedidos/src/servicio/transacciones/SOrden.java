package servicio.transacciones;

import java.util.Date;
import java.util.List;

import interfacedao.transacciones.ICupoDAO;
import interfacedao.transacciones.IOrdenDAO;

import modelo.maestros.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

	
	@Service("SOrden")
	public class SOrden {

		@Autowired
		private IOrdenDAO ordenDAO;

		public List<String> buscarStatus() {
			return ordenDAO.buscarStatus();
		}

		public List<Order> buscarTodasOrdenadas() {
			return ordenDAO.findAll();
		}

		public List<Order> buscarPorMarcaYFecha(String idMarca, String string,
				String string2) {
			// TODO Auto-generated method stub
			return ordenDAO.findByOrderDateBetween(string,string2);
			
		}

		public List<Order> buscarPorVendedorStatusYFecha(String idVendedor,
				String status, String value, String value2) {
			return ordenDAO.findBySalesmanIdAndStatusAndOrderDateBetween(idVendedor,status,value,value2);
		}


}
