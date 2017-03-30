package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.OrderItem;

public interface OrderItemRepository {
	
	List<OrderItem> findByIdOrder(Long idOrder);

	void create(OrderItem orderItem);
}
