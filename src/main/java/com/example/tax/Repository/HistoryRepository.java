package com.example.tax.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tax.Entity.History;
import com.example.tax.Entity.Person;
@Repository
public interface HistoryRepository extends CrudRepository<History, Long>{
	
	@Query("SELECT h FROM History h WHERE h.person.id = :personId Order by h.date DESC")
	List<History> findByPersonId(@Param("personId") Long personId);
	
	@Query("SELECT COALESCE(SUM(h.taxMoney), 0) FROM History h WHERE MONTH(h.date) = :month AND YEAR(h.date) = :year")
	Long getTotalTaxMoneyByMonthAndYear(@Param("month") int month, @Param("year") int year);

	@Query("SELECT COALESCE(SUM(h.taxMoney), 0) FROM History h WHERE MONTH(h.date) <= :quarter AND YEAR(h.date) = :year")
	Long getTotalTaxMoneyByQuarterAndYear(@Param("quarter") int quarter, @Param("year") int year);
	
	@Query("SELECT COALESCE(SUM(h.taxMoney), 0) FROM History h WHERE YEAR(h.date) =:year AND h.taxMoney > :income1 AND h.taxMoney <= :income2")
	Long totalTaxMoneyByIncome(@Param("income1") long income1,@Param("income2") long income2, @Param("year") int year);
}
