package servicio.maestros;
import interfacedao.maestros.IVendedorDAO;
import interfacedao.transacciones.ICupoDAO;

import java.util.List;

import modelo.maestros.Cupo;
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

}