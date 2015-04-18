package in.lms.service;

import java.util.List;

import in.lms.model.Trainer;
import in.lms.request.TrainerScheduleRequest;

public interface TrainerService {
	
	public Boolean saveATrainer(Trainer aTrainer);//implemented
	
	public Boolean attachTrainerToSchedule(TrainerScheduleRequest aRequest);//implemented
	
	public List<Trainer> getTrainersPerSchedule(Long scheduleId);//implemented
	
	public Trainer getTrainerById(Long trainerId);//implemented

}
