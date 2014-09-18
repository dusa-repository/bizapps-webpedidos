package interfacedao.maestros;

import modelo.maestros.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

	public interface IClienteDAO extends JpaRepository<Customer, String> {

		Customer findByCustomerId(String customerId);

}
