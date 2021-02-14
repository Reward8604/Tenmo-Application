package com.techelevator.tenmo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;

public class TransferServices extends ApiServiceBase {
private AuthenticatedUser currentUser;
    public TransferServices(String url, AuthenticatedUser currentUser)
    {
        super(url);
        this.BASE_URL = url;
        this.currentUser = currentUser;
    }

    public Transfers sendBucks(Transfers transfers)
    {
        String url = BASE_URL;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<Transfers>(transfers, headers);
        
        
        Transfers newTransfers = restTemplate.postForObject(url + "transfers", entity, Transfers.class);



        return newTransfers;
    }

    public List<User> listUser()
    {
    	List<User> users;
        String url = BASE_URL;

        User[] usersArray = restTemplate.exchange(url + "users",HttpMethod.GET, makeEntity(), User[].class).getBody();
        users = Arrays.asList(usersArray);
        return users;
    }
    
    public List<Transfers> listTransfers(Transfers transfers)
    {
    	List<Transfers> transfersList;
    	String url = BASE_URL;
     	HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(user.getToken());
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity entity = new HttpEntity<Transfers>(transfers, headers);
       
    	
    	Transfers[] transfersArray = restTemplate.exchange(url + "transfers", HttpMethod.GET, entity, Transfers[].class).getBody();
    	transfersList = Arrays.asList(transfersArray);
    	return transfersList;
    }
    
    public List<Transfers> byId(int accountId)
    {
    	
    	Transfers[] byId = restTemplate.exchange(BASE_URL + "transfers/" + accountId, HttpMethod.GET, makeEntity(), Transfers[].class).getBody();
    	
    	return Arrays.asList(byId);
    }
    
    private HttpEntity makeEntity()
	{
		HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
	}
}