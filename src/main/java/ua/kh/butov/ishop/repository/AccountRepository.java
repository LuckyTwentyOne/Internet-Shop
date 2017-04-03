package ua.kh.butov.ishop.repository;

import ua.kh.butov.ishop.entity.Account;
import ua.kh.butov.ishop.framework.annotation.jdbc.Insert;
import ua.kh.butov.ishop.framework.annotation.jdbc.Select;

public interface AccountRepository {

	@Select("select * from account where email=?")
	Account findByEmail(String email);
	
	@Insert
	void create(Account account);
}
