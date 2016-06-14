package com.codernb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codernb.access.UserAccess;
import com.codernb.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping
	public List<User> getAll() {
		return UserAccess.getAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody User add(@RequestBody User user) {
		UserAccess.add(user);
		return user;
	}

}
