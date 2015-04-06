package in.lms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SessionWrapper {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private LoginEnvelope envelope;

	private String sessionID;
	private Boolean isValid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LoginEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(LoginEnvelope envelope) {
		this.envelope = envelope;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return "SessionWrapper [id=" + id + ", sessionID=" + sessionID
				+ ", isValid=" + isValid + "]";
	}
	
	

}
