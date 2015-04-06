package com.spring.hibernate.rest;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lms.spring.model.Employee;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class AccountController {

	private AccountService accountService;

	@Autowired(required = true)
	@Qualifier(value = "accountServiceBean")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(AccountController.class);


	@RequestMapping(value = AccountRestURIConstants.DUMMY_ACC, method = RequestMethod.GET)
	public @ResponseBody
	Account getDummyAccount() {
		logger.info("Start getDummyAccount");
		Account account = new Account("arka dutta", "123456");
		Set<Bookmark> bookmarks = account.getBookmarks();
		Bookmark bookMark = new Bookmark(account, "http://journaldev.com",
				"Java Tutorials");
		bookmarks.add(bookMark);
		return account;
	}

	@RequestMapping(value = AccountRestURIConstants.GET_ACCOUNT, method = RequestMethod.GET)
	public @ResponseBody
	Account getEmployee(@PathVariable("id") int accId) {
		logger.info("Start getAccount. ID=" + accId);

		return accountService.getById(accId);
	}

	@RequestMapping(value = AccountRestURIConstants.GET_ALL_ACCOUNT, method = RequestMethod.GET)
	public @ResponseBody
	List<Account> getAllAccounts() {
		logger.info("Start getAllAccounts.");
		List<Account> accounts = accountService.read();
		
		return accounts ;
	}

	@RequestMapping(value = AccountRestURIConstants.CREATE_ACC, method = RequestMethod.POST)
	public @ResponseBody
	Boolean createAccount(@RequestBody Account acc) {
		logger.info("Start createAccount.");
		accountService.save(acc);
		
		return true;
	}
	
	@RequestMapping(value = AccountRestURIConstants.UPDATE_ACCOUNT, method = RequestMethod.POST)
	public @ResponseBody
	Boolean updateAccount(@RequestBody Account acc) {
		logger.info("Start updateAccount.");
		try{
		accountService.update(acc);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return false;
		}
		
		return true;
	}

	/*@RequestMapping(value = AccountRestURIConstants.DELETE_EMP, method = RequestMethod.PUT)
	public @ResponseBody
	Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		Employee emp = empData.get(empId);
		empData.remove(empId);
		return emp;
	}*/

}
