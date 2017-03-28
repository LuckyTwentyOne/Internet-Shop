package ua.kh.butov.ishop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.form.SearchForm;
import ua.kh.butov.ishop.framework.annotation.jdbc.Transactional;
import ua.kh.butov.ishop.framework.factory.JDBCConnectionUtils;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.IntResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.service.ProductService;
import ua.kh.butov.ishop.service.jdbc.JDBCUtils;
import ua.kh.butov.ishop.service.jdbc.SearchQuery;

@Transactional
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final ResultSetHandler<List<Product>> productsResultSetHandler = new DefaultListResultSetHandler<>(Product.class);
	private final ResultSetHandler<List<Category>> categoryListResultSetHandler = new DefaultListResultSetHandler<>(Category.class);
	private final ResultSetHandler<List<Producer>> producerListResultSetHandler = new DefaultListResultSetHandler<>(Producer.class);
	private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler();

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		int offset = (page - 1) * limit;
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
				"select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
						+ "where c.id=p.id_category and pr.id=p.id_producer limit ? offset ?",
				productsResultSetHandler, limit, offset);
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		int offset = (page - 1) * limit;
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
				"select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
						+ "where c.id=p.id_category and pr.id=p.id_producer and c.url=? limit ? offset ?",
				productsResultSetHandler, categoryUrl, limit, offset);
	}

	@Override
	public List<Category> listAllCategories() {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from category order by id", categoryListResultSetHandler);
	}

	@Override
	public List<Producer> listAllProducers() {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from producer order by name", producerListResultSetHandler);
	}

	@Override
	public int countAllProducts() {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select count(*) from product", countResultSetHandler);
	}

	@Override
	public int countProductsByCategory(String categoryUrl) {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
				"select count(*) from product p, category c  where p.id_category=c.id and c.url = ?",
				countResultSetHandler, categoryUrl);
	}

	@Override
	public List<Product> listProductsBySearchForm(SearchForm form, int page, int limit) {
		int offset = (page - 1) * limit;
		SearchQuery sq = buildSearchQuery("p.*, c.name as category, pr.name as producer", form);
		sq.getSql().append(" order by p.id limit ? offset ?");
		sq.getParams().add(limit);
		sq.getParams().add(offset);
		LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), sq.getSql().toString(), productsResultSetHandler, sq.getParams().toArray());
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
		SearchQuery sq = buildSearchQuery("count(*)", form);
		LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), sq.getSql().toString(), countResultSetHandler, sq.getParams().toArray());
	}

}
