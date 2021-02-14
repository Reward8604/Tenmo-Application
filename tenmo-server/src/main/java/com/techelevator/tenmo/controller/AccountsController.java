package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Accounts;

@RestController
@RequestMapping(path="accounts/")
public class AccountsController
{
	@Autowired
	AccountsDAO dao;
	@Autowired
	UserDAO userdao;
	

	@PreAuthorize("isAuthenticated()")
	@GetMapping("balance/{userId}")
	public BigDecimal getBalance(@PathVariable int userId)
	{

		BigDecimal balance = dao.getBalance(userId);
				
		return balance;
	}
	
}
	