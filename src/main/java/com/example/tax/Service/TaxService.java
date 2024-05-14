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
	
	public boolean updateTax(List<Tax> listTaxNew) {
		List<Tax> taxs = (ArrayList<Tax>) taxRepository.findAll();
		if(!checkValuesValidity(taxs)) return false;
		for(int i = 0; i < 7; i++) {
			if(!taxs.get(i).equals(listTaxNew.get(i))) {
				taxRepository.save(listTaxNew.get(i));
			}
		}
		return true;
	}
	
	private static boolean checkValuesValidity(List<Tax> taxList) {
        for (int i = 0; i < taxList.size(); i++) {
            String amountLargest = String.valueOf(taxList.get(i).getAmountLargest());
            String percentOfTax = String.valueOf(taxList.get(i).getPercentOfTax());

            if (amountLargest.matches(".*[a-zA-Z].*")) {
                return false;
            }
            if (percentOfTax.matches(".*[a-zA-Z].*")) {
                return false;
            }

            double currentAmount;
            double currentPercent;

            try {
                currentAmount = Double.parseDouble(amountLargest);
            } catch (NumberFormatException e) {
                return false;
            }

            try {
                currentPercent = Double.parseDouble(percentOfTax);
            } catch (NumberFormatException e) {
                return false;
            }

            if (currentAmount < 0 && i != 6) {
                return false;
            }

            if (currentPercent < 0) {
                return false;
            }

            if (currentPercent == 0) {
                return false;
            }

            if (currentAmount == 0) {
                return false;
            }

            // Kiểm tra xem nếu không phải là hàng cuối cùng
            if (i < taxList.size() - 2) {
                double nextAmount;
                double nextPercent;

                try {
                    nextAmount = Double.parseDouble(String.valueOf(taxList.get(i + 1).getAmountLargest()));
                    nextPercent = Double.parseDouble(String.valueOf(taxList.get(i + 1).getPercentOfTax()));
                } catch (NumberFormatException e) {
                    return false;
                }

                if (currentAmount >= nextAmount) {
                    return false;
                }

                if (currentPercent >= nextPercent) {
                    return false;
                }
            }

            if (i == taxList.size() - 2) {
                double nextPercent;

                try {
                    nextPercent = Double.parseDouble(String.valueOf(taxList.get(i + 1).getPercentOfTax()));
                } catch (NumberFormatException e) {
                    return false;
                }

                if (currentPercent >= nextPercent) {
                    return false;
                }

                if (nextPercent >= 100) {
                    System.out.println("Phần thuế suất hàng " + (i + 2) + " phải nhỏ hơn 100.");
                    return false;
                }
            }
        }

        return true;
    }

}
