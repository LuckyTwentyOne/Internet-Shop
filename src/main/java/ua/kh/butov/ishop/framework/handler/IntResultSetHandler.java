package ua.kh.butov.ishop.framework.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntResultSetHandler implements ResultSetHandler<Integer> {
	@Override
	public Integer handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 0;
		}
	}
}
