package in.lms.request;

import java.util.List;

public class TrainerScheduleRequest {

	private Long trainerId;
	private List<Long> scheduleIds;

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public List<Long> getScheduleIds() {
		return scheduleIds;
	}

	public void setScheduleIds(List<Long> scheduleIds) {
		this.scheduleIds = scheduleIds;
	}

	@Override
	public String toString() {
		return "TrainerScheduleRequest [trainerId=" + trainerId
				+ ", scheduleIds=" + scheduleIds + "]";
	}

}
