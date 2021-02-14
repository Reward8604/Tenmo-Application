package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

public interface TransfersDAO 
{
	Transfers sendBucks(int accountFrom, int accountTo, BigDecimal transferAmount);
	
	List<User> listUser();
	
	List<Transfers> listTransfers(int accountId);
	
	
}
