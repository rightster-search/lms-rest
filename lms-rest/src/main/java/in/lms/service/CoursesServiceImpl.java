package in.lms.service;

import in.lms.model.AtomicCourseSchedule;
import in.lms.model.Course;
import in.lms.model.CourseSchedule;
import in.lms.model.CustomFields;
import in.lms.model.SubSection;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.spring.hibernate.rest.Account;
import com.spring.hibernate.rest.Bookmark;

public class CoursesServiceImpl implements CoursesService {

	private SessionFactory sessionFactory;
	private static final String CLASSNAME = "in.lms.model.Course";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Boolean saveACourse(Course aCourse) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();

		aCourse.setCreationDate(new Date());
		aCourse.setIsActive(true);
		Set<SubSection> sections = aCourse.getSubsections();
		for (SubSection aSection : sections) {
			if (aSection.getCourse() == null) {
				aSection.setCourse(aCourse);
			}
		}

		Set<CustomFields> flds = aCourse.getCustomFields();
		for (CustomFields aFld : flds) {
			if (aFld.getCourse() == null) {
				aFld.setCourse(aCourse);
			}
		}
		
		Set<CourseSchedule> courseSchs = aCourse.getCourseSchedules();

		for (CourseSchedule sec : courseSchs) {
			if (sec.getCourse() == null) {
				sec.setCourse(aCourse);
			}
			Set<AtomicCourseSchedule> temp = sec.getSubSchedule();
			for(AtomicCourseSchedule tmp : temp)
			{
				if(tmp.getSchedule() == null)
				{
					tmp.setSchedule(sec);
				}
			}
		}
		
		
		aSession.save(aCourse);
		aSession.getTransaction().commit();
		aSession.close();

		return true;
	}

	public Boolean upDateACourse(Course aCourse) {
		if (aCourse.getId() != null) {
			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();
			aCourse.getUpdateDates().add(new Date());
			try {
				aSession.merge(aCourse);
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

	public Boolean deleteACourse(Course aCourse) {
		// TODO Auto-generated method stub
		return null;
	}

	public Course getCourseByID(Long id) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();
		Course aCourse = (Course) aSession.get(Course.class, id);
		System.out.println(aCourse);
		aSession.getTransaction().commit();
		aSession.close();
		return aCourse;
	}

	public List<Course> getAllCourses() {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();

		Query query = aSession.createQuery("from " + CLASSNAME
				+ " where id != :id");
		query.setLong("id", 1);

		List<Course> courseList = query.list();// need to check this code
		System.out.println(courseList);
		aSession.getTransaction().commit();
		aSession.close();
		return courseList;
	}
}
