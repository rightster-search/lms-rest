package in.lms.service;

import java.util.List;

import in.lms.model.Course;

public interface CoursesService {
	
	public Boolean saveACourse(Course aCourse);//implemented
	
	public Boolean upDateACourse(Course aCourse);//implemented
	
	public Boolean deleteACourse(Course aCourse);
	
	public Course getCourseByID(Long id);//implemented
	
	public List<Course> getAllCourses();//implemented

}
