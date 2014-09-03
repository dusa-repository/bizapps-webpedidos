package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		Date fecha = null;
		try {
			fecha = formatter.parse(desde);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fecha;
	}

	public Date traerFechaHasta() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		Date fecha = null;
		try {
			fecha = formatter.parse(hasta);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fecha;
	}

}