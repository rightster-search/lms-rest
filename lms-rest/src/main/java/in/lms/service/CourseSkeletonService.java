package in.lms.service;

import in.lms.model.Course;

public interface CourseSkeletonService {
	
	public Course returnSkeleton();
	
	public Boolean saveSkeleton(Course skeleton);

}
