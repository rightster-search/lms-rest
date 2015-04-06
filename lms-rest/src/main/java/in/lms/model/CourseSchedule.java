package in.lms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CourseSchedule {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private Course course;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<AtomicCourseSchedule> subSchedule = new HashSet<AtomicCourseSchedule>();

	private String typeOfCourse;// values - Weekends and Weekdays

	public Set<AtomicCourseSchedule> getSubSchedule() {
		return subSchedule;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeOfCourse() {
		return typeOfCourse;
	}

	public void setTypeOfCourse(String typeOfCourse) {
		this.typeOfCourse = typeOfCourse;
	}

	@Override
	public String toString() {
		return "CourseSchedule [id=" + id + ", subSchedule=" + subSchedule
				+ ", typeOfCourse=" + typeOfCourse + "]";
	}

}
