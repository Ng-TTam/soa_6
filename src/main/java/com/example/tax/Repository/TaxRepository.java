package com.example.tax.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tax.Entity.Tax;

public interface TaxRepository extends JpaRepository<Tax, Integer>{

}
