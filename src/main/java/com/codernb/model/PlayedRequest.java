package com.codernb.model;

import java.util.Date;

public class PlayedRequest {
	
	public String videoId;
	public long time;
	public long timePlayed;
	
	public PlayedRequest() {
	}
	
	public PlayedRequest(String videoId, long time, long timePlayed) {
		this.videoId = videoId;
		this.time = time;
		this.timePlayed = timePlayed;
	}
	
	public PlayedRequest(String videoId, long time) {
		this.videoId = videoId;
		this.time = time;
		this.timePlayed = new Date().getTime();
	}

}
