package interfacedao.maestros;

import java.util.List;

import modelo.maestros.Salesmen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IVendedorDAO extends JpaRepository<Salesmen, String> {

	Salesmen findBySalesmanId(String idVendedor);

	List<Salesmen> findBySalesmanIdNotIn(List<String> ids);

}
