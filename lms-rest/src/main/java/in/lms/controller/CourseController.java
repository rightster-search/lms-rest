package in.lms.controller;

import java.util.List;
import java.util.Set;

import in.lms.model.Course;
import in.lms.service.CourseSkeletonService;
import in.lms.service.CoursesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.hibernate.rest.Account;
import com.spring.hibernate.rest.AccountRestURIConstants;
import com.spring.hibernate.rest.Bookmark;

@Controller
public class CourseController {
	
	private CoursesService courseService;

	@Autowired(required = true)
	@Qualifier(value = "coursesServiceBean")
	public void setAccountService(CoursesService courseService) {
		this.courseService = courseService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CourseController.class);
	
	/*@RequestMapping(value = CourseRestURIConstants.COURSE_SKELETON, method = RequestMethod.GET)
	public @ResponseBody
	Course getCourseSkeleton() {
		logger.info("Start getCourseSkeleton");
		try{
			return courseSkeletonService.returnSkeleton();
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}*/
	
	@RequestMapping(value = CourseRestURIConstants.COURSE_SAVE, method = RequestMethod.POST)
	public @ResponseBody
	Boolean saveCourse(@RequestBody Course aCourse) {
		logger.info("Start getCourseSkeletonSave");
		try{
			return courseService.saveACourse(aCourse);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}
	
	@RequestMapping(value = CourseRestURIConstants.UPDATE_COURSE, method = RequestMethod.POST)
	public @ResponseBody
	Boolean updateCourse(@RequestBody Course course) {
		logger.info("Start updateCourse.");
		try{
			courseService.upDateACourse(course);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return false;
		}
		
		return true;
	}
	
	@RequestMapping(value = CourseRestURIConstants.GET_COURSE, method = RequestMethod.GET)
	public @ResponseBody
	Course getCourseById(@PathVariable("id") long courseId) {
		logger.info("Start getCourse. ID=" + courseId);

		return courseService.getCourseByID(courseId);
	}

	@RequestMapping(value = CourseRestURIConstants.GET_ALL_COURSE, method = RequestMethod.GET)
	public @ResponseBody
	List<Course> getAllCourses() {
		logger.info("Start getAllCourses.");
		List<Course> courses = courseService.getAllCourses();
		
		return courses ;
	}

}
