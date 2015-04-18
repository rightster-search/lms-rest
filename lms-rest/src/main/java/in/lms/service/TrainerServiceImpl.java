package in.lms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import in.lms.model.Address;
import in.lms.model.AtommicWorkExp;
import in.lms.model.CourseSchedule;
import in.lms.model.Degree;
import in.lms.model.SubSection;
import in.lms.model.Trainer;
import in.lms.model.WorkExperience;
import in.lms.request.TrainerScheduleRequest;

public class TrainerServiceImpl implements TrainerService{
	
	private SessionFactory sessionFactory;
	private static final String CLASSNAME = "in.lms.model.Course";

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Boolean saveATrainer(Trainer aTrainer) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();

		Address addr = aTrainer.getCurrentAddress();
		if(addr!= null && addr.getTrainer() == null)
		{
			addr.setTrainer(aTrainer);
		}
		
		List<Degree> degrees = aTrainer.getDegrees();
		for(Degree aDgr : degrees)
		{
			if(aDgr.getTrainer() == null)
			{
				aDgr.setTrainer(aTrainer);
			}
		}

		WorkExperience workEx = aTrainer.getWorkExp();
		if(workEx!= null && workEx.getTrainer()== null)
		{
			workEx.setTrainer(aTrainer);
			List<AtommicWorkExp>  list = workEx.getExps();
			for(AtommicWorkExp element : list)
			{
				if(element.getWork() == null)
				{
					element.setWork(workEx);
				}
			}
			
		}
		aSession.save(aTrainer);
		aSession.getTransaction().commit();
		aSession.close();

		return true;
	}

	public Boolean attachTrainerToSchedule(TrainerScheduleRequest aRequest) {
		// TODO Auto-generated method stub
		List<Long> scheduleIds = aRequest.getScheduleIds();
		Long trainerId = aRequest.getTrainerId();
		
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();
		Trainer trainer = (Trainer)aSession.get(Trainer.class,trainerId);
		for(Long schId : scheduleIds)
		{
			CourseSchedule aTmp = (CourseSchedule)aSession.get(CourseSchedule.class, schId);
			aTmp.getTrainers().add(trainer);
			aSession.merge(aTmp);
		}
		
		aSession.getTransaction().commit();
		aSession.close();
		return true;
	}

	public List<Trainer> getTrainersPerSchedule(Long scheduleId) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();
		
		CourseSchedule aTmp = (CourseSchedule)aSession.get(CourseSchedule.class, scheduleId);
		Set<Trainer> trainers = aTmp.getTrainers();
		
		List<Trainer> tr = new ArrayList<Trainer>();
		for(Trainer tmp : trainers)
		{
			System.out.println(tmp);
		}
		tr.addAll(trainers);
		
		return tr;
	}

	public Trainer getTrainerById(Long trainerId) {
		Session aSession = this.sessionFactory.openSession();
		aSession.beginTransaction();
		
		Trainer tr = (Trainer)aSession.get(Trainer.class, trainerId);
		System.out.println(tr);
		return tr;
	}

}
