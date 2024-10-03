package com.it.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.it.entity.Customer;
import com.it.entity.Merchant;
import com.it.entity.SigninRequestBean;
import com.it.entity.Transaction;
import com.it.repository.CustomerRepository;
import com.it.repository.MerchantRepository;
import com.it.repository.TransactionRepository;
import com.it.service.CustomerServiceImpl;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {

// only check the 
	Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	MerchantRepository merchantRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CustomerServiceImpl service;

	@Autowired
	TransactionRepository transRepo;

	@Autowired(required = true)
	JavaMailSender mailSender;

	@Value("${microfinance.mail.to}")
	private String to;

	@Value("${microfinance.targetfolder}")
	private String targetFolder;

	@Value("${microfinance.db.username}")
	private String username;

	@Value("${microfinance.db.password}")
	private String password;

	@Value("${microfinance.db.databaseName}")
	private String databaseName;

	public List<Customer> todayPaymentList  = null;

	HttpSession session = null;

	@GetMapping("/")
	public String show() {
		
		logger.info("login page");
		return "signin";
	}
	
	@GetMapping("/payment")
	public String payment() {
		return " ";
	}
	
	@GetMapping("/new-loan")
	public String formData(Model model, HttpServletRequest request) {
		try {
			session = request.getSession(true);
			if (session.getAttribute("username") != null) {
				model.addAttribute("todayPaymentList", todayPaymentList);
				return "newLoan";
			} else {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "signin";
	}

	@GetMapping("/index")
	public String showIndex(Model model, HttpServletRequest request) {
		double monthProfit = 0.0;
		double totalProfit = 0.0;
		double totalLoanAmount = 0.0;
		double interestAmt = 0.0;
		List<Transaction> transList = null;
		List<Customer> custList = null;

		try {
			session = request.getSession(true);
			if (session.getAttribute("username") == null) {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
			todayPaymentList = new ArrayList<Customer>();
			LocalDateTime currentDate = LocalDateTime.now();
			Month month = currentDate.getMonth();
			String monthName = month.getDisplayName(TextStyle.FULL, Locale.UK);
			long custCount = customerRepo.findCustomersCount();
			if (custCount > 0) {
				totalLoanAmount = customerRepo.getTotalLoanAmount();
				transList = transRepo.getAllTransaction();
				if (transList.size() > 0) {
					for (Transaction tran : transList) {
						if (tran.getTransDate().getMonth().getDisplayName(TextStyle.FULL, Locale.UK).equals(monthName)
								&& tran.getDebit() <= 0) {
							if (tran.getInterestAmount() < tran.getCredit()) {
								monthProfit = monthProfit + interestAmt;
							} else {
								monthProfit = monthProfit + tran.getCredit();
							}
						}
						if (tran.getInterestAmount() < tran.getCredit()) {
							totalProfit = totalProfit + interestAmt;
						} else {
							totalProfit = totalProfit + tran.getCredit();
						}

						interestAmt = tran.getInterestAmount();
					}
				}
				custList = customerRepo.findAll();
				for (Customer customer : custList) {
					long dayCount = ChronoUnit.DAYS.between(currentDate, customer.getDueDate());
					if (dayCount == 0) {
						todayPaymentList.add(customer);
					}
				}
			}
			model.addAttribute("custCount", custCount);
			model.addAttribute("totalLoanAmount", totalLoanAmount);
			model.addAttribute("totalProfit", totalProfit);
			model.addAttribute("monthName", monthName);
			model.addAttribute("monthProfit", monthProfit);
			model.addAttribute("todayPaymentList", todayPaymentList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signin-process")
	public String signInProcess(@ModelAttribute SigninRequestBean signin, Model model, HttpServletRequest request) {
		if (signin != null) {
			logger.info("Login process started");
			String username = signin.getUsername();
			String encodedPassword = service.encryptString(signin.getPassword());
			logger.debug("password :: " + encodedPassword);

			Merchant response = service.loginProcess(username, encodedPassword);

			if (response != null) {
				System.out.println("+++++++ Successfully login into system +++++++");
				logger.info("login in system");
				HttpSession session = request.getSession(true);
				session.setAttribute("username", response.getUsername());
				model.addAttribute("merchant", response);
				return "redirect:/index";
			}

		} else {
			model.addAttribute("msg", "Incorrect username or password!");
		}
		return "signin";
	}

	@PostMapping("/customer-loan")
	public String customerLon(@ModelAttribute Customer customer, Model model) {
		String account_Number = null;
		Transaction trans = new Transaction();
		double interestAmount = service.calculateInterestAmount(customer.getAmount(), customer.getPercentage());
		String maxAccount = customerRepo.getMaxAccountNumber();
		if (maxAccount != null && !maxAccount.isEmpty()) {
			account_Number = String.valueOf(Long.parseLong(maxAccount) + 1);
		} else {
			account_Number = "80000010011";
		}
		LocalDateTime insertDate = LocalDateTime.now();
		customer.setInsertDate(insertDate);
		customer.setAccount_number(account_Number);
		customer.setDueDate(customer.getInsertDate().plusMonths(1));
		customer.setInterestAmount(interestAmount);
		Customer response = customerRepo.save(customer);
		if (response != null) {
			trans.setLoanId(response.getId());
			trans.setAccountNumber(account_Number);
			trans.setInterestAmount(interestAmount);
			trans.setLoanAmount(customer.getAmount());
			trans.setInterestRate(customer.getPercentage());
			trans.setDueDate(customer.getDueDate());
			trans.setTransDate(LocalDateTime.now());
			trans.setDebit(customer.getAmount());
			transRepo.save(trans);
			model.addAttribute("msg", "Loan Account created successfully");
			model.addAttribute("customer", response);
		} else {
			model.addAttribute("msg", "Technical issue occured");
		}
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "newLoan";
	}

	@GetMapping("/view-loans")
	public String showData(Model model, HttpServletRequest request) {

		try {
			session = request.getSession(true);
			if (session.getAttribute("username") == null) {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
			List<Customer> result = customerRepo.findAllByDesc();
			model.addAttribute("customerList", result);
			model.addAttribute("todayPaymentList", todayPaymentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewloan";
	}

	@GetMapping("/getCustomer")
	public String getCustomer(Model model, HttpServletRequest request) {
		try {
			session = request.getSession(true);
			if (session.getAttribute("username") == null) {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
			int id = Integer.parseInt(request.getParameter("id"));
			Customer result = null;
			Optional<Customer> ctd = customerRepo.findById(id);
			if (ctd.isPresent()) {
				result = (Customer) ctd.get();
			}
			model.addAttribute("customer", result);
			model.addAttribute("todayPaymentList", todayPaymentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "editCustomer";
	}

	@PostMapping("/updateCustomerData")
	public String updateCustomerData(@ModelAttribute Customer customer, Model model) {
		Customer response = null;
		Transaction tranResponse = null;
		LocalDateTime updateDate = null;
		Customer request = new Customer();
		int count = 0;

		count = transRepo.getTransactionCountByLoanId(customer.getId());
		if (customer != null && count <= 1) {
			Optional<Customer> cust = customerRepo.findById(customer.getId());
			if (cust.isPresent()) {
				double interestAmount = service.calculateInterestAmount(customer.getAmount(), customer.getPercentage());
				request = (Customer) cust.get();
				updateDate = LocalDateTime.now();
				request.setUpdateDate(updateDate);
				request.setName(customer.getName());
				request.setEmail(customer.getEmail());
				request.setAdhar_number(customer.getAdhar_number());
				request.setMobile(customer.getMobile());
				request.setAmount(customer.getAmount());
				request.setPercentage(customer.getPercentage());
				request.setAddress(customer.getAddress());
				request.setReference(customer.getReference());
				request.setInterestAmount(interestAmount);
				response = customerRepo.save(request);
				if (response != null) {
					Optional<Transaction> trans = transRepo.findByLoanId(customer.getId());
					if (trans.isPresent()) {
						Transaction transDetails = (Transaction) trans.get();
						transDetails.setDebit(customer.getAmount());
						transDetails.setInterestAmount(interestAmount);
						transDetails.setInterestRate(customer.getPercentage());
						transDetails.setLoanAmount(customer.getAmount());
						transDetails.setTransDate(updateDate);
						tranResponse = transRepo.save(transDetails);
					}
					if (tranResponse == null)
						model.addAttribute("msg", "Failed to update transaction details ");
					else
						model.addAttribute("msg", "Loan Details updated successfully ");
				} else {
					model.addAttribute("msg", "Technical issue occured");
				}
			}
		} else if (count > 1) {
			model.addAttribute("msg", "Can't update the loan details after any transaction ");
		} else {
			model.addAttribute("msg", "No data found for update ");
		}
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "editCustomer";
	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(HttpServletRequest request, Model model) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			int count = 0;
			count = transRepo.getTransactionCountByLoanId(id);
			if (count <= 1) {
				customerRepo.deleteById(id);
				transRepo.deleteByLoanId(id);
			} else {
				model.addAttribute("msg", " Can't delete the loan details after any transaction ");
			}
			List<Customer> result = customerRepo.findAllByDesc();
			model.addAttribute("cab", result);
			model.addAttribute("todayPaymentList", todayPaymentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewloan";

	}

	@GetMapping("/show-loan-details")
	public String showLoanDetails(Model model, HttpServletRequest request) {
		session = request.getSession(true);
		if (session.getAttribute("username") == null) {
			model.addAttribute("msg", "Please login");
			return "signin";
		}
		int id = Integer.parseInt(request.getParameter("id"));
		Customer customer = null;
		LocalDateTime dueDate = null;
		Optional<Customer> cust = customerRepo.findById(id);
		if (cust.isPresent()) {
			customer = (Customer) cust.get();
			dueDate = customer.getDueDate();
			customer.setDueDate(dueDate);
		}
		model.addAttribute("customer", customer);
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "loanDetails";
	}

	@PostMapping("/payment")
	public String showPayment(@ModelAttribute Customer customer, Model model, HttpServletRequest request) {
		Customer response = new Customer();
		session = request.getSession(true);
		if (session.getAttribute("username") == null) {
			model.addAttribute("msg", "Please login");
			return "signin";
		}
		Optional<Customer> cust = customerRepo.findById(customer.getId());
		if (cust.isPresent()) {
			response = (Customer) cust.get();
			model.addAttribute("customer", response);
		} else {
			model.addAttribute("customer", null);
		}

		model.addAttribute("todayPaymentList", todayPaymentList);
		return "payment";
	}

	@PostMapping("/payment-process")
	public String transaction(@ModelAttribute Transaction transaction, Model model) {
		try {
			Transaction response = null;
			double loanAmount = 0.0;
			double interestAmount = 0.0;
			Customer customer = null;
			System.out.println(transaction.getCredit());
			Optional<Customer> cust = customerRepo.findById(transaction.getLoanId());
			if (cust.isPresent()) {
				customer = (Customer) cust.get();

			}
			if (transaction != null && transaction.getCredit() > 0
					&& (transaction.getLoanAmount() + transaction.getInterestAmount()) >= transaction.getCredit()) {
				if (transaction.getCredit() >= transaction.getInterestAmount()) {
					loanAmount = transaction.getLoanAmount()
							- (transaction.getCredit() - transaction.getInterestAmount());
					transaction.setLoanAmount(loanAmount);
					interestAmount = service.calculateInterestAmount(transaction.getLoanAmount(),
							transaction.getInterestRate());
					transaction.setInterestAmount(interestAmount);
				} else {
					interestAmount = transaction.getInterestAmount() + transaction.getInterestAmount()
							- transaction.getCredit();
					transaction.setInterestAmount(interestAmount);
				}
				transaction.setTransDate(LocalDateTime.now());
				LocalDateTime nextDueDate = customer.getDueDate().plusMonths(1);
				transaction.setDueDate(nextDueDate);

				response = transRepo.save(transaction);
				if (response != null) {
					int count = customerRepo.updateLoanDetails(transaction.getLoanAmount(), transaction.getDueDate(),
							transaction.getTransDate(), transaction.getInterestAmount(),
							transaction.getAccountNumber());
					System.out.println("count :: " + count);
					model.addAttribute("transaction", response);
				} else {
					model.addAttribute("msg", "Transaction failed");
					return "payment";
				}
			} else {
				model.addAttribute("customer", customer);
				model.addAttribute("msg", "Please enter valid amount");
				return "payment";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "paymentReceipt";
	}

	@GetMapping("/upcoming-payment")
	public String upcomingPayment(Model model, HttpServletRequest request) {
		List<Customer> upcomingList = null;
		LocalDate currentDate = java.time.LocalDate.now();
		try {
			session = request.getSession(true);
			if (session.getAttribute("username") == null) {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
			upcomingList = new ArrayList<>();
			List<Customer> custList = customerRepo.findAllCustomersLoan();
			for (Customer customer : custList) {
				long dayCount = ChronoUnit.DAYS.between(currentDate, customer.getDueDate());
				System.out.println(dayCount);
				if (dayCount <= 7 && dayCount >= 0) {
					upcomingList.add(customer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (upcomingList.size() <= 0)
			model.addAttribute("msg", "No upcoming payment found within 7 days.");
		else
			model.addAttribute("customerList", upcomingList);

		model.addAttribute("todayPaymentList", todayPaymentList);
		return "upcomingPayment";
	}

	@GetMapping("/missed-payment")
	public String missedPayment(Model model, HttpServletRequest request) {
		List<Customer> missingList = null;
		long dayCount = 0;
		LocalDate currentDate = java.time.LocalDate.now();
		try {
			session = request.getSession(true);
			if (session.getAttribute("username") == null) {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
			missingList = new ArrayList<>();
			List<Customer> custList = customerRepo.findAllCustomersLoan();
			for (Customer customer : custList) {
				dayCount = ChronoUnit.DAYS.between(currentDate, customer.getDueDate());
				if (dayCount < 0) {
					missingList.add(customer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (missingList.size() <= 0) {
			model.addAttribute("msg", "No missing payment found");
		} else {
			model.addAttribute("days", dayCount * -1);
			model.addAttribute("customerList", missingList);
		}
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "missingPayment";
	}

	@GetMapping("/success-payment")
	public String successPayment(Model model, HttpServletRequest request) {
		session = request.getSession(true);
		if (session.getAttribute("username") == null) {
			model.addAttribute("msg", "Please login");
			return "signin";
		}
		LocalDate currentDate = java.time.LocalDate.now();
		Month month = currentDate.getMonth();
		String monthName = month.getDisplayName(TextStyle.FULL, Locale.UK);
		List<Transaction> successList = new ArrayList<Transaction>();
		List<Transaction> transList = transRepo.getAllTransDetails();
		if (transList.size() > 0) {
			for (Transaction tran : transList) {
				if (tran.getTransDate().getMonth().getDisplayName(TextStyle.FULL, Locale.UK).equals(monthName)) {
					successList.add(tran);
				}
			}
		}
		model.addAttribute("monthName", monthName);
		model.addAttribute("transList", successList);
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "successPayment";
	}

	@GetMapping("/view-transaction")
	public String viewTransaction(Model model, HttpServletRequest request) {

		session = request.getSession(true);
		if (session.getAttribute("username") == null) {
			model.addAttribute("msg", "Please login");
			return "signin";
		}
		model.addAttribute("todayPaymentList", todayPaymentList);
		return "viewTransactions";
	}

	@PostMapping("/search-transactions")
	public String searchTransaction(Model model, @ModelAttribute Transaction transaction, HttpServletRequest request) {
		try {
			session = request.getSession(true);
			if (session.getAttribute("username") == null) {
				model.addAttribute("msg", "Please login");
				return "signin";
			}
			String accountNumber = transaction.getAccountNumber();
			if (accountNumber != null && !accountNumber.isEmpty()) {
				List<Transaction> tranList = transRepo.findByAccountNumber(accountNumber);
				if (tranList.size() > 0) {
					model.addAttribute("accountNumber", accountNumber);
					model.addAttribute("transList", tranList);
				} else {
					model.addAttribute("msg", "Transaction not found for Account Number : " + accountNumber);
				}
			} else {
				model.addAttribute("msg", "Please Enter Account Number");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("todayPaymentList", todayPaymentList);
		return "viewTransactions";
	}

	@GetMapping("/logout-process")
	public String logout(HttpServletRequest request) {
		System.out.println("----------Logout process----------");
		if (session != null) {
			String username = String.valueOf(session.getAttribute("username"));
			session.invalidate();
			int count = merchantRepo.updateMerchantLogout(LocalDateTime.now(), username);
			if (count == 1)
				System.out.println("Merchant Updated");
			else
				System.out.println("Issue occured");

		}
		return "signin";
	}

	@GetMapping("/db-backup")
	public String backup(Model model) throws IOException, InterruptedException {
		try {
			String targetOutput = targetFolder + new java.sql.Timestamp(System.currentTimeMillis()).getTime() + ".sql";
			String command = String.format(
					"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe -u%s -p%s --add-drop-table --databases %s -r %s",
					username, password, databaseName, targetOutput);
			Runtime.getRuntime().exec(command);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject("Database Backup");
			helper.setTo(to);

			helper.setText("<b>Hi Sir</b>,<br><i> Please find your database backup in attachment</i>", true);

			FileSystemResource file = new FileSystemResource(new File(targetOutput));
			helper.addAttachment(file.getFilename(), file);
			mailSender.send(message);
			model.addAttribute("msg", "Backup Completed");
		} catch (Exception e) {
			model.addAttribute("msg", "Backup Failed");
			e.printStackTrace();
		}
		return "successpage";
	}
}
