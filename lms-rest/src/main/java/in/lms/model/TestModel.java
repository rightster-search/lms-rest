package in.lms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String dayOfWeek;

	private java.sql.Time startTime;
	private java.sql.Time endTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "TimeSQLTest [id=" + id + ", dayOfWeek=" + dayOfWeek
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
