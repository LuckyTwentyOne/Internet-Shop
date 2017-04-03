package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.framework.annotation.jdbc.CollectionItem;
import ua.kh.butov.ishop.framework.annotation.jdbc.Select;

public interface CategoryRepository {

	@Select("select * from category order by id")
	@CollectionItem(Category.class)
	List<Category> listAllCategories();
}