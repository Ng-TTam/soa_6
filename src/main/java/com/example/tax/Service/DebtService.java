package com.example.tax.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tax.Entity.Debt;
import com.example.tax.Repository.DebtRepository;

@Service
public class DebtService {
	@Autowired
	private DebtRepository debtRepository;
	
	public long getSumTaxMoney(String time,int year) {
		return debtRepository.sumTaxMoney(time, year);
	}
	
	public long getSumDebtByIncome(long income1, long income2, int year) {
		return debtRepository.sumDebtByIncome(income1, income2, year);
	}
	
	public List<Debt> getDeabtByAddressAndYear (String address, int year){
		return debtRepository.allDeabtByAddressAndYear(address, year);
	}
	
	public List<Debt> getDeabtByAddressAndYearAndTypeMonth(String address,int year, String month){
		return debtRepository.deabtByAddressAndYearAndTypeMonth(address, year, month);
	}
	
	public List<Debt> getDeabtByAddressAndYearAndTypeQuarter (String address, int year,String quarter){
		return debtRepository.deabtByAddressAndYearAndTypeQuarter(address, year, quarter);
	}
}
