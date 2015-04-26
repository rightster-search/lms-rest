package in.lms.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserSequence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myGen")
	@SequenceGenerator(name = "myGen", sequenceName = "MY_SEQUENCE")
	@Column(name = "ID", insertable = false)
	private Long id;
	// private String name;

	@JsonIgnore
	@OneToOne
	private LoginEnvelope user;
	
	/*@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Student student;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LoginEnvelope getUser() {
		return user;
	}

	public void setUser(LoginEnvelope user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserSequence [id=" + id + "]";
	}

}
