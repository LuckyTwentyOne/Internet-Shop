package ua.kh.butov.ishop.service.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.kh.butov.ishop.entity.Account;
import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.entity.Product;

public final class ResultSetHandlerFactory {

	public final static ResultSetHandler<Product> PRODUCT_RESULT_SET_HANDLER = new ResultSetHandler<Product>() {
		@Override
		public Product handle(ResultSet rs) throws SQLException {
			Product p = new Product();
			p.setCategory(rs.getString("category"));
			p.setDescription(rs.getString("description"));
			p.setId(rs.getInt("id"));
			p.setImageLink(rs.getString("image_link"));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getBigDecimal("price"));
			p.setProducer(rs.getString("producer"));
			return p;
		}
	};
	
	public final static ResultSetHandler<Category> CATEGORY_RESULT_SET_HANDLER = new ResultSetHandler<Category>() {
		@Override
		public Category handle(ResultSet rs) throws SQLException {
			Category c = new Category();
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			c.setProductCount(rs.getInt("product_count"));
			c.setUrl(rs.getString("url"));
			return c;
		}
	};
	
	public final static ResultSetHandler<Producer> PRODUCER_RESULT_SET_HANDLER = new ResultSetHandler<Producer>() {
		@Override
		public Producer handle(ResultSet rs) throws SQLException {
			Producer p = new Producer();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setProductCount(rs.getInt("product_count"));
			return p;
		}
	};
	
	public final static ResultSetHandler<Account> ACCOUNT_RESULT_SET_HANDLER = new ResultSetHandler<Account>() {
		@Override
		public Account handle(ResultSet rs) throws SQLException {
			Account a = new Account();
			a.setId(rs.getInt("id"));
			a.setEmail(rs.getString("email"));
			a.setName(rs.getString("name"));
			return a;
		}
	};
	
	public final static ResultSetHandler<Integer> getCountResultSetHandler() {
		return new ResultSetHandler<Integer>() {
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			}
		};
	}

	public final static <T> ResultSetHandler<T> getSingleResultSetHandler(
			final ResultSetHandler<T> oneRowResultSetHandler) {
		return new ResultSetHandler<T>() {
			@Override
			public T handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return oneRowResultSetHandler.handle(rs);
				} else {
					return null;
				}
			}
		};
	}

	public final static <T> ResultSetHandler<List<T>> getListResultSetHandler(
			final ResultSetHandler<T> oneRowResultSetHandler) {
		return new ResultSetHandler<List<T>>() {
			@Override
			public List<T> handle(ResultSet rs) throws SQLException {
				List<T> list = new ArrayList<>();
				while (rs.next()) {
					list.add(oneRowResultSetHandler.handle(rs));
				}
				return list;
			}
		};
	}

	private ResultSetHandlerFactory() {
	}
}
