package modelo.maestros;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the salesmen_actions database table.
 * 
 */

@Embeddable
public class SalesmenActionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="salesman_id")
	private String salesmanId;

	@Column(name="action_id")
	private int actionId;


	public SalesmenActionPK() {
	}
	public String getSalesmanId() {
		return this.salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}
	public int getActionId() {
		return this.actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SalesmenActionPK)) {
			return false;
		}
		SalesmenActionPK castOther = (SalesmenActionPK)other;
		return 
			this.salesmanId.equals(castOther.salesmanId)
			&& (this.actionId == castOther.actionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.salesmanId.hashCode();
		hash = hash * prime + this.actionId;
		
		return hash;
	}

	
}