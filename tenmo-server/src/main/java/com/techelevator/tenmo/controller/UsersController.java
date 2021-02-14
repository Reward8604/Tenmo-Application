package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@RestController
@RequestMapping(path="/users")
public class UsersController
{
	@Autowired
	UserDAO userdao;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping()
	public List<User> getUsers()
	{
		return userdao.findAll();
	}
}
