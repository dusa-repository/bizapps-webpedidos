package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Salesmen;
import modelo.maestros.SalesmenSub;
import modelo.maestros.SalesmenSubPK;

import org.springframework.data.jpa.repository.JpaRepository;

	
	public interface ISubVendedorDAO extends JpaRepository<SalesmenSub, SalesmenSubPK> {

		List<SalesmenSub> findByIdSalesmanId(String salesmanId);


}
