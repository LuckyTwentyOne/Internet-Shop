package ua.kh.butov.ishop.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.exception.InternalServerErrorException;
import ua.kh.butov.ishop.form.SearchForm;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.IntResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.service.ProductService;
import ua.kh.butov.ishop.service.jdbc.JDBCUtils;
import ua.kh.butov.ishop.service.jdbc.SearchQuery;

class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final ResultSetHandler<List<Product>> productsResultSetHandler = new DefaultListResultSetHandler<>(Product.class);
	private final ResultSetHandler<List<Category>> categoryListResultSetHandler = new DefaultListResultSetHandler<>(Category.class);
	private final ResultSetHandler<List<Producer>> producerListResultSetHandler = new DefaultListResultSetHandler<>(Producer.class);
	private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler();
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
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		try (Connection connection = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			return JDBCUtils.select(connection,
					"select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
							+ "where c.id=p.id_category and pr.id=p.id_producer and c.url=? limit ? offset ?",
					productsResultSetHandler, categoryUrl, limit, offset);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Category> listAllCategories() {
		try (Connection connection = dataSource.getConnection()) {
			return JDBCUtils.select(connection, "select * from category order by id", categoryListResultSetHandler);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Producer> listAllProducers() {
		try (Connection connection = dataSource.getConnection()) {
			return JDBCUtils.select(connection, "select * from producer order by name", producerListResultSetHandler);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public int countAllProducts() {
		try (Connection connection = dataSource.getConnection()) {
			return JDBCUtils.select(connection, "select count(*) from product", countResultSetHandler);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public int countProductsByCategory(String categoryUrl) {
		try (Connection connection = dataSource.getConnection()) {
			return JDBCUtils.select(connection,
					"select count(*) from product p, category c  where p.id_category=c.id and c.url = ?",
					countResultSetHandler, categoryUrl);
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Product> listProductsBySearchForm(SearchForm form, int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			SearchQuery sq = buildSearchQuery("p.*, c.name as category, pr.name as producer", form);
			sq.getSql().append(" order by p.id limit ? offset ?");
			sq.getParams().add(limit);
			sq.getParams().add(offset);
			LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
			return JDBCUtils.select(c, sq.getSql().toString(), productsResultSetHandler, sq.getParams().toArray());
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}

	protected SearchQuery buildSearchQuery(String selectFields, SearchForm form) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select ");
		sql.append(selectFields).append(" from product p, category c, producer pr "
				+ "where pr.id=p.id_producer and c.id=p.id_category and (p.name ilike ? or p.description ilike ?)");
		params.add("%" + form.getQuery() + "%");
		params.add("%" + form.getQuery() + "%");
		JDBCUtils.populateSqlAndParams(sql, params, form.getCategories(), "c.id = ?");
		JDBCUtils.populateSqlAndParams(sql, params, form.getProducers(), "pr.id = ?");
		return new SearchQuery(sql, params);
	}

	@Override
	public int countProductsBySearchForm(SearchForm form) {
		try (Connection c = dataSource.getConnection()) {
			SearchQuery sq = buildSearchQuery("count(*)", form);
			LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
			return JDBCUtils.select(c, sq.getSql().toString(), countResultSetHandler, sq.getParams().toArray());
		} catch (SQLException e) {
			throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
		}
	}

}
