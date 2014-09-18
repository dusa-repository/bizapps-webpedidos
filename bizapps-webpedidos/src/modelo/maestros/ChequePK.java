package modelo.maestros;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the cheques database table.
 * 
 */
@Embeddable
public class ChequePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="customer_id")
	private String customerId;

	@Column(name="check_code")
	private String checkCode;

	@Column(name="check_number")
	private String checkNumber;

	public ChequePK() {
	}
	public String getCustomerId() {
		return this.customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCheckCode() {
		return this.checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getCheckNumber() {
		return this.checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ChequePK)) {
			return false;
		}
		ChequePK castOther = (ChequePK)other;
		return 
			this.customerId.equals(castOther.customerId)
			&& this.checkCode.equals(castOther.checkCode)
			&& this.checkNumber.equals(castOther.checkNumber);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.customerId.hashCode();
		hash = hash * prime + this.checkCode.hashCode();
		hash = hash * prime + this.checkNumber.hashCode();
		
		return hash;
	}
}