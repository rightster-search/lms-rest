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
public class AtommicWorkExp {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private WorkExperience work;

	private String companyName;
	private String startYearWithMonth;
	private String endYearWithMonth;

	@ElementCollection
	private Collection<String> designations = new ArrayList<String>();

	@ElementCollection
	private Collection<String> technologies = new ArrayList<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkExperience getWork() {
		return work;
	}

	public void setWork(WorkExperience work) {
		this.work = work;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStartYearWithMonth() {
		return startYearWithMonth;
	}

	public void setStartYearWithMonth(String startYearWithMonth) {
		this.startYearWithMonth = startYearWithMonth;
	}

	public String getEndYearWithMonth() {
		return endYearWithMonth;
	}

	public void setEndYearWithMonth(String endYearWithMonth) {
		this.endYearWithMonth = endYearWithMonth;
	}

	public Collection<String> getDesignations() {
		return designations;
	}

	public Collection<String> getTechnologies() {
		return technologies;
	}

	@Override
	public String toString() {
		return "AtommicWorkExp [id=" + id + ", companyName=" + companyName
				+ ", startYearWithMonth=" + startYearWithMonth
				+ ", endYearWithMonth=" + endYearWithMonth + ", designations="
				+ designations + ", technologies=" + technologies + "]";
	}

}
