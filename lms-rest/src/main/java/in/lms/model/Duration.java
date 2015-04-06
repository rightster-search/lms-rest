package in.lms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class Duration {

	private Date startDate;
	private Date endDate ;
	private String hoursPerSession;
	
	@Id
	@GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getHoursPerSession() {
		return hoursPerSession;
	}
	public void setHoursPerSession(String hoursPerSession) {
		this.hoursPerSession = hoursPerSession;
	}
	
	
}
