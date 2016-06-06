package com.codernb.model;

public class PlayedRequest {
	
	public final String videoId;
	public final long time;
	public final long timePlayed;
	
	public PlayedRequest(String videoId, long time, long timePlayed) {
		this.videoId = videoId;
		this.time = time;
		this.timePlayed = timePlayed;
	}

}
