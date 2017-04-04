package ua.kh.butov.ishop.repository;

import ua.kh.butov.framework.annotation.JDBCRepository;
import ua.kh.butov.framework.annotation.jdbc.Insert;
import ua.kh.butov.framework.annotation.jdbc.Select;
import ua.kh.butov.ishop.entity.Account;
@JDBCRepository
public interface AccountRepository {

	@Select("select * from account where email=?")
	Account findByEmail(String email);
	
	@Insert
	void create(Account account);
}
