package in.lms.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SubSection {
	
	@JsonIgnore
	@ManyToOne
	private Course course;

	@Id
	@GeneratedValue
	private Long id;
	
	private String heading;
	
	@ElementCollection
	private Collection<String> bulletPoints = new ArrayList<String>();
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public Collection<String> getBulletPoints() {
		return bulletPoints;
	}
	
	@Override
	public String toString() {
		return "SubSection [id=" + id + ", heading=" + heading
				+ ", bulletPoints=" + bulletPoints + "]";
	}

	
}
