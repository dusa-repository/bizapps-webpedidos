package interfacedao.transacciones;

import java.util.List;

import modelo.maestros.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

	
	public interface  IOrdenDAO extends JpaRepository<Order, String> {

		@Query("select distinct o.status from Order o")
		List<String> buscarStatus();

		List<Order> findByOrderDateBetween(String string, String string2);

		List<Order> findBySalesmanIdAndStatusAndOrderDateBetween(
				String idVendedor, String status, String value, String value2);

		List<Order> findBySalesmanIdAndOrderDateBetween(String idVendedor,
				String format, String format2);

		List<Order> findByOrderDateBetweenAndOrderIdIn(String string,
				String string2, List<Integer> ordenes);

		List<Order> findByStatusAndOrderDateBetween(String status,
				String desde, String hasta);

}
