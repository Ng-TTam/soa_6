package com.example.tax.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.tax.Entity.Debt;
import com.example.tax.Entity.Person;

public interface DebtRepository extends CrudRepository<Debt, Long>{
	
	@Query("SELECT COALESCE(SUM(d.taxMoney), 0) FROM Debt d WHERE d.taxPeriod = :period AND d.taxYear = :year")
	long sumTaxMoney(@Param("period") String period, @Param("year") Integer year);
	
	@Query("SELECT COALESCE(SUM(d.taxMoney), 0) FROM Debt d WHERE d.taxMoney > :income1 AND d.taxMoney <= :income2 AND d.taxYear = :year")
	long sumDebtByIncome(@Param("income1") long income1,@Param("income2") long income2, @Param("year") int year);
	
	@Query("SELECT p FROM Debt p WHERE p.address LIKE %:specificString% AND p.taxYear =:year ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Debt> allDeabtByAddressAndYear(@Param("specificString") String specificString,@Param("year") int year);
	
	@Query("SELECT p FROM Debt p WHERE p.address LIKE %:specificString% AND p.taxYear =:year AND p.taxPeriod =:month ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Debt> deabtByAddressAndYearAndTypeMonth(@Param("specificString") String specificString,@Param("year") int year,@Param("month") String month);
	
	@Query("SELECT p FROM Debt p WHERE p.address LIKE %:specificString% AND p.taxYear =:year AND p.taxPeriod =:quarter ORDER BY SUBSTRING_INDEX(p.name, ' ', -1) ASC")
	public List<Debt> deabtByAddressAndYearAndTypeQuarter(@Param("specificString") String specificString,@Param("year") int year,@Param("quarter") String quarter);
}
