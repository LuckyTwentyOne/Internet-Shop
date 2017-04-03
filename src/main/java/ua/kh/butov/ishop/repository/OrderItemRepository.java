package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.OrderItem;
import ua.kh.butov.ishop.framework.annotation.jdbc.CollectionItem;
import ua.kh.butov.ishop.framework.annotation.jdbc.Insert;
import ua.kh.butov.ishop.framework.annotation.jdbc.Select;

public interface OrderItemRepository {

	@Select("select o.id, o.id_order, o.id_product, o.count, p.name, "
			+ "p.description, p.price, p.image_link, c.name as category, pr.name as producer "
			+ "from order_item o, product p, category c, producer pr "
			+ "where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?")
	@CollectionItem(OrderItem.class)
	List<OrderItem> findByIdOrder(Long idOrder);

	@Insert
	void create(OrderItem orderItem);
}
