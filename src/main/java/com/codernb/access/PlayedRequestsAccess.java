package com.codernb.access;

import java.sql.SQLException;

import com.codernb.exception.AccessException;
import com.codernb.model.PlayedRequest;

public class PlayedRequestsAccess {

	private PlayedRequestsAccess() {
	}

	public static void add(PlayedRequest playedRequest) {
		try {
			QueryExecuter.execute(
					String.format("INSERT INTO playedRequest (videoId, time, timePlayed) VALUES ('%s', '%s', '%s')",
							playedRequest.videoId, playedRequest.time, playedRequest.timePlayed));
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

}
