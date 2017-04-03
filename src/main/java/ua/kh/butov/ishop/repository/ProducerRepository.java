package ua.kh.butov.ishop.repository;

import java.util.List;

import ua.kh.butov.ishop.entity.Producer;
import ua.kh.butov.ishop.framework.annotation.jdbc.CollectionItem;
import ua.kh.butov.ishop.framework.annotation.jdbc.Select;

public interface ProducerRepository {
	
	@Select("select * from producer order by name")
	@CollectionItem(Producer.class)
	List<Producer> listAllProducers();
}
