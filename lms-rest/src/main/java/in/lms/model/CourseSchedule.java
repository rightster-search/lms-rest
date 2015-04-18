package in.lms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CourseSchedule {

	@Id
	@Column(name = "COURSE_SCHEDULE_ID")
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private Course course;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<AtomicCourseSchedule> subSchedule = new HashSet<AtomicCourseSchedule>();

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "COURSE_SCHEDULE_TRAINERS", joinColumns = { @JoinColumn(name = "COURSE_SCHEDULE_ID") }, inverseJoinColumns = { @JoinColumn(name = "TRAINER_ID") })
	private Set<Trainer> trainers = new HashSet<Trainer>();

	private String typeOfCourse;// values - Weekends and Weekdays
	private int noOfWeeks;

	// there should be a start date
	// there should be a flag to say whether the courseSchedule is Active or
	// not.

	public Set<AtomicCourseSchedule> getSubSchedule() {
		return subSchedule;
	}

	public int getNoOfWeeks() {
		return noOfWeeks;
	}

	public void setNoOfWeeks(int noOfWeeks) {
		this.noOfWeeks = noOfWeeks;
	}

	public Set<Trainer> getTrainers() {
		return trainers;
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
