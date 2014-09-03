package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the balances database table.
 * 
 */
@Embeddable
public class BalancePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="customer_id")
	private String customerId;

	@Column(name="doc_type")
	private String docType;

	@Column(name="doc_number")
	private String docNumber;

	public BalancePK() {
	}
	public String getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDocType() {
		return this.docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocNumber() {
		return this.docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BalancePK)) {
			return false;
		}
		BalancePK castOther = (BalancePK)other;
		return 
			this.customerId.equals(castOther.customerId)
			&& this.docType.equals(castOther.docType)
			&& this.docNumber.equals(castOther.docNumber);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.customerId.hashCode();
		hash = hash * prime + this.docType.hashCode();
		hash = hash * prime + this.docNumber.hashCode();
		
		return hash;
	}
}