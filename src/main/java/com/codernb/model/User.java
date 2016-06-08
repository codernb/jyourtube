package com.codernb.model;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

public class User {
	
	public String name;
	public String passwordHash;
	public long created;
	
	public User() {
	}
	
	public User(String name, String passwordHash, long created) {
		this.name = name;
		this.passwordHash = passwordHash;
		this.created = created;
	}
	
	public User(String name, String passwordHash) {
		this(name, passwordHash, new Date().getTime());
	}
	
	public String getNameEscaped() {
		return StringEscapeUtils.escapeSql(name);
	}
	
}
