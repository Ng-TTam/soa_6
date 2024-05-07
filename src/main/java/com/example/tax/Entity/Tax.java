package com.example.tax.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tax")
public class Tax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "tax_backet")
	private int taxBacket;
	
	@Column(name = "amount_least")
	private int amountLeast;
	
	@Column(name = "amount_largest")
	private int amountLargest;
	
	@Column(name = "percent_of_tax")
	private int percentOfTax;

	public Tax(int id, int taxBacket, int amountLeast, int amountLargest, int percentOfTax) {
		super();
		this.id = id;
		this.taxBacket = taxBacket;
		this.amountLeast = amountLeast;
		this.amountLargest = amountLargest;
		this.percentOfTax = percentOfTax;
	}

	public Tax() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaxBacket() {
		return taxBacket;
	}

	public void setTaxBacket(int taxBacket) {
		this.taxBacket = taxBacket;
	}

	public int getAmountLeast() {
		return amountLeast;
	}

	public void setAmountLeast(int amountLeast) {
		this.amountLeast = amountLeast;
	}

	public int getAmountLargest() {
		return amountLargest;
	}

	public void setAmountLargest(int amountLargest) {
		this.amountLargest = amountLargest;
	}

	public int getPercentOfTax() {
		return percentOfTax;
	}

	public void setPercentOfTax(int percentOfTax) {
		this.percentOfTax = percentOfTax;
	}
	

}
