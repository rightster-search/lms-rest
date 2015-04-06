package in.lms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PasswordWrapper {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private LoginEnvelope envelope;

	private String password;
	private Boolean isSystemGenerated;

	private String creationDate;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsSystemGenerated() {
		return isSystemGenerated;
	}

	public void setIsSystemGenerated(Boolean isSystemGenerated) {
		this.isSystemGenerated = isSystemGenerated;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "PasswordWrapper [password=" + password + ", isSystemGenerated="
				+ isSystemGenerated + ", creationDate=" + creationDate + "]";
	}

}
