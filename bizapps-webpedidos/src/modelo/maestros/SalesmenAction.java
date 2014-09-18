package modelo.maestros;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import servicio.maestros.SAction;


/**
 * The persistent class for the salesmen_actions database table.
 * 
 */
@Entity
@Table(name="salesmen_actions")
public class SalesmenAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalesmenActionPK id;

	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/META-INF/ConfiguracionAplicacion.xml");
	
	public SalesmenAction() {
	}

	public SalesmenActionPK getId() {
		return this.id;
	}

	public void setId(SalesmenActionPK id) {
		this.id = id;
	}	
	
	public static SAction getServicioAccion() {
		return applicationContext.getBean(SAction.class);
	}

	
	public String accion(){
		return getServicioAccion().buscar(id.getActionId()).getName();
	}
	


}