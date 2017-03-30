package ua.kh.butov.ishop.repository.impl;

import java.util.List;

import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.framework.factory.JDBCConnectionUtils;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.DefaultUniqueResultSetHandler;
import ua.kh.butov.ishop.framework.handler.IntResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.jdbc.JDBCUtils;
import ua.kh.butov.ishop.repository.OrderRepository;

public class OrderRepositoryImpl implements OrderRepository {

	private final ResultSetHandler<Order> orderResultSetHandler = new DefaultUniqueResultSetHandler<>(Order.class);
	private final ResultSetHandler<List<Order>> ordersResultSetHandler = new DefaultListResultSetHandler<>(Order.class);
	private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler();

	@Override
	public void create(Order order) {
		Order createdOrder = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(), 
				"insert into \"order\" values(nextval('order_seq'),?,?)", orderResultSetHandler, order.getIdAccount(), order.getCreated());
		order.setId(createdOrder.getId());
	}
	
	@Override
	public Order findById(Long id) {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
				"select * from \"order\" where id=?", orderResultSetHandler, id);
	}
	
	@Override
	public List<Order> listMyOrders(Integer idAccount, int offset, int limit) {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from \"order\" where id_account=? order by id desc limit ? offset ?", 
				ordersResultSetHandler, idAccount, limit, offset);
	}

	@Override
	public int countMyOrders(Integer idAccount) {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select count(*) from \"order\" where id_account=?", 
				countResultSetHandler, idAccount);
	}
}
