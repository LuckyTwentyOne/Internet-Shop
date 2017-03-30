package ua.kh.butov.ishop.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ua.kh.butov.ishop.entity.OrderItem;
import ua.kh.butov.ishop.framework.factory.JDBCConnectionUtils;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.DefaultUniqueResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.jdbc.JDBCUtils;
import ua.kh.butov.ishop.repository.OrderItemRepository;

public class OrderItemRepositoryImpl implements OrderItemRepository {

	private final ResultSetHandler<List<OrderItem>> orderItemListResultSetHandler = new DefaultListResultSetHandler<>(OrderItem.class);
	private final ResultSetHandler<OrderItem> orderItemResultSetHandler = new DefaultUniqueResultSetHandler<>(OrderItem.class);

	@Override
	public List<OrderItem> findByIdOrder(Long idOrder) {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
				"select o.id, o.id_order, o.id_product, o.count, p.id as pid, p.name, p.description, p.price, p.image_link, c.name as category, pr.name as producer "
			  + "from order_item o, product p, category c, producer pr where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?",
				orderItemListResultSetHandler, idOrder);
	}
	
	@Override
	public void create(OrderItem orderItem) {
		/*OrderItem createdOrderItem = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(), 
				"insert into order_item values(nextval('order_item_seq'),?,?,?)", orderItemResultSetHandler, 
				orderItem.getIdOrder(), orderItem.getProduct().getId(), orderItem.getCount());
		orderItem.setId(createdOrderItem.getId());*/
		JDBCUtils.insertBatch(JDBCConnectionUtils.getCurrentConnection(), "insert into order_item values(nextval('order_item_seq'),?,?,?)", toOrderItemParameterList(orderItem));
	}
	private List<Object[]> toOrderItemParameterList(OrderItem orderItem) {
		List<Object[]> parametersList = new ArrayList<>();
			parametersList.add(new Object[] { orderItem.getIdOrder(), orderItem.getProduct().getId(), orderItem.getCount() });
		return parametersList;
	}

}
