package com.codernb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codernb.access.VideoAccess;
import com.codernb.model.Video;

@Controller
@RequestMapping("/video")
public class VideoController {
	
	@RequestMapping
	public @ResponseBody List<Video> getVideos() throws SQLException, ClassNotFoundException {
		System.out.println("getall");
		return VideoAccess.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video video) {
		VideoAccess.add(video);
		return video;
	}
	
	@RequestMapping("/next")
	public @ResponseBody Video getNext() {
		return VideoAccess.getNext();
	}
	
	@RequestMapping("/requested")
	public @ResponseBody List<Video> getRequested() {
		return VideoAccess.getRequested();
	}
	
}
