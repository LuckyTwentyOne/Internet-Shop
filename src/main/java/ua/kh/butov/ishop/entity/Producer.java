package ua.kh.butov.ishop.entity;

import ua.kh.butov.framework.annotation.jdbc.Column;
import ua.kh.butov.framework.annotation.jdbc.Table;

@Table(name="product")
public class Producer extends AbstractEntity<Integer> {
	private static final long serialVersionUID = -320441763179477663L;

	private String name;
	@Column("product_count")
	private Integer productCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	
}
