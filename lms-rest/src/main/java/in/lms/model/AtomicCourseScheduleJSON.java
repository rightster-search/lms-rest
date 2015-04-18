package in.lms.model;

public class AtomicCourseScheduleJSON {

	private Long id;
	private String dayOfWeek;

	private String startTime;
	private String endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "AtomicCourseScheduleJSON [id=" + id + ", dayOfWeek="
				+ dayOfWeek + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

}
