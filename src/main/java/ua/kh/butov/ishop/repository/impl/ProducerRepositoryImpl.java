package ua.kh.butov.ishop.repository.impl;

import java.util.List;

import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.framework.factory.JDBCConnectionUtils;
import ua.kh.butov.ishop.framework.handler.DefaultListResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.jdbc.JDBCUtils;
import ua.kh.butov.ishop.repository.ProducerRepository;

public class ProducerRepositoryImpl implements ProducerRepository {
	private final ResultSetHandler<List<Producer>> producerListResultSetHandler = new DefaultListResultSetHandler<>(Producer.class);
	
	@Override
	public List<Producer> listAllProducers() {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from producer order by name", producerListResultSetHandler);
	}
}
