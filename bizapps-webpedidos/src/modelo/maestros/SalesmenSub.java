package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import servicio.maestros.SVendedor;

/**
 * The persistent class for the salesmen_sub database table.
 * 
 */
@Entity
@Table(name = "salesmen_sub")
public class SalesmenSub implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalesmenSubPK id;

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/META-INF/ConfiguracionAplicacion.xml");

	public SalesmenSub() {
	}

	public SalesmenSubPK getId() {
		return this.id;
	}

	public void setId(SalesmenSubPK id) {
		this.id = id;
	}

	public static SVendedor getServicioVendedor() {
		return applicationContext.getBean(SVendedor.class);
	}

	public String nombre() {
		return getServicioVendedor().buscar(id.getSalesmanIdSub()).getName();
	}
}