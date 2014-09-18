package interfacedao.transacciones;


import java.util.List;

import modelo.maestros.OrdersDetail;
import modelo.maestros.OrdersDetailPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
	
	public interface  IDetalleOrdenDAO extends JpaRepository<OrdersDetail, OrdersDetailPK> {
		
		@Query("select distinct o.id.orderId from OrdersDetail o where o.id.productId in ?1")
		List<Integer> findByIdProductIdIn(List<String> productos);

		List<OrdersDetail> findByIdOrderId(int orderId);

}
