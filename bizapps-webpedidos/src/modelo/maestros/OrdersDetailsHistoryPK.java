package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the orders_details_history database table.
 * 
 */
@Embeddable
public class OrdersDetailsHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="order_id")
	private int orderId;

	@Column(name="product_id")
	private String productId;

	private String unit;

	public OrdersDetailsHistoryPK() {
	}
	public int getOrderId() {
		return this.orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return this.productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUnit() {
		return this.unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OrdersDetailsHistoryPK)) {
			return false;
		}
		OrdersDetailsHistoryPK castOther = (OrdersDetailsHistoryPK)other;
		return 
			(this.orderId == castOther.orderId)
			&& this.productId.equals(castOther.productId)
			&& this.unit.equals(castOther.unit);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderId;
		hash = hash * prime + this.productId.hashCode();
		hash = hash * prime + this.unit.hashCode();
		
		return hash;
	}
}