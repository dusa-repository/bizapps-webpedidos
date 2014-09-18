package servicio.maestros;
import interfacedao.maestros.IVendedorDAO;

import java.util.List;

import modelo.maestros.Salesmen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SVendedor")
public class SVendedor {

	@Autowired
	private IVendedorDAO vendedorDAO;

	public List<Salesmen> buscarTodosOrdenados() {
		// TODO Auto-generated method stub
		return vendedorDAO.findAll();
	}

	public Salesmen buscar(String idVendedor) {
		// TODO Auto-generated method stub
		return vendedorDAO.findBySalesmanId(idVendedor);
	}

	public void guardar(Salesmen vendedor) {
		vendedorDAO.save(vendedor);
		
	}

	public void eliminarVarios(List<Salesmen> eliminarLista) {
		vendedorDAO.delete(eliminarLista);
		
	}

	public void eliminarClave(String id) {
		vendedorDAO.delete(id);
	}

	public List<Salesmen> buscarSubordinadosDisponibles(List<String> ids) {
		return vendedorDAO.findBySalesmanIdNotIn(ids);
	}

}
