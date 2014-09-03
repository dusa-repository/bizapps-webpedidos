package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orders_status database table.
 * 
 */
@Entity
@Table(name="orders_status")
public class OrdersStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrdersStatusPK id;

	@Column(name="order_date")
	private String orderDate;

	@Column(name="status_description")
	private String statusDescription;

	public OrdersStatus() {
	}

	public OrdersStatusPK getId() {
		return this.id;
	}

	public void setId(OrdersStatusPK id) {
		this.id = id;
	}

	public String getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatusDescription() {
		return this.statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

}