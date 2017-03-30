package ua.kh.butov.ishop.repository.impl;

import java.util.List;

import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.framework.factory.JDBCConnectionUtils;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.jdbc.JDBCUtils;
import ua.kh.butov.ishop.repository.CategoryRepository;

public class CategoryRepositoryImpl implements CategoryRepository {
	private final ResultSetHandler<List<Category>> categoryListResultSetHandler = new DefaultListResultSetHandler<>(Category.class);
	@Override
	public List<Category> listAllCategories() {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from category order by id", categoryListResultSetHandler);
	}
}
