package com.spring.hibernate.rest;

import java.util.List;

public interface AccountService {

	// Will be implementing the CRUD operation
	public boolean save(Account account);

	/*public boolean delete(Account account);*/

	public boolean update(Account account);

	public List<Account> read();

	public Account getById(long id);

}
