package in.lms.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.spring.hibernate.rest.Account;
import com.spring.hibernate.rest.Bookmark;

import in.lms.common.CourseTypeConstants;
import in.lms.common.DayOfWeekConstant;
import in.lms.common.ScheduleType;
import in.lms.model.AtomicCourseSchedule;
import in.lms.model.Course;
import in.lms.model.CourseSchedule;
import in.lms.model.CustomFields;
import in.lms.model.DataTypeConstants;
import in.lms.model.SubSection;

public class CourseSkeletonServiceImpl implements CourseSkeletonService {

	private SessionFactory sessionFactory;
	private static final String CLASSNAME = "in.lms.model.Course";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Course returnSkeleton() {
		// Long id = account.getId();
		// Account newAcc = getById(id);
		Course aCourse = null;
		Session aSession = this.sessionFactory.openSession();
		Session session2 = null;
		Transaction t = aSession.beginTransaction();
		/*
		 * Set<Bookmark> bookMarks = account.getBookmarks(); for (Bookmark
		 * aBookMark : bookMarks) { if (aBookMark.getAccount() == null) {
		 * aBookMark.setAccount(account); } }
		 */

		try {
			long idTemp = 1;
			aCourse = (Course) aSession.get(Course.class, idTemp);
			System.out.println(aCourse);
			if (aCourse == null) {
				aCourse = new Course();
				aCourse.setCreationDate(new Date());
				aCourse.setDescription("Demo description");
				aCourse.setIsActive(true);
				aCourse.setBigImageURL("http://type1-big.com");
				aCourse.setSmallImageURL("http://type1-small.com");
				
				aCourse.setCourseCategory("JAVA");
				aCourse.setCourseType(CourseTypeConstants.ONLINE);
				aCourse.setDurationOfCourse(40);
				aCourse.setPrice(new BigDecimal("12000.00"));
				aCourse.setTitle("JAVA Core is th way to go");
				
				Set<SubSection> subSections = aCourse.getSubsections();
				SubSection subSection = new SubSection();
				subSection.setCourse(aCourse);
				subSection.setHeading("DEMO HEADING");
				Collection<String> bulletPoints = subSection.getBulletPoints();
				bulletPoints.add("DEMO BULLLET 1");
				bulletPoints.add("DEMO BULLLET 2");

				subSections.add(subSection);
				
				aCourse.getUpdateDates().add(new Date());
				Set<CustomFields> customFields = aCourse.getCustomFields();
				CustomFields cFld = new CustomFields();
				cFld.setCourse(aCourse);
				cFld.setIsActive(true);
				cFld.setName("CustomField1");
				cFld.setValue("Arka Dutta");
				cFld.setDataType(DataTypeConstants.STRING);
				customFields.add(cFld);
				
				Set<CourseSchedule> schedules = aCourse.getCourseSchedules();
				CourseSchedule sch = new CourseSchedule();
				sch.setCourse(aCourse);
				sch.setTypeOfCourse(ScheduleType.WEEKENDS);
				Set<AtomicCourseSchedule> detailedSch = sch.getSubSchedule();
				AtomicCourseSchedule detSch = new AtomicCourseSchedule();
				detSch.setSchedule(sch);
				detSch.setDayOfWeek(DayOfWeekConstant.SATURDAY);
				detSch.setStartTime(java.sql.Time.valueOf( "08:05:00" ));
				detSch.setStartTime(java.sql.Time.valueOf( "14:05:00" ));
				detailedSch.add(detSch);
				schedules.add(sch);

				Long id = (Long) aSession.save(aCourse);
				t.commit();
				if (id != null) {
					session2 = sessionFactory.openSession();
					Transaction t2 = session2.beginTransaction();
					
					long id2 = id;
					aCourse = (Course) session2.get(Course.class, id2);
					t2.commit();
					System.out.println(aCourse);
					return aCourse;
				}

			} else {
				t.commit();
				return aCourse;
			}

		} catch (HibernateException e) {
			if(!t.wasCommitted()){
			t.rollback();
			}
			throw e;
		} finally {
			aSession.close();
			if(session2 != null)
			{
				session2.close();
			}
		}
		
		return null;
	}

	public Boolean saveSkeleton(Course skeleton) {
		// TODO Auto-generated method stub

		if (skeleton != null) {
			Set<SubSection> subSections = skeleton.getSubsections();

			for (SubSection sec : subSections) {
				if (sec.getCourse() == null) {
					sec.setCourse(skeleton);
				}
			}
			
			Set<CourseSchedule> courseSchs = skeleton.getCourseSchedules();

			for (CourseSchedule sec : courseSchs) {
				if (sec.getCourse() == null) {
					sec.setCourse(skeleton);
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
			
			

			Set<CustomFields> customFlds = skeleton.getCustomFields();

			for (CustomFields fld : customFlds) {
				if (fld.getCourse() == null) {
					fld.setCourse(skeleton);
				}
			}
			
			Collection<Date> updatedDates = skeleton.getUpdateDates();
			updatedDates.add(new Date());
			Session aSession = this.sessionFactory.openSession();
			Transaction t = aSession.beginTransaction();
			try {
				aSession.merge(skeleton);
				t.commit();

				return true;
			} catch (HibernateException e) {
				t.rollback();
				throw e;
			} finally {
				aSession.close();
			}
		} else
			return false;
	}
}
