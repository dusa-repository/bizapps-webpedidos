package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the salesmen_sub database table.
 * 
 */
@Embeddable
public class SalesmenSubPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="salesman_id")
	private String salesmanId;

	@Column(name="salesman_id_sub")
	private String salesmanIdSub;

	public SalesmenSubPK() {
	}
	public String getSalesmanId() {
		return this.salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}
	public String getSalesmanIdSub() {
		return this.salesmanIdSub;
	}
	public void setSalesmanIdSub(String salesmanIdSub) {
		this.salesmanIdSub = salesmanIdSub;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SalesmenSubPK)) {
			return false;
		}
		SalesmenSubPK castOther = (SalesmenSubPK)other;
		return 
			this.salesmanId.equals(castOther.salesmanId)
			&& this.salesmanIdSub.equals(castOther.salesmanIdSub);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.salesmanId.hashCode();
		hash = hash * prime + this.salesmanIdSub.hashCode();
		
		return hash;
	}
}