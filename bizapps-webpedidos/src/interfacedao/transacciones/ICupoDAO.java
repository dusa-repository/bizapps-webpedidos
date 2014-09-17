package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Cupo;
import modelo.maestros.CupoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
	
	public interface  ICupoDAO extends JpaRepository<Cupo, CupoPK> {
		
		List<Cupo> findByIdVendedorAndIdMarca(String idVendedor, String idMarca);

		List<Cupo> findByCantidad(int i);

		@Query("select distinct cu.id.marca from Cupo cu where cu.id.vendedor ='0'")
		List<String> buscarMarcas();

		@Query("select c from Cupo c where c.id.vendedor ='0'")
		List<Cupo> buscarItems();

		Cupo findById(CupoPK pk);

		Object findByIdAndIdVendedor(CupoPK pk, String string);

		List<Cupo> findByIdVendedor(String idVendedor);

		List<Cupo> findByIdMarca(String idMarca);

		List<Cupo> findByIdVendedorNot(String string);

		List<Cupo> findByIdVendedorAndIdProductoStartingWithAllIgnoreCase(
				String string, String valor);

}
