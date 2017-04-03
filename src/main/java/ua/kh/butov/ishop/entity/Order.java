package ua.kh.butov.ishop.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import ua.kh.butov.ishop.framework.annotation.jdbc.Column;
import ua.kh.butov.ishop.framework.annotation.jdbc.Table;
import ua.kh.butov.ishop.framework.annotation.jdbc.Transient;

@Table(name="\"order\"", nextIdExpression="nextval('order_seq')")
public class Order extends AbstractEntity<Long>{
	private static final long serialVersionUID = 1375749915108285716L;
	@Column("id_account")
	private Integer idAccount;
	@Transient
	private List<OrderItem> items;
	private Timestamp created;

	public Order() {
	}

	public Order(Integer idAccount, Timestamp created) {
		this.idAccount = idAccount;
		this.created = created;
	}

	public Integer getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Integer idAccount) {
		this.idAccount = idAccount;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public BigDecimal getTotalCost() {
		BigDecimal cost = BigDecimal.ZERO;
		if (items != null) {
			for (OrderItem item : items) {
				cost = cost.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getCount())));
			}
		}
		return cost;
	}

	@Override
	public String toString() {
		return String.format("Order [id=%s, idAccount=%s, items=%s, created=%s]", getId(), idAccount, items, created);
	}
}
