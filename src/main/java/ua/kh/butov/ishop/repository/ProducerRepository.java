package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.framework.annotation.JDBCRepository;
import ua.kh.butov.framework.annotation.jdbc.CollectionItem;
import ua.kh.butov.framework.annotation.jdbc.Select;
import ua.kh.butov.ishop.entity.Producer;

@JDBCRepository
public interface ProducerRepository {

	@Select("select * from producer order by name")
	@CollectionItem(Producer.class)
	List<Producer> listAllProducers();
}
