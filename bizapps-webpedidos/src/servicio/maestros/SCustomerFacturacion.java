package servicio.maestros;

import interfacedao.maestros.ICustomerFacturacionDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.maestros.CustomerFacturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("SCustomerFacturacion")
public class SCustomerFacturacion {

	@Autowired
	private ICustomerFacturacionDAO customerFacturacionDAO;

	public void guardar(CustomerFacturacion customer) {
		customerFacturacionDAO.save(customer);
	}

	public List<CustomerFacturacion> buscarTodosOrdenados() {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("warehouseFacturacion");
		ordenar.add("warehouse");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return customerFacturacionDAO.findAll(o);
	}

	public void eliminarVarios(List<CustomerFacturacion> eliminarLista) {
		customerFacturacionDAO.delete(eliminarLista);
	}

	public void eliminarClave(String id) {
		customerFacturacionDAO.delete(id);
	}

	public CustomerFacturacion buscar(String value) {
		return customerFacturacionDAO.findOne(value);
	}
}
