package com.codernb.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codernb.exception.AccessException;
import com.codernb.exception.AlreadyExistsException;
import com.codernb.exception.NoEntryException;
import com.codernb.model.User;

public class UserAccess {

	public static void add(User user) {
		try {
			get(user.name);
			throw new AlreadyExistsException();
		} catch (NoEntryException e) {
			try {

				QueryExecuter.execute(String.format("INSERT INTO user VALUES ('%s', '%s', '%d'", user.name,
						user.passwordHash, user.created));
			} catch (SQLException e1) {
				throw new AccessException(e1);
			}
		}
	}

	public static User get(String userName) {
		try {
			ResultSet resultSet = QueryExecuter
					.execute(String.format("SELECT * FROM user WHERE NAME = '%s'", userName));
			if (!resultSet.next())
				throw new NoEntryException();
			return getUser(resultSet);
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static List<User> getAll() {
		try {
			ResultSet resultSet = QueryExecuter.execute("SELECT * FROM user");
			List<User> users = new ArrayList<>();
			while (resultSet.next())
				users.add(getUser(resultSet));
			return users;
		} catch (SQLException e) {
			throw new AccessException(e);
		}
	}

	public static User getUser(ResultSet resultSet) throws SQLException {
		return new User(resultSet.getString("name"), resultSet.getString("passwordHash"), resultSet.getLong("created"));
	}

}
