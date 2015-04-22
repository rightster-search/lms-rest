package in.lms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.lms.common.DayOfWeekConstant;
import in.lms.common.ScheduleType;
import in.lms.model.AtomicCourseSchedule;
import in.lms.model.Course;
import in.lms.model.CourseCategory;
import in.lms.model.CourseCategoryEnvelope;
import in.lms.model.CourseSchedule;
import in.lms.model.TestModel;
import in.lms.request.TestEmailRequest;
import in.lms.service.CourseSkeletonService;
import in.lms.service.MiscellaneousService;
import in.mail.EmailUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestController {

	private MiscellaneousService miscellaneousService;

	@Autowired(required = true)
	@Qualifier(value = "miscServiceBean")
	public void setMiscellaneousService(MiscellaneousService miscService) {
		this.miscellaneousService = miscService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(TestController.class);

	@RequestMapping(value = CourseRestURIConstants.TEST_SQLTIME, method = RequestMethod.GET)
	public @ResponseBody
	CourseSchedule getCourseSkeleton() {

		CourseSchedule aSchedule = new CourseSchedule();
		Set<AtomicCourseSchedule> atmSchedules = aSchedule.getSubSchedule();

		AtomicCourseSchedule t1 = new AtomicCourseSchedule();
		t1.setDayOfWeek(DayOfWeekConstant.MONDAY);

		java.sql.Time jsqlT2 = java.sql.Time.valueOf("08:30:00");

		t1.setStartTime(jsqlT2);
		t1.setEndTime(java.sql.Time.valueOf("10:30:00"));

		atmSchedules.add(t1);

		aSchedule.setTypeOfCourse(ScheduleType.WEEKDAYS);
		return aSchedule;

	}
	
	@RequestMapping(value = CourseRestURIConstants.TEST_MODEL, method = RequestMethod.POST)
	public @ResponseBody
	Boolean saveTestModel(@RequestBody TestModel demo) {

		try {
			return miscellaneousService.addTestModel(demo);
		} catch (Exception e) {
			logger.info(e.getStackTrace().toString());
			return false;
		}
		
	}
	
	@RequestMapping(value = CourseRestURIConstants.GET_TEST_MODEL, method = RequestMethod.GET)
	public @ResponseBody
	List<TestModel> getTestModel() {
		try {
			return miscellaneousService.getTestModel();
		} catch (Exception e) {
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}

	@RequestMapping(value = CourseRestURIConstants.ADD_CATEGORY, method = RequestMethod.POST)
	public @ResponseBody
	Boolean addCourseCategories(
			@RequestBody CourseCategoryEnvelope categoryEnvelope) {
		try {
			return miscellaneousService.addCourseCategory(categoryEnvelope);
		} catch (Exception e) {
			logger.info(e.getStackTrace().toString());
			return false;
		}
	}

	@RequestMapping(value = CourseRestURIConstants.GET_COURSE_CATEGORY, method = RequestMethod.GET)
	public @ResponseBody
	List<CourseCategory> getCourseCategories() {
		try {
			return miscellaneousService.getCourseCategories();
		} catch (Exception e) {
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}

	@RequestMapping(value = CourseRestURIConstants.GET_COURSE_CATEGORY_SKELETON, method = RequestMethod.GET)
	public @ResponseBody
	CourseCategoryEnvelope getCourseCategoriesSkeleton() {

		CourseCategoryEnvelope env = new CourseCategoryEnvelope();

		List<CourseCategory> cat = env.getCategories();

		CourseCategory catTmp1 = new CourseCategory();
		catTmp1.setCategoryName("Android");
		catTmp1.setIsActive(true);

		CourseCategory catTmp2 = new CourseCategory();
		catTmp2.setCategoryName("Java");
		catTmp2.setIsActive(true);

		cat.add(catTmp1);
		cat.add(catTmp2);

		return env;

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	String upload(@RequestParam("file") MultipartFile file) {
		String name = "test11";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(
								"C:/Users/adutta/Desktop/office_work/lms/upload/"
										+ name + "-uploaded.pdf")));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded " + name + " into " + name
						+ "-uploaded !";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}
	
	@RequestMapping(value = CourseRestURIConstants.SEND_MAIL, method = RequestMethod.GET)
	public @ResponseBody
	Boolean sendMail(@RequestBody TestEmailRequest emailRequest) {
		try{
		return EmailUtil.sendEmailAWS(emailRequest.getEmailId());

		}catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}

}
