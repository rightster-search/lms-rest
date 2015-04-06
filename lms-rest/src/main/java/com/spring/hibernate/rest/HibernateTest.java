package com.spring.hibernate.rest;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
	
	private static final String CLASSNAME = "com.spring.hibernate.rest.Account";
	
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Account> accountList = session.createQuery("from " + CLASSNAME).list();
		tx.commit();
		
		for(Account acc : accountList)
		{
			System.out.println(acc);
		}
		session.flush();
		session.close();
		
		sessionFactory.close();
	}

}
