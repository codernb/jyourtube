package com.codernb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.codernb.access.RequestAccess;
import com.codernb.model.Request;

@Controller
@RequestMapping("/request")
public class RequestController {

	@RequestMapping
	public @ResponseBody List<Request> getAll() {
		return RequestAccess.getAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{videoId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String videoId) {
		RequestAccess.delete(videoId);
	}

	@RequestMapping(value = "/all", method = RequestMethod.DELETE)
	public void clear() {
		RequestAccess.clear();
	}

}
