package modelo.maestros;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	private int orderId;

	private double amount;

	private String comments;

	@Column(name="customer_id")
	private String customerId;

	@Column(name="delivery_date")
	private String deliveryDate;

	@Column(name="order_date")
	private String orderDate;

	@Column(name="order_time")
	private String orderTime;

	@Column(name="required_date")
	private String requiredDate;

	@Column(name="salesman_id")
	private String salesmanId;

	private String status;

	@Column(name="status_changed_at")
	private String statusChangedAt;

	@Column(name="status_changed_on")
	private String statusChangedOn;

	public Order() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getRequiredDate() {
		return this.requiredDate;
	}

	public void setRequiredDate(String requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getSalesmanId() {
		return this.salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusChangedAt() {
		return this.statusChangedAt;
	}

	public void setStatusChangedAt(String statusChangedAt) {
		this.statusChangedAt = statusChangedAt;
	}

	public String getStatusChangedOn() {
		return this.statusChangedOn;
	}

	public void setStatusChangedOn(String statusChangedOn) {
		this.statusChangedOn = statusChangedOn;
	}

}