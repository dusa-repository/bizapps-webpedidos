package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


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

	public SalesmenAction() {
	}

	public SalesmenActionPK getId() {
		return this.id;
	}

	public void setId(SalesmenActionPK id) {
		this.id = id;
	}

}