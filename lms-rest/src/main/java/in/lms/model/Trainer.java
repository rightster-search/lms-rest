package in.lms.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Trainer {

	@Id
	@Column(name = "TRAINER_ID")
	@GeneratedValue
	private Long id;

	private String firstName;
	private String middleName;
	private String lastName;
	
	private String pictureURL;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "trainers")
	private Set<CourseSchedule> schedules = new HashSet<CourseSchedule>();

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Degree> degrees = new ArrayList<Degree>();

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "trainer", cascade = CascadeType.ALL)
	private WorkExperience workExp;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "trainer", cascade = CascadeType.ALL)
	private Address currentAddress;

	@ElementCollection
	private Collection<String> languageProficiency = new ArrayList<String>();

	private double rating;
	
	

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public Set<CourseSchedule> getSchedules() {
		return schedules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public WorkExperience getWorkExp() {
		return workExp;
	}

	public void setWorkExp(WorkExperience workExp) {
		this.workExp = workExp;
	}

	public Address getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Degree> getDegrees() {
		return degrees;
	}

	public Collection<String> getLanguageProficiency() {
		return languageProficiency;
	}

	@Override
	public String toString() {
		return "Trainer [id=" + id + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", degrees=" + degrees + ", workExp=" + workExp
				+ ", currentAddress=" + currentAddress
				+ ", languageProficiency=" + languageProficiency + ", rating="
				+ rating + "]";
	}

}
