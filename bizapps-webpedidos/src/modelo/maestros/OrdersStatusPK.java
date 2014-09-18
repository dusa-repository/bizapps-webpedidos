package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the orders_status database table.
 * 
 */
@Embeddable
public class OrdersStatusPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="order_id")
	private int orderId;

	@Column(name="customer_id")
	private String customerId;

	@Column(name="status_id")
	private String statusId;

	public OrdersStatusPK() {
	}
	public int getOrderId() {
		return this.orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStatusId() {
		return this.statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrdersStatusPK)) {
			return false;
		}
		OrdersStatusPK castOther = (OrdersStatusPK)other;
		return 
			(this.orderId == castOther.orderId)
			&& this.customerId.equals(castOther.customerId)
			&& this.statusId.equals(castOther.statusId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderId;
		hash = hash * prime + this.customerId.hashCode();
		hash = hash * prime + this.statusId.hashCode();
		
		return hash;
	}
}