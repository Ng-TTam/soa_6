package com.example.tax.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tax.Entity.Person;
import com.example.tax.Repository.PersonRepository;
import com.example.tax.Utility.Utility;

@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepository;
	
	public List<Person> getAllPerSon (){
		return (List<Person>) personRepository.findAll();
	}
	
	public List<Person> getAllPersonOderByName (){
		return personRepository.getAllPersonsOrderedByName();
	}
	
	public List<Person> getAllPersonByKeyword (String keyword){
		return personRepository.getAllPersonsContainingKeyword(keyword);
	}
	
	public Optional<Person> getPerSonById(long pId){
		return personRepository.findById(pId);
	}
	
	public List<Person> getPersonsByAddress(String address){
		return personRepository.getPersonsByAddressContainingSpecificString(address);
	}
	
	public List<Person> getPersonByIncome(String incomeStr){
		List<Long>number = Utility.getNumberFromString(incomeStr);
		return personRepository.getPersonsByIncomeRange(number.get(0), number.get(1));
	}
	
	public List<Person> getPersonsByAddressAndIncome(String address, String incomeStr){
		List<Long>number = Utility.getNumberFromString(incomeStr);
		return personRepository.getPersonsByAddressContainingSpecificStringAndIncomeRange(address, number.get(0), number.get(1));
	}
}
