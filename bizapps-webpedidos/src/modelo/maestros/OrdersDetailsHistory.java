package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orders_details_history database table.
 * 
 */
@Entity
@Table(name="orders_details_history")
public class OrdersDetailsHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrdersDetailsHistoryPK id;

	private double qty;

	private double subtotal;

	public OrdersDetailsHistory() {
	}

	public OrdersDetailsHistoryPK getId() {
		return this.id;
	}

	public void setId(OrdersDetailsHistoryPK id) {
		this.id = id;
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

}