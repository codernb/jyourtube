package com.codernb.model;

import org.apache.commons.lang.StringEscapeUtils;

public class Video {

	public String id;
	public String title;
	public String description;
	public String thumbnail;
	public String author;
	
	public Video() {
	}

	public Video(String id, String title, String description, String thumbnail, String author) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.thumbnail = thumbnail;
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public String getIdEscaped() {
		return StringEscapeUtils.escapeSql(id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getITitleEscaped() {
		return StringEscapeUtils.escapeSql(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleEscaped() {
		return StringEscapeUtils.escapeSql(title);
	}

	public String getDescription() {
		return description;
	}

	public String getDescriptionEscaped() {
		return StringEscapeUtils.escapeSql(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getThumbnailEscaped() {
		return StringEscapeUtils.escapeSql(thumbnail);
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getAuthor() {
		return author;
	}

	public String getAuthorEscaped() {
		return StringEscapeUtils.escapeSql(author);
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
