package com.example.tax.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "tax_code")
	private String taxCode;
	
	@Column(name = "card_code")
	private String cardCode;
	
	@Column(name = "birth")
	private Date birth;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "income")
	private long income;
	
	@Column(name = "date_register")
	private Date dateRegister;
	
	@Column(name = "type_period")
	private String typePeriod;

	public Person() {
		super();
	}

	public Person(long id, String name, String taxCode, String cardCode, Date birth, String phoneNumber, String address,
			long income, Date dateRegister, String typePeriod) {
		super();
		this.id = id;
		this.name = name;
		this.taxCode = taxCode;
		this.cardCode = cardCode;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.income = income;
		this.dateRegister = dateRegister;
		this.typePeriod = typePeriod;
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

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getIncome() {
		return income;
	}

	public void setIncome(long income) {
		this.income = income;
	}

	public Date getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}

	public String getTypePeriod() {
		return typePeriod;
	}

	public void setTypePeriod(String typePeriod) {
		this.typePeriod = typePeriod;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", taxCode=" + taxCode + ", cardCode=" + cardCode + ", birth="
				+ birth + ", phoneNumber=" + phoneNumber + ", address=" + address + ", income=" + income
				+ ", dateRegister=" + dateRegister + ", typePeriod=" + typePeriod + "]";
	}
}
