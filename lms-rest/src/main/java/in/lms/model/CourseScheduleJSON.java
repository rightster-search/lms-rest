package in.lms.model;

import java.util.HashSet;
import java.util.Set;

public class CourseScheduleJSON {

	// private Long courseId;

	private Long courseId;

	private CourseSchedule schedule;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public CourseSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CourseSchedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "CourseScheduleJSON [courseId=" + courseId + ", schedule="
				+ schedule + "]";
	}

}
