package com.it.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.it.entity.Transaction;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query(nativeQuery = true, value = "select * from Transaction where account_number = ? order by trans_date desc")
	public List<Transaction> findByAccountNumber(@Param("account_number") String account_number);

	@Query(nativeQuery = true, value = "select count(*) from Transaction where loan_id = ?")
	public int getTransactionCountByLoanId(@Param("loan_id") int loan_id);

	@Query(nativeQuery = true, value = "select * from Transaction where loan_id = ?")
	public Optional<Transaction> findByLoanId(@Param("loan_id") int loan_id);

	@Query(nativeQuery = true, value = "select SUM(credit) from Transaction where debit <= 0")
	public double getTotalProfit();

	@Query(nativeQuery = true, value = "select * from Transaction order by id asc")
	public List<Transaction> getAllTransaction();
	
	@Query(nativeQuery = true, value = "select * from Transaction order by trans_date desc")
	public List<Transaction> getAllTransDetails();

	@Query(nativeQuery = true, value = "select count(*) from Transaction where debit <= 0")
	public long getTransactionCount();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from Transaction where loan_id = ?")
	public int deleteByLoanId(@Param("loan_id") int loan_id);
}
