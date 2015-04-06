package in.lms.controller;

import java.util.Set;

import in.lms.model.Course;
import in.lms.service.CourseSkeletonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.hibernate.rest.AccountRestURIConstants;
import com.spring.hibernate.rest.Bookmark;

@Controller
public class CourseSkeleton {
	
	private CourseSkeletonService courseSkeletonService;

	@Autowired(required = true)
	@Qualifier(value = "courseSkeletonServiceBean")
	public void setCourseSkeletonService(CourseSkeletonService courseSkeletonService) {
		this.courseSkeletonService = courseSkeletonService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CourseSkeleton.class);
	
	@RequestMapping(value = CourseRestURIConstants.COURSE_SKELETON, method = RequestMethod.GET)
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
	}
	
	@RequestMapping(value = CourseRestURIConstants.COURSE_SKELETON_SAVE, method = RequestMethod.POST)
	public @ResponseBody
	Boolean getCourseSkeleton(@RequestBody Course aCourse) {
		logger.info("Start getCourseSkeletonSave");
		try{
			return courseSkeletonService.saveSkeleton(aCourse);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}

}
