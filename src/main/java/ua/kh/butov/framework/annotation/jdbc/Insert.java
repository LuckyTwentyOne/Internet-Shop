package ua.kh.butov.framework.annotation.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ua.kh.butov.framework.handler.DefaultResultSetHandler;
import ua.kh.butov.framework.handler.ResultSetHandler;

@SuppressWarnings("rawtypes")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Insert {
	Class<? extends ResultSetHandler> resultSetHandlerClass() default DefaultResultSetHandler.class;
}
