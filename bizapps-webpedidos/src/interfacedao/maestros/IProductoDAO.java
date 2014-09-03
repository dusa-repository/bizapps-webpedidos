package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProductoDAO extends JpaRepository<Product, String> {

	Product findByProductId(String producto);
	
	@Query("select distinct p.brand from Product p")
	List<String> findMarcas();

	List<Product> findByBrand(String marca);


}
