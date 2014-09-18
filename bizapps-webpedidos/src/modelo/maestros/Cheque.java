package modelo.maestros;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the cheques database table.
 * 
 */
@Entity
@Table(name="cheques")
public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ChequePK id;

	private double amount;

	public Cheque() {
	}

	public ChequePK getId() {
		return this.id;
	}

	public void setId(ChequePK id) {
		this.id = id;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}