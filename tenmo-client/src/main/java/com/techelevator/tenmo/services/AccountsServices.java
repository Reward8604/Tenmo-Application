package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.techelevator.tenmo.models.AuthenticatedUser;

public class AccountsServices extends ApiServiceBase
{
	private AuthenticatedUser currentUser;
	public AccountsServices(String url, AuthenticatedUser currentUser)
	{
		
		super(url);
		this.BASE_URL = url;
		this.currentUser = currentUser;
	}
	
	public BigDecimal getBalance()
	{
		String url = BASE_URL;
		BigDecimal balance = new BigDecimal(0);
	
    	balance = restTemplate.exchange(url + "accounts/balance/" + currentUser.getUser().getId(), HttpMethod.GET, makeEntity(), BigDecimal.class).getBody();
		
		return balance;
	}
	
	
	private HttpEntity makeEntity()
	{
		HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
	}
	
}
