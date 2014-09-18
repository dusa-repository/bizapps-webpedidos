package servicio.maestros;

import interfacedao.maestros.IActionVendedorDAO;

import java.util.List;

import modelo.maestros.SalesmenAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SActionVendedor")
public class SActionVendedor {

	@Autowired
	private IActionVendedorDAO actionVendedorDAO;

	public List<SalesmenAction> buscarTodos() {
		return actionVendedorDAO.findAll();
	}

	public List<SalesmenAction> buscarAccionesDeVendedor(String salesmanId) {
		return actionVendedorDAO.findByIdSalesmanId(salesmanId);
	}

	public void guardar(List<SalesmenAction> acciones) {
		actionVendedorDAO.save(acciones);
		
	}

	public void eliminarVarios(List<SalesmenAction> acteriores) {
		actionVendedorDAO.delete(acteriores);
		
	}
}
