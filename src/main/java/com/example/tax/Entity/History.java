package com.example.tax.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "history")
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "tax_year")
	private int taxYear;
	
	@Column(name = "tax_period")
	private int taxPeriod;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "tax_money")
	private long taxMoney;
	
	@Column(name = "number_of_days_late")
	private int numberOfDayLate;
	
	@Column(name = "fined_amount")
	private long finedAmount;
	
	@Column(name = "total_payment")
	private long totalPayment;
	
	@Column(name = "type_payment")
	private String typePayment;

	public History() {
		super();
	}

	public History(long id, Person person, String location, Date date, int taxYear, int taxPeriod, String status,
			long taxMoney, int numberOfDayLate, long finedAmount, long totalPayment, String typePayment) {
		super();
		this.id = id;
		this.person = person;
		this.location = location;
		this.date = date;
		this.taxYear = taxYear;
		this.taxPeriod = taxPeriod;
		this.status = status;
		this.taxMoney = taxMoney;
		this.numberOfDayLate = numberOfDayLate;
		this.finedAmount = finedAmount;
		this.totalPayment = totalPayment;
		this.typePayment = typePayment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(int taxYear) {
		this.taxYear = taxYear;
	}

	public int getTaxPeriod() {
		return taxPeriod;
	}

	public void setTaxPeriod(int taxPeriod) {
		this.taxPeriod = taxPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(long taxMoney) {
		this.taxMoney = taxMoney;
	}

	public int getNumberOfDayLate() {
		return numberOfDayLate;
	}

	public void setNumberOfDayLate(int numberOfDayLate) {
		this.numberOfDayLate = numberOfDayLate;
	}

	public long getFinedAmount() {
		return finedAmount;
	}

	public void setFinedAmount(long finedAmount) {
		this.finedAmount = finedAmount;
	}

	public long getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(long totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}

	@Override
	public String toString() {
		return "History [id=" + id + ", person=" + person + ", location=" + location + ", date=" + date + ", taxYear="
				+ taxYear + ", taxPeriod=" + taxPeriod + ", status=" + status + ", taxMoney=" + taxMoney
				+ ", numberOfDayLate=" + numberOfDayLate + ", finedAmount=" + finedAmount + ", totalPayment="
				+ totalPayment + ", typePayment=" + typePayment + "]";
	}
}
