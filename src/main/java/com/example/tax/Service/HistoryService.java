package com.example.tax.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tax.Entity.History;
import com.example.tax.Entity.HistoryDTO;
import com.example.tax.Entity.Person;
import com.example.tax.Repository.HistoryRepository;
@Service
public class HistoryService {
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private PersonService personService;
	
	public List<HistoryDTO> getTaxHistoryByPersonId (long personId){
		List<History> historys = new ArrayList<>();
		List<HistoryDTO> historyDTOs = new ArrayList<>();
		historys = historyRepository.findByPersonId(personId);
		Optional<Person> perOptional = personService.getPerSonById(personId);
		for (History history : historys) {
		    HistoryDTO historyDTO = new HistoryDTO();
		    if (!perOptional.isEmpty()) {
		    	historyDTO.setName(perOptional.get().getName());
		    	historyDTO.setTaxPeriod(perOptional.get().getTypePeriod()+" "+history.getTaxPeriod());
		    }
		    historyDTO.setLocation(history.getLocation());
		    historyDTO.setDate(history.getDate());
		    historyDTO.setTaxYear(history.getTaxYear());
		    historyDTO.setStatus(history.getStatus());
		    historyDTO.setTaxMoney(history.getTaxMoney());
		    historyDTO.setNumberOfDayLate(history.getNumberOfDayLate());
		    historyDTO.setFinedAmount(history.getFinedAmount());
		    historyDTO.setTotalPayment(history.getTotalPayment());
		    historyDTO.setTypePayment(history.getTypePayment());
		    
		    historyDTOs.add(historyDTO);
		}
		return historyDTOs;
	}
	
	public long getTotalMoneyByMonthAndYear (int month, int year) {
		return historyRepository.getTotalTaxMoneyByMonthAndYear(month, year);
	}
	
	public long getTotalMoneyByQuarterAndYear (int quarter, int year) {
		return historyRepository.getTotalTaxMoneyByQuarterAndYear(quarter, year);
	}
	
	public long getTotalMoneyByIncome (long income1, long income2 , int year) {
		return historyRepository.totalTaxMoneyByIncome(income1, income2, year);
	}
}
