package com.it.repository;

import java.time.LocalDateTime;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.it.entity.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query(nativeQuery = true, value="Select * from customer order by insert_date desc")
	public List<Customer> findAllByDesc();
	
	@Query(nativeQuery = true, value="Select count(*) from customer")
	public long findCustomersCount();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value="UPDATE Customer set amount = ?, due_date = ?, update_date = ?, interest_amount = ? where account_number = ?")
	public int updateLoanDetails(@Param("amount") double amount, @Param("due_date") LocalDateTime due_date, @Param("update_date") LocalDateTime update_date, @Param("interest_amount") double interest_amount, @Param("account_number")String account_number);

	@Query(nativeQuery = true, value="Select * from customer order by insert_date asc")
	public List<Customer> findAllCustomersLoan();

	@Query(nativeQuery = true, value="Select SUM(amount) from customer")
	public double getTotalLoanAmount();

	@Query(nativeQuery = true, value="Select MAX(account_number) from customer")
	public String getMaxAccountNumber();
}
