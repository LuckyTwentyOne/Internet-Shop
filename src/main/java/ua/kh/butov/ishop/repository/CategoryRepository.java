package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.Category;

public interface CategoryRepository {

	List<Category> listAllCategories();
}