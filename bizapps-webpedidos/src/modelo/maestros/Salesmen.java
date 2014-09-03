package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the salesmen database table.
 * 
 */
@Entity
@Table(name="salesmen")
public class Salesmen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="salesman_id")
	private String salesmanId;

	@Column(name="bottom_serial")
	private int bottomSerial;

	private String comments;

	@Column(name="last_serial")
	private int lastSerial;

	private short logged;

	private String mail;

	@Column(name="mail_coordinador")
	private String mailCoordinador;

	private short movil;

	private String name;

	private String region;

	@Column(name="session_id")
	private String sessionId;

	@Column(name="top_serial")
	private int topSerial;

	public Salesmen() {
	}

	public String getSalesmanId() {
		return this.salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public int getBottomSerial() {
		return this.bottomSerial;
	}

	public void setBottomSerial(int bottomSerial) {
		this.bottomSerial = bottomSerial;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getLastSerial() {
		return this.lastSerial;
	}

	public void setLastSerial(int lastSerial) {
		this.lastSerial = lastSerial;
	}

	public short getLogged() {
		return this.logged;
	}

	public void setLogged(short logged) {
		this.logged = logged;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailCoordinador() {
		return this.mailCoordinador;
	}

	public void setMailCoordinador(String mailCoordinador) {
		this.mailCoordinador = mailCoordinador;
	}

	public short getMovil() {
		return this.movil;
	}

	public void setMovil(short movil) {
		this.movil = movil;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getTopSerial() {
		return this.topSerial;
	}

	public void setTopSerial(int topSerial) {
		this.topSerial = topSerial;
	}

}