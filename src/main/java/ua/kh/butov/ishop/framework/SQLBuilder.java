package ua.kh.butov.ishop.framework;

public interface SQLBuilder {

	SearchQuery build(Object... builderParams);
}
