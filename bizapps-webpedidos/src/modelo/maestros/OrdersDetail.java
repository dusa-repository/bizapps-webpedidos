package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import servicio.maestros.SProducto;

/**
 * The persistent class for the orders_details database table.
 * 
 */
@Entity
@Table(name = "orders_details")
public class OrdersDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrdersDetailPK id;

	@Column(name = "customer_id")
	private String customerId;

	private double qty;

	private double subtotal;

	private String warehouse;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/META-INF/ConfiguracionAplicacion.xml");

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

	public static SProducto getServicioProducto() {
		return applicationContext.getBean(SProducto.class);
	}

	public String nombre() {
		if (getServicioProducto().buscar(id.getProductId()) != null)
			return getServicioProducto().buscar(id.getProductId())
					.getDescription();
		else
			return id.getProductId();
	}

}