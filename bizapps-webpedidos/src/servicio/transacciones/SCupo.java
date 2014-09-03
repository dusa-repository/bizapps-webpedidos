package servicio.transacciones;
import interfacedao.transacciones.ICupoDAO;

import java.util.List;

import modelo.maestros.Cupo;
import modelo.maestros.CupoPK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCupo")
public class SCupo {

	@Autowired
	private ICupoDAO cupoDAO;

	public List<Cupo> buscarTodos() {
		// TODO Auto-generated method stub
		return cupoDAO.findAll();
	}
	
	public List<Cupo> buscarNinguno() {
		// TODO Auto-generated method stub
		return cupoDAO.findByCantidad(-1);
	}

	public void guardar(Cupo cupo) {
		cupoDAO.save(cupo);
		
	}

	public List<Cupo> buscarPorVendedoryMarca(String idVendedor, String idMarca) {
		return cupoDAO.findByIdVendedorAndIdMarca(idVendedor,idMarca);
	}

	public List<String> buscarMarcasConCero() {
		// TODO Auto-generated method stub
		return cupoDAO.buscarMarcas();
	}

	public List<Cupo> buscarItemsRestringidos() {
		// TODO Auto-generated method stub
		return cupoDAO.buscarItems();
	}

	public Cupo buscar(CupoPK pk) {
		// TODO Auto-generated method stub
		return cupoDAO.findById(pk);
	}

	public Object buscarPorItems(CupoPK pk) {
		return cupoDAO.findByIdAndIdVendedor(pk,"0");
	}

	public List<Cupo> buscarCuposVendedor(String idVendedor) {
		return cupoDAO.findByIdVendedor(idVendedor);
	}

	public void eliminarVarios(List<Cupo> eliminarLista) {
		cupoDAO.delete(eliminarLista);
		
	}

	public List<Cupo> buscarCuposMarca(String idMarca) {
		return cupoDAO.findByIdMarca(idMarca);
	}

	public List<Cupo> buscarCuposActivos() {
		return cupoDAO.findByIdVendedorNot("0");
	}

}
