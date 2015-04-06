package in.lms.model;

import java.util.ArrayList;
import java.util.List;

public class CourseCategoryEnvelope {

	private List<CourseCategory> categories = new ArrayList<CourseCategory>();

	public List<CourseCategory> getCategories() {
		return categories;
	}

	@Override
	public String toString() {
		return "CourseCategoryEnvelope [categories=" + categories + "]";
	}

}
