package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.form.SearchForm;

public interface ProductRepository {

	List<Product> listAllProducts(int offset, int limit);

	int countAllProducts();

	List<Product> listProductsByCategory(String categoryUrl, int offset, int limit);

	int countProductsByCategory(String categoryUrl);

	List<Product> listProductsBySearchForm(SearchForm searchForm, int offset, int limit);

	int countProductsBySearchForm(SearchForm searchForm);
	
	Product findById(int idProduct);
}
