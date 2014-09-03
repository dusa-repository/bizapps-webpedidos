package modelo.maestros;
import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the salesplan database table.
 * 
 */
@Entity
@Table(name="salesplan")
public class Salesplan implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalesplanPK id;

	private double done;

	private double planned;

	public Salesplan() {
	}

	public SalesplanPK getId() {
		return this.id;
	}

	public void setId(SalesplanPK id) {
		this.id = id;
	}

	public double getDone() {
		return this.done;
	}

	public void setDone(double done) {
		this.done = done;
	}

	public double getPlanned() {
		return this.planned;
	}

	public void setPlanned(double planned) {
		this.planned = planned;
	}

}