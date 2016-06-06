package com.codernb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codernb.access.RequestAccess;

@Controller
public class DefaultController {
	
	private int volume = 100;

	@RequestMapping("/")
	public String home() {
		return index();
	}

	@RequestMapping("/index")
	public String index() {
		return "indexpage.html";
	}

	@RequestMapping("/player")
	public String player() {
		return "playerpage.html";
	}

	@RequestMapping("/version")
	public @ResponseBody int getVersion() {
		return RequestAccess.getVersion();
	}

	@RequestMapping("/volume")
	public @ResponseBody int getVolume() {
		return volume;
	}

	@RequestMapping(value = "/volume/up", method = RequestMethod.POST)
	public @ResponseBody int increaseVolume() {
		volume = Math.min(volume + 5, 100);
		return volume;
	}

	@RequestMapping(value = "/volume/down", method = RequestMethod.POST)
	public @ResponseBody int decreaseVolume() {
		volume = Math.max(volume - 5, 0);
		return volume;
	}

}
