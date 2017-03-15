package ua.kh.butov.ishop.service;

import java.util.List;

import ua.kh.butov.ishop.entity.Product;

public interface ProductService {

	List<Product> listAllProducts(int page, int limit);
}
