package in.lms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WorkExperience {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@OneToOne
	private Trainer trainer;

	@OneToMany(mappedBy = "work", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AtommicWorkExp> exps = new ArrayList<AtommicWorkExp>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AtommicWorkExp> getExps() {
		return exps;
	}

	@Override
	public String toString() {
		return "WorkExperience [id=" + id + ", exps=" + exps + "]";
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

}
