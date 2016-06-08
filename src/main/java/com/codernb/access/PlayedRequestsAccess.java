package com.codernb.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codernb.exception.AccessException;
import com.codernb.model.PlayedRequest;
import com.codernb.model.Request;

public class PlayedRequestsAccess {

	private PlayedRequestsAccess() {
	}

	public static void add(PlayedRequest playedRequest) {
		try {
			QueryExecuter.execute(
					String.format("INSERT INTO playedRequest (videoId, time, timePlayed) VALUES ('%s', '%d', '%d')",
							playedRequest.videoId, playedRequest.time, playedRequest.timePlayed));
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static void add(Request request) {
		add(new PlayedRequest(request.videoId, request.time));
	}

	public static List<PlayedRequest> get(String videoId) {
		try {
			ResultSet resultSet = QueryExecuter
					.execute(String.format("SELECT * FROM playedRequest WHERE videoId = '%s'", videoId));
			List<PlayedRequest> playedRequests = new ArrayList<>();
			while (resultSet.next())
				playedRequests.add(getPlayedRequest(resultSet));
			return playedRequests;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	private static PlayedRequest getPlayedRequest(ResultSet resultSet) throws SQLException {
		return new PlayedRequest(resultSet.getString("videoId"), resultSet.getLong("time"), resultSet.getLong("timePlayed"));
	}

}
