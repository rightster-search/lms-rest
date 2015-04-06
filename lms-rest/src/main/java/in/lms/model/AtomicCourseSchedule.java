package in.lms.model;

import java.sql.Time;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AtomicCourseSchedule {

	@JsonIgnore
	@ManyToOne
	private CourseSchedule schedule;

	@Id
	@GeneratedValue
	private Long id;

	private String dayOfWeek;

	private java.sql.Time startTime;
	private java.sql.Time endTime;

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public java.sql.Time getStartTime() {
		return startTime;
	}

	public void setStartTime(java.sql.Time startTime) {
		this.startTime = startTime;
	}

	public java.sql.Time getEndTime() {
		return endTime;
	}

	public void setEndTime(java.sql.Time endTime) {
		this.endTime = endTime;
	}

	public CourseSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CourseSchedule schedule) {
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AtomicCourseSchedule [id=" + id + ", dayOfWeek=" + dayOfWeek
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	

	/*
	 * public static void main(String[] args) { AtomicCourseSchedule tmp = new
	 * AtomicCourseSchedule(); Calendar cal = Calendar.getInstance();
	 * 
	 * // set Date portion to January 1, 1970 cal.set( cal.YEAR, 1970 );
	 * cal.set( cal.MONTH, cal.JANUARY ); cal.set( cal.DATE, 1 );
	 * cal.set(cal.HOUR_OF_DAY,18); cal.set(cal.MINUTE, 50); cal.set(cal.SECOND,
	 * 0);
	 * 
	 * 
	 * cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + ":" +
	 * cal.get(cal.SECOND) );
	 * 
	 * cal.set( cal.MILLISECOND, 0 );
	 * 
	 * java.sql.Time jsqlT = new java.sql.Time( cal.getTime().getTime() );
	 * 
	 * java.sql.Time jsqlT2 = java.sql.Time.valueOf( "18:05:00" );
	 * 
	 * System.out.println(jsqlT); }
	 */

}
