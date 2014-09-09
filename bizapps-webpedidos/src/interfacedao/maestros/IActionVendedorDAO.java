package interfacedao.maestros;

import java.util.List;

import modelo.maestros.SalesmenAction;
import modelo.maestros.SalesmenActionPK;

import org.springframework.data.jpa.repository.JpaRepository;
	
	public interface IActionVendedorDAO extends JpaRepository<SalesmenAction, SalesmenActionPK> {

		List<SalesmenAction> findByIdSalesmanId(String salesmanId);

}
