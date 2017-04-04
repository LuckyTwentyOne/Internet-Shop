package ua.kh.butov.ishop.service.impl;

import java.util.List;

import ua.kh.butov.framework.annotation.Autowired;
import ua.kh.butov.framework.annotation.Component;
import ua.kh.butov.framework.annotation.jdbc.Transactional;
import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.form.SearchForm;
import ua.kh.butov.ishop.repository.CategoryRepository;
import ua.kh.butov.ishop.repository.ProducerRepository;
import ua.kh.butov.ishop.repository.ProductRepository;
import ua.kh.butov.ishop.service.ProductService;

@Component
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProducerRepository producerRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listAllProducts(limit, offset);
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listProductsByCategory(categoryUrl, limit, offset);
	}

	@Override
	public List<Category> listAllCategories() {
		return categoryRepository.listAllCategories();
	}

	@Override
	public List<Producer> listAllProducers() {
		return producerRepository.listAllProducers();
	}
	
	@Override
	public int countAllProducts() {
		return productRepository.countAllProducts();
	}
	
	@Override
	public int countProductsByCategory(String categoryUrl) {
		return productRepository.countProductsByCategory(categoryUrl);
	}
	
	@Override
	public List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listProductsBySearchForm(searchForm, limit, offset);
	}

	@Override
	public int countProductsBySearchForm(SearchForm searchForm) {
		return productRepository.countProductsBySearchForm(searchForm);
	}
}
