package in.lms.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

@Entity
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@JsonSerialize(using = DateSerializer.class)
	private Date creationDate;

	/*
	 * @ElementCollection
	 * 
	 * @JoinTable(name="USER_ADDRESS", joinColumns=@JoinColumn(name="USER_ID") )
	 * 
	 * @GenericGenerator(name="hilo-gen",strategy="hilo")
	 * 
	 * @CollectionId(columns = { @Column(name="ADDRESS_ID") }, generator =
	 * "hilo-gen", type = @Type(type="long"))
	 */
	@ElementCollection
	private Collection<Date> updateDates = new ArrayList<Date>();
	// only one image url
	/*
	 * private String bigImageURL; private String smallImageURL;
	 */
	private String imageURL;
	private Boolean isActive;
	private String description;
	private String title;
	private String courseType;// online, corporate
	private String courseCategory;// java, big data

	private int durationOfCourse;

	@ElementCollection
	private Collection<String> keyWords = new ArrayList<String>();

	@Column(name = "GROSS_PRICE", precision = 12, scale = 3)
	private BigDecimal price;// may have problem with json and hibernate mapping

	// @OneToOne
	// private Duration duration;

	// private Set<Trainers> trainers ;

	// to be added
	// course can have a video url. - upload facility
	// pre-requisities,target audience,student reviews for course.(Important)
	private String preRequisities;
	private String targetAudience;

	private String videoUrl;

	/*
	 * @ElementCollection private List<String> startDate = new
	 * ArrayList<String>();
	 */

	public String getPreRequisities() {
		return preRequisities;
	}

	public void setPreRequisities(String preRequisities) {
		this.preRequisities = preRequisities;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<SubSection> subsections = new HashSet<SubSection>();//Need to make this a list as it needs to be ordered.

	@JsonIgnore
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<CourseSchedule> courseSchedules = new HashSet<CourseSchedule>();

	@JsonIgnore
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<CustomFields> customFields = new HashSet<CustomFields>();

	public Set<CustomFields> getCustomFields() {
		return customFields;
	}

	public Collection<Date> getUpdateDates() {
		return updateDates;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * public Collection<Date> getListOfAddress() { return updateDates; }
	 * 
	 * public Duration getDuration() { return duration; }
	 */

	public Set<SubSection> getSubsections() {
		return subsections;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", creationDate=" + creationDate
				+ ", updateDates=" + updateDates + ", imageURL=" + imageURL
				+ ", isActive=" + isActive + ", description=" + description
				+ ", title=" + title + ", courseType=" + courseType
				+ ", courseCategory=" + courseCategory + ", keyWords="
				+ keyWords + ", price=" + price + ", subsections="
				+ subsections + ", courseSchedules=" + courseSchedules
				+ ", customFields=" + customFields + "]";
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/*
	 * public String getSmallImageURL() { return smallImageURL; }
	 * 
	 * public void setSmallImageURL(String smallImageURL) { this.smallImageURL =
	 * smallImageURL; }
	 */

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public int getDurationOfCourse() {
		return durationOfCourse;
	}

	public void setDurationOfCourse(int durationOfCourse) {
		this.durationOfCourse = durationOfCourse;
	}

	public Collection<String> getKeyWords() {
		return keyWords;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Set<CourseSchedule> getCourseSchedules() {
		return courseSchedules;
	}

}
