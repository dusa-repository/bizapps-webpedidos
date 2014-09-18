package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the balances database table.
 * 
 */
@Entity
@Table(name="balances")
public class Balance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BalancePK id;

	@Column(name="adjusted_taxable_amount")
	private double adjustedTaxableAmount;

	@Column(name="applied_credit_note")
	private double appliedCreditNote;

	@Column(name="applied_credit_note_iva")
	private double appliedCreditNoteIva;

	@Column(name="credit_note_dev")
	private double creditNoteDev;

	@Column(name="credit_note_fin_discount")
	private double creditNoteFinDiscount;

	@Column(name="credit_note_fin_discount_iva")
	private double creditNoteFinDiscountIva;

	@Column(name="exp_date")
	private String expDate;

	@Column(name="exp_days")
	private int expDays;

	@Column(name="general_net_iva")
	private double generalNetIva;

	@Column(name="invoice_amount")
	private double invoiceAmount;

	@Column(name="invoice_balance")
	private double invoiceBalance;

	@Column(name="issue_date")
	private String issueDate;

	@Column(name="iva_retention")
	private double ivaRetention;

	@Column(name="net_iva")
	private double netIva;

	private double payments;

	public Balance() {
	}

	public BalancePK getId() {
		return this.id;
	}

	public void setId(BalancePK id) {
		this.id = id;
	}

	public double getAdjustedTaxableAmount() {
		return this.adjustedTaxableAmount;
	}

	public void setAdjustedTaxableAmount(double adjustedTaxableAmount) {
		this.adjustedTaxableAmount = adjustedTaxableAmount;
	}

	public double getAppliedCreditNote() {
		return this.appliedCreditNote;
	}

	public void setAppliedCreditNote(double appliedCreditNote) {
		this.appliedCreditNote = appliedCreditNote;
	}

	public double getAppliedCreditNoteIva() {
		return this.appliedCreditNoteIva;
	}

	public void setAppliedCreditNoteIva(double appliedCreditNoteIva) {
		this.appliedCreditNoteIva = appliedCreditNoteIva;
	}

	public double getCreditNoteDev() {
		return this.creditNoteDev;
	}

	public void setCreditNoteDev(double creditNoteDev) {
		this.creditNoteDev = creditNoteDev;
	}

	public double getCreditNoteFinDiscount() {
		return this.creditNoteFinDiscount;
	}

	public void setCreditNoteFinDiscount(double creditNoteFinDiscount) {
		this.creditNoteFinDiscount = creditNoteFinDiscount;
	}

	public double getCreditNoteFinDiscountIva() {
		return this.creditNoteFinDiscountIva;
	}

	public void setCreditNoteFinDiscountIva(double creditNoteFinDiscountIva) {
		this.creditNoteFinDiscountIva = creditNoteFinDiscountIva;
	}

	public String getExpDate() {
		return this.expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public int getExpDays() {
		return this.expDays;
	}

	public void setExpDays(int expDays) {
		this.expDays = expDays;
	}

	public double getGeneralNetIva() {
		return this.generalNetIva;
	}

	public void setGeneralNetIva(double generalNetIva) {
		this.generalNetIva = generalNetIva;
	}

	public double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public double getInvoiceBalance() {
		return this.invoiceBalance;
	}

	public void setInvoiceBalance(double invoiceBalance) {
		this.invoiceBalance = invoiceBalance;
	}

	public String getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public double getIvaRetention() {
		return this.ivaRetention;
	}

	public void setIvaRetention(double ivaRetention) {
		this.ivaRetention = ivaRetention;
	}

	public double getNetIva() {
		return this.netIva;
	}

	public void setNetIva(double netIva) {
		this.netIva = netIva;
	}

	public double getPayments() {
		return this.payments;
	}

	public void setPayments(double payments) {
		this.payments = payments;
	}

}