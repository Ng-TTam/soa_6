package com.example.tax.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tax.Entity.Person;
@Repository
public interface PersonRepository extends CrudRepository<Person, Long>{
	
	@Query("SELECT p FROM Person p ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Person> getAllPersonsOrderedByName();
	
	@Query("SELECT p FROM Person p WHERE p.name LIKE %:keyword%")
	public List<Person> getAllPersonsContainingKeyword(@Param("keyword") String keyword);

	@Query("SELECT p FROM Person p WHERE p.address LIKE %:specificString% ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Person> getPersonsByAddressContainingSpecificString(@Param("specificString") String specificString);

	@Query("SELECT p FROM Person p WHERE p.income > :numberOne AND p.income <= :numberTwo ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Person> getPersonsByIncomeRange(@Param("numberOne") long numberOne, @Param("numberTwo") long numberTwo);

	@Query("SELECT p FROM Person p WHERE p.address LIKE %:specificString% AND p.income > :numberOne AND p.income <= :numberTwo ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Person> getPersonsByAddressContainingSpecificStringAndIncomeRange(@Param("specificString") String specificString, @Param("numberOne") long numberOne, @Param("numberTwo") long numberTwo);

}
