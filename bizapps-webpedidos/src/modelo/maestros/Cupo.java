package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the Cupo database table.
 * 
 */
@Entity
public class Cupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CupoPK id;

	private int cantidad;

	private int consumido;

	private String description;

	private String desde;

	private String hasta;

	private int restante;

	public Cupo() {
	}

	public CupoPK getId() {
		return this.id;
	}

	public void setId(CupoPK id) {
		this.id = id;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getConsumido() {
		return this.consumido;
	}

	public void setConsumido(int consumido) {
		this.consumido = consumido;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDesde() {
		return this.desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return this.hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public int getRestante() {
		return this.restante;
	}

	public void setRestante(int restante) {
		this.restante = restante;
	}

	public Date traerFechaDesde() {

		String ano = String.valueOf(desde.charAt(0))
				+ String.valueOf(desde.charAt(1))
				+ String.valueOf(desde.charAt(2))
				+ String.valueOf(desde.charAt(3));
		String mes = String.valueOf(desde.charAt(4))
				+ String.valueOf(desde.charAt(5));
		String dia = String.valueOf(desde.charAt(6))
				+ String.valueOf(desde.charAt(7));

//		System.out.println(ano);
//		System.out.println(mes);
//		System.out.println(dia);

		Date fechaa = new Date();
		
		Timestamp fecha = new Timestamp(fechaa.getTime());

		fecha.setYear(Integer.valueOf(ano));
		fecha.setMonth(Integer.valueOf(mes));
		fecha.setDate(Integer.valueOf(dia));

//		System.out.println("fecha :" + fecha);

		return fecha;
	}

	public Date traerFechaHasta() {

		String ano = String.valueOf(hasta.charAt(0))
				+ String.valueOf(hasta.charAt(1))
				+ String.valueOf(hasta.charAt(2))
				+ String.valueOf(hasta.charAt(3));
		String mes = String.valueOf(hasta.charAt(4))
				+ String.valueOf(hasta.charAt(5));
		String dia = String.valueOf(hasta.charAt(6))
				+ String.valueOf(hasta.charAt(7));
//
//		System.out.println(ano);
//		System.out.println(mes);
//		System.out.println(dia);
		
		Date fechaa = new Date();
		Timestamp fecha = new Timestamp(fechaa.getTime());

		fecha.setYear(Integer.valueOf(ano));
		fecha.setMonth(Integer.valueOf(mes));
		fecha.setDate(Integer.valueOf(dia));

		//System.out.println("fecha hasta :" + fecha);

		return fecha;
	}

}