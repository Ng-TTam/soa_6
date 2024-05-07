package com.example.tax.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tax.Entity.Account;
@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{
	
	@Query("SELECT COUNT(a) > 0 FROM Account a WHERE a.username = :username AND a.password = :password")
	public boolean checkExistsAccount(@Param("username") String username, @Param("password") String password);
	
	public Account getAccountByUsernameAndPassword(String username,String password);
}
