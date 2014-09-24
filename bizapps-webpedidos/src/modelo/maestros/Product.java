package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name="products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_id")
	private String productId;

	@Column(name="alternative_unit")
	private String alternativeUnit;

	private String brand;

	@Column(name="conversion_rate")
	private double conversionRate;

	private String description;

	private double price1;

	private double price10;

	private double price2;

	private double price3;

	private double price4;

	private double price5;

	private double price6;

	private double price7;

	private double price8;

	private double price9;

	@Column(name="primary_unit")
	private String primaryUnit;

	private double stock;

	private String target;

	private String warehouse;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean estado;
	
	public Product() {
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAlternativeUnit() {
		return this.alternativeUnit;
	}

	public void setAlternativeUnit(String alternativeUnit) {
		this.alternativeUnit = alternativeUnit;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getConversionRate() {
		return this.conversionRate;
	}

	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice1() {
		return this.price1;
	}

	public void setPrice1(double price1) {
		this.price1 = price1;
	}

	public double getPrice10() {
		return this.price10;
	}

	public void setPrice10(double price10) {
		this.price10 = price10;
	}

	public double getPrice2() {
		return this.price2;
	}

	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	public double getPrice3() {
		return this.price3;
	}

	public void setPrice3(double price3) {
		this.price3 = price3;
	}

	public double getPrice4() {
		return this.price4;
	}

	public void setPrice4(double price4) {
		this.price4 = price4;
	}

	public double getPrice5() {
		return this.price5;
	}

	public void setPrice5(double price5) {
		this.price5 = price5;
	}

	public double getPrice6() {
		return this.price6;
	}

	public void setPrice6(double price6) {
		this.price6 = price6;
	}

	public double getPrice7() {
		return this.price7;
	}

	public void setPrice7(double price7) {
		this.price7 = price7;
	}

	public double getPrice8() {
		return this.price8;
	}

	public void setPrice8(double price8) {
		this.price8 = price8;
	}

	public double getPrice9() {
		return this.price9;
	}

	public void setPrice9(double price9) {
		this.price9 = price9;
	}

	public String getPrimaryUnit() {
		return this.primaryUnit;
	}

	public void setPrimaryUnit(String primaryUnit) {
		this.primaryUnit = primaryUnit;
	}

	public double getStock() {
		return this.stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}