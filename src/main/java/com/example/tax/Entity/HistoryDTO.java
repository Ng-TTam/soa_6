package com.example.tax.Entity;

import java.sql.Date;

public class HistoryDTO {
	
	private String name;
	
	private String location;
	
	private Date date;
	
	private int taxYear;
	
	private String taxPeriod;
	
	private String status;
	
	private long taxMoney;
	
	private int numberOfDayLate;
	
	private long finedAmount;
	
	private long totalPayment;
	
	private String typePayment;

	public HistoryDTO() {
		super();
	}

	public HistoryDTO(String name, String location, Date date, int taxYear, String taxPeriod, String status,
			long taxMoney, int numberOfDayLate, long finedAmount, long totalPayment, String typePayment) {
		super();
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTaxPeriod() {
		return taxPeriod;
	}

	public void setTaxPeriod(String taxPeriod) {
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
}
