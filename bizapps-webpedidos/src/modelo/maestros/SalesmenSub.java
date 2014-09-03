package modelo.maestros;
import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the salesmen_sub database table.
 * 
 */
@Entity
@Table(name="salesmen_sub")
public class SalesmenSub implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalesmenSubPK id;

	public SalesmenSub() {
	}

	public SalesmenSubPK getId() {
		return this.id;
	}

	public void setId(SalesmenSubPK id) {
		this.id = id;
	}

}