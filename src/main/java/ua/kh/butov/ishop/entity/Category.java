package ua.kh.butov.ishop.entity;

import ua.kh.butov.ishop.framework.annotation.jdbc.Column;
import ua.kh.butov.ishop.framework.annotation.jdbc.Table;

@Table(name="category")
public class Category extends AbstractEntity<Integer> {
	private static final long serialVersionUID = -6041136051105166722L;
	
	private String name;
	private String url;
	@Column("product_count")
	private Integer productCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	@Override
	public String toString() {
		return String.format("Category [id=%s, name=%s, url=%s, productCount=%s]", getId(), name, url, productCount);
	}
}
