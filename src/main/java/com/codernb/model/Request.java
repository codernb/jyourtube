package com.codernb.model;

public class Request {

	public final String videoId;
	public final long time;

	public Request(String videoId, long time) {
		this.videoId = videoId;
		this.time = time;
	}

	public String getVideoId() {
		return videoId;
	}

	public long getTime() {
		return time;
	}

}
