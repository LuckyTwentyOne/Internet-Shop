package ua.kh.butov.ishop.repository;

import ua.kh.butov.ishop.entity.Account;

public interface AccountRepository {

	Account findByEmail(String email);
	
	void create(Account account);
}
