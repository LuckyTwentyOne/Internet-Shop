package ua.kh.butov.ishop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.ishop.framework.factory.JDBCTransactionalServiceFactory;
import ua.kh.butov.ishop.repository.AccountRepository;
import ua.kh.butov.ishop.repository.CategoryRepository;
import ua.kh.butov.ishop.repository.OrderItemRepository;
import ua.kh.butov.ishop.repository.OrderRepository;
import ua.kh.butov.ishop.repository.ProducerRepository;
import ua.kh.butov.ishop.repository.ProductRepository;
import ua.kh.butov.ishop.repository.impl.AccountRepositoryImpl;
import ua.kh.butov.ishop.repository.impl.CategoryRepositoryImpl;
import ua.kh.butov.ishop.repository.impl.OrderItemRepositoryImpl;
import ua.kh.butov.ishop.repository.impl.OrderRepositoryImpl;
import ua.kh.butov.ishop.repository.impl.ProducerRepositoryImpl;
import ua.kh.butov.ishop.repository.impl.ProductRepositoryImpl;
import ua.kh.butov.ishop.service.OrderService;
import ua.kh.butov.ishop.service.ProductService;
import ua.kh.butov.ishop.service.SocialService;

public class ServiceManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

	private final BasicDataSource dataSource;
	private final ProductService productService;
	private final OrderService orderService;
	private final SocialService socialService;
	private final Properties applicationProperties = new Properties();

	final OrderItemRepository orderItemRepository;
	final OrderRepository orderRepository;
	final ProductRepository productRepository;
	final ProducerRepository producerRepository;
	final CategoryRepository categoryRepository;
	final AccountRepository accountRepository;

	private ServiceManager(ServletContext context) {
		loadApplicationProperties();
		dataSource = createDataSource();
		productRepository = new ProductRepositoryImpl();
		producerRepository = new ProducerRepositoryImpl();
		categoryRepository = new CategoryRepositoryImpl();
		accountRepository = new AccountRepositoryImpl();
		orderRepository = new OrderRepositoryImpl();
		orderItemRepository = new OrderItemRepositoryImpl();
		productService = (ProductService) JDBCTransactionalServiceFactory.createTransactionalService(dataSource,
				new ProductServiceImpl(this));
		orderService = (OrderService) JDBCTransactionalServiceFactory.createTransactionalService(dataSource,
				new OrderServiceImpl(this));
		socialService = new FacebookSocialService(this);
	}

	public static ServiceManager getInstance(ServletContext context) {
		ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
		if (instance == null) {
			instance = new ServiceManager(context);
			context.setAttribute("SERVICE_MANAGER", instance);
		}
		return instance;
	}

	public ProductService getProductService() {
		return productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public SocialService getSocialService() {
		return socialService;
	}

	public String getApplicationProperty(String key) {
		String value = applicationProperties.getProperty(key);
		if (value.startsWith("${sysEnv.")) {
			String keyVal = value.replace("${sysEnv.", "").replace("}", "");
			value = System.getProperty(keyVal);
		}
		return value;
	}

	public void close() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			LOGGER.error("Close datasource failed: " + e.getMessage(), e);
		}
	}

	private void loadApplicationProperties() {
		try (InputStream in = ServiceManager.class.getClassLoader().getResourceAsStream("application.properties")) {
			applicationProperties.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private BasicDataSource createDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDefaultAutoCommit(false);
		dataSource.setRollbackOnReturn(true);
		dataSource.setDriverClassName(getApplicationProperty("db.driver"));
		dataSource.setUrl(getApplicationProperty("db.url"));
		dataSource.setUsername(getApplicationProperty("db.username"));
		dataSource.setPassword(getApplicationProperty("db.password"));
		dataSource.setInitialSize(Integer.parseInt(getApplicationProperty("db.pool.initSize")));
		dataSource.setMaxTotal(Integer.parseInt(getApplicationProperty("db.pool.maxSize")));
		return dataSource;
	}
}
