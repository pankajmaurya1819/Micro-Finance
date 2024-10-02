package com.it.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;



@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private int id;
	private int loanId;
	private String accountNumber;
	private double loanAmount;
	private double interestRate;
	private double interestAmount;
	private LocalDateTime dueDate;
	private LocalDateTime transDate;
	private double debit;
	private double credit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public LocalDateTime getTransDate() {
		return transDate;
	}
	public void setTransDate(LocalDateTime transDate) {
		this.transDate = transDate;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", loanId=" + loanId + ", accountNumber=" + accountNumber + ", loanAmount="
				+ loanAmount + ", interestRate=" + interestRate + ", interestAmount=" + interestAmount + ", dueDate="
				+ dueDate + ", transDate=" + transDate + ", debit=" + debit + ", credit=" + credit + "]";
	}
}
