package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orders_details database table.
 * 
 */
@Entity
@Table(name="orders_details")
public class OrdersDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrdersDetailPK id;

	@Column(name="customer_id")
	private String customerId;

	private double qty;

	private double subtotal;

	private String warehouse;

	public OrdersDetail() {
	}

	public OrdersDetailPK getId() {
		return this.id;
	}

	public void setId(OrdersDetailPK id) {
		this.id = id;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getQty() {
		return this.qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

}