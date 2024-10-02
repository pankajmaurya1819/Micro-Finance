package com.it.repository;

import java.time.LocalDateTime;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.it.entity.Merchant;

import jakarta.transaction.Transactional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

	@Query(value = "select * from Merchant where username=? and password=?", nativeQuery = true)
	public Merchant validateUsernameAndPassword(String username, String encodedPassword);

	@Transactional
	@Modifying
	@Query(value = "update merchant set active='N', logout=? where username=?", nativeQuery = true)
	public int updateMerchantLogout(LocalDateTime logout, String username);

}
