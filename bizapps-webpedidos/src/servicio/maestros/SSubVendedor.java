package servicio.maestros;

import interfacedao.maestros.ISubVendedorDAO;

import java.util.List;

import modelo.maestros.SalesmenSub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	
	@Service("SSubVendedor")
	public class SSubVendedor {

		@Autowired
		private ISubVendedorDAO subVendedorDAO;

		public List<SalesmenSub> buscarSubordinadosDeVendedor(String salesmanId) {
			// TODO Auto-generated method stub
			return subVendedorDAO.findByIdSalesmanId(salesmanId);
		}

		public void guardar(List<SalesmenSub> subordinados) {
			subVendedorDAO.save(subordinados);
		}

		public void eliminarVarios(List<SalesmenSub> acteriores) {
			subVendedorDAO.delete(acteriores);
			
		}

}
