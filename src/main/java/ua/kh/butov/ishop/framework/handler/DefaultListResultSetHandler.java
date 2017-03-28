package ua.kh.butov.ishop.framework.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultListResultSetHandler<T> implements ResultSetHandler<List<T>> {
	private ResultSetHandler<T> oneRowResultSetHandler;

	public DefaultListResultSetHandler(ResultSetHandler<T> oneRowResultSetHandler) {
		this.oneRowResultSetHandler = oneRowResultSetHandler;
	}

	public DefaultListResultSetHandler(Class<T> entityClass) {
		this(new DefaultResultSetHandler<>(entityClass));
	}

	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		List<T> list = new ArrayList<>();
		while (rs.next()) {
			list.add(oneRowResultSetHandler.handle(rs));
		}
		return list;
	}
}
