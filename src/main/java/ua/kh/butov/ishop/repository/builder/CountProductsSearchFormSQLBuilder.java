package ua.kh.butov.ishop.repository.builder;

public class CountProductsSearchFormSQLBuilder extends AbstractSearchFormSQLBuilder {
	@Override
	protected String getSelectFields() {
		return "count(*)";
	}
}
