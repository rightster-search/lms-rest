package in.lms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import in.lms.model.CourseCategory;
import in.lms.model.CourseCategoryEnvelope;
import in.lms.model.LoginEnvelope;
import in.lms.model.PasswordWrapper;
import in.lms.model.SessionWrapper;
import in.lms.model.TestModel;
import in.lms.model.UserRole;
import in.lms.model.UserSequence;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.*;

public class MiscellaneousServiceImpl implements MiscellaneousService {

	private SessionFactory sessionFactory;
	private static final String CLASSNAME = "in.lms.model.CourseCategory";
	private static final String LOGIN_ENVELOPE_CLASSNAME = "in.lms.model.LoginEnvelope";
	private static final String SESSION_CLASSNAME = "in.lms.model.SessionWrapper";
	private static final String TEST_MODEL_CLASSNAME = "in.lms.model.TestModel";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Boolean addCourseCategory(CourseCategoryEnvelope envelope) {

		if (envelope != null) {
			List<CourseCategory> categories = envelope.getCategories();
			if (!categories.isEmpty()) {

				for (CourseCategory cat : categories) {
					Session aSession = this.sessionFactory.openSession();
					Transaction t = aSession.beginTransaction();

					try {
						// Need to change code to see already such a category
						// exist
						cat.setCategoryName(cat.getCategoryName().toUpperCase()
								.trim());
						cat.setIsActive(true);
						Long id = (Long) aSession.save(cat);
						t.commit();

					} catch (HibernateException e) {
						if (!t.wasCommitted()) {
							t.rollback();
						}
						throw e;
					} finally {
						aSession.close();
					}

				}

				return true;
			}
		}
		return false;
	}

	public List<CourseCategory> getCourseCategories() {

		Session aSession = this.sessionFactory.openSession();
		Transaction t = aSession.beginTransaction();

		List<CourseCategory> empList = null;

		try {
			Query query = aSession.createQuery("from " + CLASSNAME);
			empList = query.list();
			t.commit();

		} catch (HibernateException e) {
			if (!t.wasCommitted()) {
				t.rollback();
			}
			throw e;
		} finally {
			aSession.close();
		}
		if (empList == null)
			empList = new ArrayList<CourseCategory>();
		return empList;
	}

	public Boolean saveLoginEnvelope(LoginEnvelope envelope) {
		if (envelope != null) {
			List<PasswordWrapper> pwdList = envelope.getPasswordList();
			for (PasswordWrapper pwd : pwdList) {
				if (pwd.getEnvelope() == null) {
					pwd.setEnvelope(envelope);
				}
			}

			List<SessionWrapper> sessionList = envelope.getSessionList();
			for (SessionWrapper session : sessionList) {
				if (session.getEnvelope() == null) {
					session.setEnvelope(envelope);
				}
			}

			UserSequence seq = envelope.getSequenceNo();
			if (seq.getUser() == null) {
				seq.setUser(envelope);
			}
			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();

			try {

				UserRole id = (UserRole) aSession.save(envelope);
				t.commit();

			} catch (HibernateException e) {
				if (!t.wasCommitted()) {
					t.rollback();
				}
				throw e;
			} finally {
				aSession.close();
			}
			return true;
		}

		return false;
	}

	public Boolean registerUser(String username, String password) {

		Session aSession = this.sessionFactory.openSession();
		Transaction t = aSession.beginTransaction();

		LoginEnvelope env = new LoginEnvelope();
		// env.setUsername(username);

		PasswordWrapper passwd = new PasswordWrapper();

		return null;
	}

	public LoginEnvelope getLoginEnvelope(String username, String role) {
		Session aSession = this.sessionFactory.openSession();
		Transaction t = aSession.beginTransaction();

		// List<LoginEnvelope> userList = null;
		LoginEnvelope user = null;

		try {
			UserRole usrRl = new UserRole();
			usrRl.setRole(role);
			usrRl.setUsername(username);

			user = (LoginEnvelope) aSession.get(LoginEnvelope.class, usrRl);
			// Query query = aSession.createQuery("from " +
			// LOGIN_ENVELOPE_CLASSNAME+ " user WHERE user.username = "+username
			// + "and user.role = "+role);
			// userList = query.list();
			System.out.println(user);
			t.commit();

			return user;

		} catch (HibernateException e) {
			if (!t.wasCommitted()) {
				t.rollback();
			}
			throw e;
		} finally {
			aSession.close();
		}

	}

	public Boolean updateLoginEnvelope(LoginEnvelope envelope) {
		if (envelope != null) {
			List<PasswordWrapper> pwdList = envelope.getPasswordList();
			for (PasswordWrapper pwd : pwdList) {
				if (pwd.getEnvelope() == null) {
					pwd.setEnvelope(envelope);
				}
			}

			List<SessionWrapper> sessionList = envelope.getSessionList();
			for (SessionWrapper session : sessionList) {
				if (session.getEnvelope() == null) {
					session.setEnvelope(envelope);
				}
			}

			UserSequence seq = envelope.getSequenceNo();
			if (seq.getUser() == null) {
				seq.setUser(envelope);
			}
			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();

			try {

				aSession.merge(envelope);
				t.commit();

			} catch (HibernateException e) {
				if (!t.wasCommitted()) {
					t.rollback();
				}
				throw e;
			} finally {
				aSession.close();
			}
			return true;
		}

		return false;
	}

	public SessionWrapper getSessionWrapper(String sessionId) {
		// TODO Auto-generated method stub

		if (sessionId != null) {
			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();

			// List<LoginEnvelope> userList = null;
			SessionWrapper sessionObj = null;

			try {

				// user = (LoginEnvelope)aSession.get(LoginEnvelope.class,
				// usrRl);
				Query query = aSession.createQuery("from " + SESSION_CLASSNAME
						+ " session WHERE session.sessionID = '" + sessionId
						+ "'");
				List<SessionWrapper> sessionObjs = query.list();

				if (sessionObjs != null && !sessionObjs.isEmpty()) {
					sessionObj = sessionObjs.get(0);
					LoginEnvelope env = sessionObj.getEnvelope();
					System.out.println(env);

				}
				System.out.println(query);

				t.commit();

				return sessionObj;

			} catch (HibernateException e) {
				if (!t.wasCommitted()) {
					t.rollback();
				}
				throw e;
			} finally {
				aSession.close();
			}
			// return null;
		}

		return null;
	}

	public Boolean updateSessionWrapper(SessionWrapper session) {
		if (session != null) {

			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();

			try {

				aSession.merge(session);
				t.commit();

			} catch (HibernateException e) {
				if (!t.wasCommitted()) {
					t.rollback();
				}
				throw e;
			} finally {
				aSession.close();
			}
			return true;
		}

		return false;
	}

	public LoginEnvelope getLoginEnvelopeFromUID(String uid) {
		Session aSession = this.sessionFactory.openSession();
		Transaction t = aSession.beginTransaction();

		// List<LoginEnvelope> userList = null;
		LoginEnvelope user = null;

		try {
			Long id = Long.parseLong(uid);
			UserSequence userSeqNo = (UserSequence) aSession.get(
					UserSequence.class, id);
			user = userSeqNo.getUser();
			System.out.println(user);

			t.commit();

			// return sessionObj;

		} catch (HibernateException e) {
			if (!t.wasCommitted()) {
				t.rollback();
			}
			throw e;
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception");
		} finally {
			aSession.close();
		}

		return user;
	}

	public Boolean addTestModel(TestModel demo) {
		if (demo != null) {

			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();

			try {
				// Need to change code to see already such a category
				// exist

				Long id = (Long) aSession.save(demo);
				t.commit();
				return true;

			} catch (HibernateException e) {
				if (!t.wasCommitted()) {
					t.rollback();
				}
				throw e;
			} finally {
				aSession.close();
			}

		}

		return false;

	}

	public List<TestModel> getTestModel() {
		Session aSession = this.sessionFactory.openSession();
		Transaction t = aSession.beginTransaction();

		List<TestModel> empList = null;

		try {
			Query query = aSession.createQuery("from " + TEST_MODEL_CLASSNAME);
			empList = query.list();
			t.commit();

		} catch (HibernateException e) {
			if (!t.wasCommitted()) {
				t.rollback();
			}
			throw e;
		} finally {
			aSession.close();
		}
		if (empList == null)
			empList = new ArrayList<TestModel>();
		return empList;
	}
	

}
