package com.example.tax.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "debt")
public class Debt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "tax_code")
	private String taxCode;
	
	@Column(name = "tax_period")
	private String taxPeriod;
	
	@Column(name = "tax_year")
	private int taxYear;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "tax_money")
	private long taxMoney;

	public Debt() {
		super();
	}

	public Debt(long id, String name, String address, String taxCode, String taxPeriod, int taxYear, String status,
			long taxMoney) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.taxCode = taxCode;
		this.taxPeriod = taxPeriod;
		this.taxYear = taxYear;
		this.status = status;
		this.taxMoney = taxMoney;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getTaxPeriod() {
		return taxPeriod;
	}

	public void setTaxPeriod(String taxPeriod) {
		this.taxPeriod = taxPeriod;
	}

	public int getTaxYear() {
		return taxYear;
	}

	public void setTaxYear(int taxYear) {
		this.taxYear = taxYear;
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

}
