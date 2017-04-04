package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.framework.annotation.JDBCRepository;
import ua.kh.butov.framework.annotation.jdbc.CollectionItem;
import ua.kh.butov.framework.annotation.jdbc.Select;
import ua.kh.butov.ishop.entity.Category;

@JDBCRepository
public interface CategoryRepository {

	@Select("select * from category order by id")
	@CollectionItem(Category.class)
	List<Category> listAllCategories();
}