package modelo.maestros;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the salesplan database table.
 * 
 */
@Embeddable
public class SalesplanPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="fiscal_year")
	private String fiscalYear;

	private String year;

	private String month;

	@Column(name="salesman_id")
	private String salesmanId;

	@Column(name="product_id")
	private String productId;

	public SalesplanPK() {
	}
	public String getFiscalYear() {
		return this.fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return this.month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getSalesmanId() {
		return this.salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}
	public String getProductId() {
		return this.productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SalesplanPK)) {
			return false;
		}
		SalesplanPK castOther = (SalesplanPK)other;
		return 
			this.fiscalYear.equals(castOther.fiscalYear)
			&& this.year.equals(castOther.year)
			&& this.month.equals(castOther.month)
			&& this.salesmanId.equals(castOther.salesmanId)
			&& this.productId.equals(castOther.productId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fiscalYear.hashCode();
		hash = hash * prime + this.year.hashCode();
		hash = hash * prime + this.month.hashCode();
		hash = hash * prime + this.salesmanId.hashCode();
		hash = hash * prime + this.productId.hashCode();
		
		return hash;
	}
}