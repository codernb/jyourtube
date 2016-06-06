package com.codernb.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codernb.exception.AccessException;
import com.codernb.model.Request;

public class RequestAccess {

	private static int version;

	private RequestAccess() {
	}

	public static void add(Request request) {
		try {
			ResultSet resultSet = QueryExecuter
					.execute(String.format("SELECT * FROM request WHERE videoId = '%s'", request.getVideoId()));
			if (resultSet.next())
				return;
			QueryExecuter.execute(String.format("INSERT INTO request (videoId, time) VALUES ('%s', '%s')",
					request.getVideoId(), request.getTime()));
			version++;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static List<Request> getAll() {
		try {
			ResultSet resultSet = QueryExecuter.execute("SELECT * FROM request");
			List<Request> requests = new ArrayList<>();
			while (resultSet.next())
				requests.add(getRequest(resultSet));
			return requests;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static void delete(String videoId) {
		try {
			QueryExecuter.execute(String.format("DELETE FROM request WHERE videoId = '%s'", videoId));
			version++;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static void clear() {
		try {
			QueryExecuter.execute("DELETE FROM request");
			version++;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static int getVersion() {
		return version;
	}

	private static Request getRequest(ResultSet resultSet) throws SQLException {
		return new Request(resultSet.getString("videoId"), resultSet.getLong("time"));
	}

}
