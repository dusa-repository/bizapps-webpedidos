package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the customers database table.
 * 
 */
@Entity
@Table(name="customers")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="customer_id")
	private String customerId;

	@Column(name="credit_limit")
	private double creditLimit;

	private int diasRequerido;

	private String name;

	@Column(name="price_index")
	private short priceIndex;

	private String region;

	@Column(name="salesman_id")
	private String salesmanId;

	private String warehouse;

	public Customer() {
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public int getDiasRequerido() {
		return this.diasRequerido;
	}

	public void setDiasRequerido(int diasRequerido) {
		this.diasRequerido = diasRequerido;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getPriceIndex() {
		return this.priceIndex;
	}

	public void setPriceIndex(short priceIndex) {
		this.priceIndex = priceIndex;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSalesmanId() {
		return this.salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

}