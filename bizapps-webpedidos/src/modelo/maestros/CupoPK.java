package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the Cupo database table.
 * 
 */
@Embeddable
public class CupoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String vendedor;

	private String marca;

	private String producto;

	public CupoPK() {
	}
	public String getVendedor() {
		return this.vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public String getMarca() {
		return this.marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getProducto() {
		return this.producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CupoPK)) {
			return false;
		}
		CupoPK castOther = (CupoPK)other;
		return 
			this.vendedor.equals(castOther.vendedor)
			&& this.marca.equals(castOther.marca)
			&& this.producto.equals(castOther.producto);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendedor.hashCode();
		hash = hash * prime + this.marca.hashCode();
		hash = hash * prime + this.producto.hashCode();
		
		return hash;
	}
}