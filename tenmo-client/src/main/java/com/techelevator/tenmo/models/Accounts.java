package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Accounts
{
	private int accountId;
	private int userId;
	private BigDecimal balance;
	
	
	public int getAccountId()
	{
		return accountId;
	}
	public int getUserId()
	{
		return userId;
	}
	public BigDecimal getBalance()
	{
		return balance;
	}
	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}
	
	

}
