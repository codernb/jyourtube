package com.codernb;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.codernb.access.QueryExecuter;

@SpringBootApplication
public class YourtubeApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.hsqldb.jdbcDriver");
		
		QueryExecuter.execute("DROP TABLE IF EXISTS playedRequest");
		QueryExecuter.execute("DROP TABLE IF EXISTS request");
		QueryExecuter.execute("DROP TABLE IF EXISTS video");
		
		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS video (id VARCHAR(11) PRIMARY KEY, title VARCHAR(255), description VARCHAR(16383), thumbnail VARCHAR(255), author VARCHAR(255))");
		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS request (videoId VARCHAR(11) NOT NULL FOREIGN KEY REFERENCES video(id), time BIGINT)");
		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS playedRequest (videoId VARCHAR(11) NOT NULL FOREIGN KEY REFERENCES video(id), time BIGINT, timePlayed BIGINT)");
		SpringApplication.run(YourtubeApplication.class, args);
	}
	
}
