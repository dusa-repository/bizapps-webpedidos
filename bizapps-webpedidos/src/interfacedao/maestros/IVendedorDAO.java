package interfacedao.maestros;

import modelo.maestros.Product;
import modelo.maestros.Salesmen;

import org.springframework.data.jpa.repository.JpaRepository;

	
	public interface  IVendedorDAO extends JpaRepository<Salesmen, String> {

		Salesmen findBySalesmanId(String idVendedor);

}
