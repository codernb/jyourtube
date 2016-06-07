package com.codernb.model;

import java.util.Date;

public class Request {

	public final String videoId;
	public final long time;

	public Request(String videoId) {
		this.videoId = videoId;
		this.time = new Date().getTime();
	}

	public Request(String videoId, long time) {
		this.videoId = videoId;
		this.time = time;
	}

}
