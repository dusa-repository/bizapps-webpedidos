package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the customer_facturacion database table.
 * 
 */
@Entity
@Table(name="customer_facturacion")
@NamedQuery(name="CustomerFacturacion.findAll", query="SELECT c FROM CustomerFacturacion c")
public class CustomerFacturacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="warehouse_facturacion")
	private String warehouseFacturacion;

	private String warehouse;

	public CustomerFacturacion() {
	}

	public String getWarehouseFacturacion() {
		return this.warehouseFacturacion;
	}

	public void setWarehouseFacturacion(String warehouseFacturacion) {
		this.warehouseFacturacion = warehouseFacturacion;
	}

	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

}