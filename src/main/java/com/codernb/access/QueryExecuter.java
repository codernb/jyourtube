package com.codernb.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecuter {

	public static ResultSet execute(String query) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:yourtube.db", "SA", "");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		connection.close();
		return resultSet;
	}

}
