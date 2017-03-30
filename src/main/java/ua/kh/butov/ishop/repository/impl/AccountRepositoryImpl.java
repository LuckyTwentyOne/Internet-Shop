package ua.kh.butov.ishop.repository.impl;

import ua.kh.butov.ishop.entity.Account;
import ua.kh.butov.ishop.framework.factory.JDBCConnectionUtils;
import ua.kh.butov.ishop.framework.handler.DefaultUniqueResultSetHandler;
import ua.kh.butov.ishop.framework.handler.ResultSetHandler;
import ua.kh.butov.ishop.jdbc.JDBCUtils;
import ua.kh.butov.ishop.repository.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {
	private final ResultSetHandler<Account> accountResultSetHandler = new DefaultUniqueResultSetHandler<>(Account.class);
	
	@Override
	public Account findByEmail(String email) {
		return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from account where email=?", accountResultSetHandler, email);
	}

	@Override
	public void create(Account account) {
		Account createdAccount = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(), 
				"insert into account values (nextval('account_seq'),?,?,?)", accountResultSetHandler, 
				account.getName(), account.getEmail(), account.getAvatarUrl());
		account.setId(createdAccount.getId());
	}
}
