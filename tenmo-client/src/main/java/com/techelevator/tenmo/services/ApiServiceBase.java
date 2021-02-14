package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;

public abstract class ApiServiceBase
{
	protected String BASE_URL;
	protected static final RestTemplate restTemplate = new RestTemplate();
	protected static AuthenticatedUser user = null;
	
	public static void setUser(AuthenticatedUser user)
	{
		ApiServiceBase.user = user;
	}
	
	public ApiServiceBase(String url)
	{
		this.BASE_URL = url;
	}

}