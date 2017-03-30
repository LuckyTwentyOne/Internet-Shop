package ua.kh.butov.ishop.service.impl;

import java.util.List;

import ua.kh.butov.ishop.entity.Category;
import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.entity.Product;
import ua.kh.butov.ishop.form.SearchForm;
import ua.kh.butov.ishop.framework.annotation.jdbc.Transactional;
import ua.kh.butov.ishop.repository.CategoryRepository;
import ua.kh.butov.ishop.repository.ProducerRepository;
import ua.kh.butov.ishop.repository.ProductRepository;
import ua.kh.butov.ishop.service.ProductService;

@Transactional
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final ProducerRepository producerRepository;
	private final CategoryRepository categoryRepository;
	
	public ProductServiceImpl(ServiceManager serviceManager) {
		this.productRepository = serviceManager.productRepository;
		this.producerRepository = serviceManager.producerRepository;
		this.categoryRepository = serviceManager.categoryRepository;
	}

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listAllProducts(offset, limit);
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listProductsByCategory(categoryUrl, offset, limit);
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
		return productRepository.listProductsBySearchForm(searchForm, offset, limit);
	}

	@Override
	public int countProductsBySearchForm(SearchForm searchForm) {
		return productRepository.countProductsBySearchForm(searchForm);
	}
}
