package servicio.maestros;


import interfacedao.maestros.IClienteDAO;
import modelo.maestros.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCliente")
public class SCliente {
	
	@Autowired
	private IClienteDAO clienteDAO;

	public Customer buscar(String customerId) {
		return clienteDAO.findByCustomerId(customerId);
	}

}
