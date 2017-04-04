package ua.kh.butov.framework.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.kh.butov.framework.FrameworkSystemException;
import ua.kh.butov.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.framework.handler.DefaultResultSetHandler;
import ua.kh.butov.framework.handler.DefaultUniqueResultSetHandler;
import ua.kh.butov.framework.handler.IntResultSetHandler;
import ua.kh.butov.framework.handler.ResultSetHandler;

@SuppressWarnings("rawtypes")
abstract class JDBCAbstractSQLHelper {

	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected ResultSetHandler<?> createResultSetHandler(Class<? extends ResultSetHandler> resultSetHandlerClass,
			Method method, Class<?> entityClass) throws IllegalAccessException, InvocationTargetException {
		try {
			if (method.getReturnType() == Integer.TYPE) {
				return new IntResultSetHandler();
			} else {
				ResultSetHandler<?> resultSetHandler = null;
				if (DefaultResultSetHandler.class.isAssignableFrom(resultSetHandlerClass)) {
					resultSetHandler = resultSetHandlerClass.getConstructor(Class.class).newInstance(entityClass);
				} else {
					resultSetHandler = resultSetHandlerClass.newInstance();
				}
				if (Collection.class.isAssignableFrom(method.getReturnType())) {
					return new DefaultListResultSetHandler<>(resultSetHandler);
				} else {
					return new DefaultUniqueResultSetHandler<>(resultSetHandler);
				}
			}
		} catch (InstantiationException | NoSuchMethodException e) {
			throw new FrameworkSystemException("Can't create instance of ResultSetHandler for class: " + resultSetHandlerClass, e);
		}
	}
}
