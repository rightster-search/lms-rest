package com.spring.hibernate.rest;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AccountServiceImpl implements AccountService {

	private SessionFactory sessionFactory;
	private static final String CLASSNAME = "com.spring.hibernate.rest.Account";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public boolean save(Account account) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();

		Set<Bookmark> bkMarks = account.getBookmarks();
		aSession.persist(account);
		for (Bookmark bk : bkMarks) {
			if (bk.getAccount() == null) {
				bk.setAccount(account);
			}
			aSession.persist(bk);
		}
		aSession.getTransaction().commit();
		aSession.close();

		return true;
	}

	/*
	 * public long delete(Account account) { // TODO Auto-generated method stub
	 * return 0; }
	 * 
	 * 
	 */
	
	public boolean update(Account account) {
		// TODO Auto-generated method stub
		Long id = account.getId();
		Account newAcc = getById(id);
		if (newAcc != null) {
			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();
			Set<Bookmark> bookMarks = account.getBookmarks();
			for (Bookmark aBookMark : bookMarks) {
				if (aBookMark.getAccount() == null) {
					aBookMark.setAccount(account);
				}
			}
			try {
				aSession.merge(account);
				t.commit();

				return true;
			} catch (HibernateException e) {
				t.rollback();
				throw e;
			} finally {
				aSession.close();
			}
		}
		else 
			return false;
	}

	@SuppressWarnings("unchecked")
	public List<Account> read() {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();

		List<Account> accountList = aSession.createQuery("from " + CLASSNAME).list();
		//System.out.println(accountList);
		aSession.getTransaction().commit();
		aSession.close();
		return accountList;
	}

	public Account getById(long id) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();
		Account aAccount = (Account) aSession.get(Account.class, id);
		aSession.getTransaction().commit();
		aSession.close();
		return aAccount;
	}

}
