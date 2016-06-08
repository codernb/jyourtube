package com.codernb.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codernb.controller.DefaultController;
import com.codernb.exception.AccessException;
import com.codernb.exception.NoEntryException;
import com.codernb.model.Request;
import com.codernb.model.Video;

public class RequestAccess {

	private RequestAccess() {
	}

	public static Request get(String videoId) {
		try {
			ResultSet resultSet = QueryExecuter
					.execute(String.format("SELECT * FROM request WHERE videoId = '%s'", videoId));
			if (!resultSet.next())
				throw new NoEntryException();
			return getRequest(resultSet);
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static void add(Request request) {
		try {
			ResultSet resultSet = QueryExecuter
					.execute(String.format("SELECT * FROM request WHERE videoId = '%s'", request.videoId));
			if (resultSet.next())
				return;
			QueryExecuter.execute(String.format("INSERT INTO request (videoId, time) VALUES ('%s', '%d')",
					request.videoId, request.time));
			DefaultController.update();
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static void add(Video video) {
		add(new Request(video.getIdEscaped()));
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

	public static Request delete(String videoId) {
		try {
			Request request = get(videoId);
			QueryExecuter.execute(String.format("DELETE FROM request WHERE videoId = '%s'", videoId));
			DefaultController.update();
			return request;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static void clear() {
		try {
			QueryExecuter.execute("DELETE FROM request");
			DefaultController.update();
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	private static Request getRequest(ResultSet resultSet) throws SQLException {
		return new Request(resultSet.getString("videoId"), resultSet.getLong("time"));
	}

}
