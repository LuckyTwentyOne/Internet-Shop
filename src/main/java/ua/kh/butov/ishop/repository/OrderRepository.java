package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.Order;

public interface OrderRepository {

	void create(Order order);

	Order findById(Long id);

	List<Order> listMyOrders(Integer idAccount, int offset, int limit);

	int countMyOrders(Integer idAccount);
}
