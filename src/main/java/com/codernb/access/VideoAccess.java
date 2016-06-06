package com.codernb.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codernb.exception.AccessException;
import com.codernb.exception.NoEntryException;
import com.codernb.model.PlayedRequest;
import com.codernb.model.Request;
import com.codernb.model.Video;

public class VideoAccess {

	private VideoAccess() {
	}

	public static void add(Video video) {
		try {
			ResultSet resultSet = QueryExecuter
					.execute(String.format("SELECT * FROM video WHERE id = '%s'", video.getId()));
			if (!resultSet.next())
				QueryExecuter.execute(String.format(
						"INSERT INTO video (id, title, description, thumbnail, author) VALUES ('%s', '%s', '%s', '%s', '%s')",
						video.getIdEscaped(), video.getTitleEscaped(), video.getDescriptionEscaped(), video.getThumbnailEscaped(),
						video.getAuthorEscaped()));
			RequestAccess.add(new Request(video.getId(), new Date().getTime()));
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static List<Video> getAll() {
		try {
			ResultSet resultSet = QueryExecuter.execute("SELECT * FROM video");
			List<Video> videos = new ArrayList<>();
			while (resultSet.next())
				videos.add(getVideo(resultSet));
			return videos;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static Video getNext() {
		try {
			ResultSet resultSet = QueryExecuter.execute(
					"SELECT MIN(time) AS time, id, title, description, thumbnail, author FROM request JOIN video ON request.videoId = video.id GROUP BY id");
			if (!resultSet.next())
				throw new NoEntryException();
			String videoId = resultSet.getString("id");
			RequestAccess.delete(videoId);
			PlayedRequestsAccess.add(new PlayedRequest(videoId, resultSet.getLong("time"), new Date().getTime()));
			return getVideo(resultSet);
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}
	
	public static List<Video> getRequested() {
		try {
			ResultSet resultSet = QueryExecuter.execute("SELECT * FROM request JOIN video ON request.videoId = video.id");
			List<Video> videos = new ArrayList<>();
			while (resultSet.next())
				videos.add(getVideo(resultSet));
			return videos;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	private static Video getVideo(ResultSet resultSet) throws SQLException {
		return new Video(resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("description"),
				resultSet.getString("thumbnail"), resultSet.getString("author"));
	}

}
