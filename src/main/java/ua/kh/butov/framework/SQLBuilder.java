package ua.kh.butov.framework;

public interface SQLBuilder {

	SearchQuery build(Object... builderParams);
}
