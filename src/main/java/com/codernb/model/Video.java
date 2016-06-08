package com.codernb.model;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringEscapeUtils;

public class Video {

	public String id;
	public String title;
	public String thumbnail;
	public String author;
	public List<PlayedRequest> playedRequests;
	public int timesPlayed;
	public long firstPlayed;
	public long lastPlayed;
	
	public Video() {
	}

	public Video(String id, String title, String thumbnail, String author, List<PlayedRequest> playedRequests) {
		this.id = id;
		this.title = title;
		this.thumbnail = thumbnail;
		this.author = author;
		this.playedRequests = playedRequests;
		timesPlayed = getTimesPlayed();
		firstPlayed = getFirstPlayed();
		lastPlayed = getLastPlayed();
	}
	
	public int getTimesPlayed() {
		return playedRequests == null ? 0 : playedRequests.size();
	}
	
	public long getFirstPlayed() {
		Optional<PlayedRequest> optional = playedRequests.stream().reduce((a, b) -> a.timePlayed < b.timePlayed ? a : b);
		if (optional.isPresent())
			return optional.get().timePlayed;
		else
			return 0;
	}
	
	public long getLastPlayed() {
		Optional<PlayedRequest> optional = playedRequests.stream().reduce((a, b) -> a.timePlayed >= b.timePlayed ? a : b);
		if (optional.isPresent())
			return optional.get().timePlayed;
		else
			return 0;
	}

	public String getIdEscaped() {
		return StringEscapeUtils.escapeSql(id);
	}

	public String getTitleEscaped() {
		return StringEscapeUtils.escapeSql(title);
	}

	public String getThumbnailEscaped() {
		return StringEscapeUtils.escapeSql(thumbnail);
	}

	public String getAuthorEscaped() {
		return StringEscapeUtils.escapeSql(author);
	}

}
