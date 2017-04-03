package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.Order;
import ua.kh.butov.ishop.framework.annotation.jdbc.CollectionItem;
import ua.kh.butov.ishop.framework.annotation.jdbc.Insert;
import ua.kh.butov.ishop.framework.annotation.jdbc.Select;

public interface OrderRepository {

	@Insert
	void create(Order order);

	@Select("select * from \"order\" where id=?")
	Order findById(Long id);

	@Select("select * from \"order\" where id_account=? order by id desc limit ? offset ?")
	@CollectionItem(Order.class)
	List<Order> listMyOrders(Integer idAccount, int limit, int offset);

	@Select("select count(*) from \"order\" where id_account=?")
	int countMyOrders(Integer idAccount);
}
