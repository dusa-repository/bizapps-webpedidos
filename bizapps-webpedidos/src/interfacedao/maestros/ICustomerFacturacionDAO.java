package interfacedao.maestros;

import modelo.maestros.CustomerFacturacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerFacturacionDAO extends JpaRepository<CustomerFacturacion, String> {

}
