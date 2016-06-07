package com.codernb.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codernb.access.PlayedRequestsAccess;
import com.codernb.access.RequestAccess;
import com.codernb.access.VideoAccess;
import com.codernb.exception.NoEntryException;
import com.codernb.model.Request;
import com.codernb.model.Video;

@Controller
@RequestMapping("/video")
public class VideoController {
	
	private static Video currentVideo;
	
	@RequestMapping
	public @ResponseBody List<Video> getVideos() throws SQLException, ClassNotFoundException {
		return VideoAccess.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video video) {
		VideoAccess.add(video);
		RequestAccess.add(video);
		return video;
	}
	
	@RequestMapping("/next")
	public @ResponseBody Video getNext() {
		try {
			currentVideo = VideoAccess.getNext();
			Request request = RequestAccess.delete(currentVideo.id);
			PlayedRequestsAccess.add(request);
			return currentVideo;
		} catch (NoEntryException e) {
			currentVideo = VideoAccess.getRandom();
			DefaultController.update();
			return currentVideo;
		}
	}
	
	@RequestMapping("/requested")
	public @ResponseBody List<Video> getRequested() {
		return VideoAccess.getRequested();
	}
	
	@RequestMapping("/current")
	public @ResponseBody Video getCurrent() {
		return currentVideo;
	}
	
}
