package com.it.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.it.entity.Merchant;
import com.it.repository.MerchantRepository;

@Service
public class CustomerServiceImpl {

	
	@Autowired
	MerchantRepository merchantRepo;

	public double calculateInterestAmount(double loanAmount, double rate) {
		double result = 0;
		result =loanAmount * rate / 100;
		return result;
	}

	public String encryptString(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			// return the HashText
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public Merchant loginProcess(String username, String encodedPassword) {
		Merchant response = null;
		Merchant merchant = merchantRepo.validateUsernameAndPassword(username, encodedPassword);

		if (merchant != null) {
			LocalDateTime loginTime = LocalDateTime.now();
			merchant.setLogin(loginTime);
			merchant.setActive("Y");
			merchant.setLogout(null);
			response = merchantRepo.save(merchant);
		}	
		return response;
	}

	

}
