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

	Product findByProductIdAndBrand(String productoRef, String marcaRef);

	List<Product> findByProductIdStartingWithAllIgnoreCase(String valor);

	@Query("select distinct p.productId from Product p where p.brand = ?1")
	List<String> findByBrandString(String idMarca);

	List<Product> findByEstadoTrue();

	List<Product> findByBrandAndEstadoTrue(String idMarca);

}
