package ua.kh.butov.ishop.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.exception.SqlApplicationException;
import ua.kh.butov.ishop.service.ProductService;
import ua.kh.butov.ishop.service.jdbc.JDBCUtils;
import ua.kh.butov.ishop.service.jdbc.ResultSetHandler;
import ua.kh.butov.ishop.service.jdbc.ResultSetHandlerFactory;

class ProductServiceImpl implements ProductService {

	private static final ResultSetHandler<List<Product>> productsResultSetHandler = ResultSetHandlerFactory
			.getListResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);
	private final DataSource dataSource;

	public ProductServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		try (Connection connection = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			return JDBCUtils.select(connection,
					"select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
							+ "where c.id=p.id_category and pr.id=p.id_producer limit ? offset ?",
					productsResultSetHandler, limit, offset);
		} catch (SQLException e) {
			throw new SqlApplicationException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

}
