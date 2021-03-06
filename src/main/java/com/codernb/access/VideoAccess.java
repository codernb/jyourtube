package com.codernb.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codernb.controller.DefaultController;
import com.codernb.exception.AccessException;
import com.codernb.exception.NoEntryException;
import com.codernb.model.Video;

public class VideoAccess {

	private VideoAccess() {
	}

	public static void add(Video video) {
		try {
			get(video.id);
		} catch (NoEntryException e) {
			try {
				QueryExecuter.execute(String.format(
						"INSERT INTO video (id, title, thumbnail, author) VALUES ('%s', '%s', '%s', '%s')",
						video.getIdEscaped(), video.getTitleEscaped(), video.getThumbnailEscaped(),
						video.getAuthorEscaped()));
			} catch (SQLException e1) {
				throw new AccessException(e1);
			}
		}
	}

	public static Video get(String videoId) {
		try {
			ResultSet resultSet = QueryExecuter.execute(String.format("SELECT * FROM video WHERE id = '%s'", videoId));
			if (!resultSet.next())
				throw new NoEntryException();
			return getVideo(resultSet);
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
					"SELECT MIN(time) AS time, id, title, thumbnail, author FROM request JOIN video ON request.videoId = video.id GROUP BY id");
			if (!resultSet.next())
				throw new NoEntryException();
			return getVideo(resultSet);
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static Video getRandom() {
		try {
			ResultSet resultSet = QueryExecuter.execute("SELECT * FROM video ORDER BY RAND() LIMIT 1");
			if (!resultSet.next())
				throw new NoEntryException();
			DefaultController.update();
			return getVideo(resultSet);
		} catch (SQLException e) {
			throw new AccessException();
		}
	}

	public static List<Video> getRequested() {
		try {
			ResultSet resultSet = QueryExecuter
					.execute("SELECT * FROM request JOIN video ON request.videoId = video.id");
			List<Video> videos = new ArrayList<>();
			while (resultSet.next())
				videos.add(getVideo(resultSet));
			return videos;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	private static Video getVideo(ResultSet resultSet) throws SQLException {
		String videoId = resultSet.getString("id");
		return new Video(videoId, resultSet.getString("title"), resultSet.getString("thumbnail"),
				resultSet.getString("author"), PlayedRequestsAccess.get(videoId));
	}

}
