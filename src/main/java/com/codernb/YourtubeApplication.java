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
		QueryExecuter.execute("DROP TABLE IF EXISTS user");

		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS user (name VARCHAR(255) NOT NULL PRIMARY KEY, passwordHash VARCHAR(255) NOT NULL, created BIGINT NOT NULL, sessionToken VARCHAR(255))");
		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS video (id VARCHAR(11) NOT NULL PRIMARY KEY, title VARCHAR(255) NOT NULL, thumbnail VARCHAR(255) NOT NULL, author VARCHAR(255) NOT NULL)");
		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS request (videoId VARCHAR(11) NOT NULL FOREIGN KEY REFERENCES video(id), time BIGINT NOT NULL, userName VARCHAR(255) FOREIGN KEY REFERENCES user(name))");
		QueryExecuter.execute(
				"CREATE TABLE IF NOT EXISTS playedRequest (videoId VARCHAR(11) NOT NULL FOREIGN KEY REFERENCES video(id), time BIGINT NOT NULL, timePlayed BIGINT NOT NULL, userName VARCHAR(255) FOREIGN KEY REFERENCES user(name))");
		SpringApplication.run(YourtubeApplication.class, args);
	}

}
