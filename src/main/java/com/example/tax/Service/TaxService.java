package com.example.tax.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tax.Entity.Tax;
import com.example.tax.Repository.TaxRepository;

@Service
public class TaxService {
	@Autowired
	TaxRepository taxRepository;
	
	public List<Tax> getAllTax(){
		return taxRepository.findAll();
	}
	
	public void updateTax(List<Tax> listTaxNew) {
		List<Tax> taxs = (ArrayList<Tax>) taxRepository.findAll();
		for(int i = 0; i < 7; i++) {
			if(!taxs.get(i).equals(listTaxNew.get(i))) {
				taxRepository.save(listTaxNew.get(i));
			}
		}
	}

}
