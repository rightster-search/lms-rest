package in.lms.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class LoginEnvelope {

	/*
	 * @Id
	 * 
	 * @GeneratedValue private Long id;
	 */

	@Id
	private UserRole id;

	private String userSessionId;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private UserSequence sequenceNo;

	@OneToMany(mappedBy = "envelope", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<PasswordWrapper> passwordList = new ArrayList<PasswordWrapper>();

	@OneToMany(mappedBy = "envelope", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<SessionWrapper> sessionList = new ArrayList<SessionWrapper>();

	public UserRole getId() {
		return id;
	}

	public void setId(UserRole id) {
		this.id = id;
	}

	public UserSequence getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(UserSequence sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}

	public List<PasswordWrapper> getPasswordList() {
		return passwordList;
	}

	public List<SessionWrapper> getSessionList() {
		return sessionList;
	}

	@Override
	public String toString() {
		return "LoginEnvelope [id=" + id + ", userSessionId=" + userSessionId
				+ ", sequenceNo=" + sequenceNo + ", passwordList="
				+ passwordList + ", sessionList=" + sessionList + "]";
	}

}
