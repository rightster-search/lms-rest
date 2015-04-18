package in.lms.controller;

import java.util.List;

import in.lms.model.Address;
import in.lms.model.AtommicWorkExp;
import in.lms.model.Course;
import in.lms.model.Degree;
import in.lms.model.Trainer;
import in.lms.model.WorkExperience;
import in.lms.request.TrainerScheduleRequest;
import in.lms.service.CoursesService;
import in.lms.service.TrainerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrainerController {
	
	private TrainerService trainerService;

	@Autowired(required = true)
	@Qualifier(value = "trainerServiceBean")
	public void setCourseService(TrainerService trainerService) {
		this.trainerService = trainerService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(TrainerController.class);
	
	@RequestMapping(value = CourseRestURIConstants.TRAINER_SAVE, method = RequestMethod.POST)
	public @ResponseBody
	Boolean saveTrainer(@RequestBody Trainer aTrainer) {
		logger.info("Start saveTrainer");
		try{
			return trainerService.saveATrainer(aTrainer);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}
	
	@RequestMapping(value = CourseRestURIConstants.ATTACH_TRAINER_TO_SCHEDULES, method = RequestMethod.POST)
	public @ResponseBody
	Boolean attachTrainerToSchedule(@RequestBody TrainerScheduleRequest aRequest) {
		logger.info("Start saveTrainerWithSchedule");
		try{
			return trainerService.attachTrainerToSchedule(aRequest);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}
	
	@RequestMapping(value = CourseRestURIConstants.GET_ALL_TRAINER_FOR_A_SCHEDULE, method = RequestMethod.GET)
	public @ResponseBody
	List<Trainer> getTrainersPerSchedule(@PathVariable("id") long scheduleId) {
		logger.info("Start getTrainerPerSchedule");
		try{
			return trainerService.getTrainersPerSchedule(scheduleId);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}
	
	@RequestMapping(value = CourseRestURIConstants.GET_TRAINER_FOR_A_ID, method = RequestMethod.GET)
	public @ResponseBody
	Trainer getTrainerById(@PathVariable("id") long trainerId) {
		logger.info("Start getTrainerById");
		try{
			return trainerService.getTrainerById(trainerId);
		}catch(Exception e)
		{
			logger.info(e.getStackTrace().toString());
			return null;
		}
	}
	
	@RequestMapping(value = CourseRestURIConstants.TRAINER_SKELETON, method = RequestMethod.GET)
	public @ResponseBody
	Trainer getTrainerSkeleton() {
		Trainer trainer = new Trainer();
		trainer.setFirstName("Arka");
		trainer.setLastName("Dutta");
		trainer.setPictureURL("http://aws.com/href?user=123456");
		trainer.setRating(4.5);
		Address addr = new Address();
		addr.setCity("Bangalore");
		addr.setCountry("India");
		addr.setLandMark("ABC");
		addr.setPincode("560016");
		addr.setState("karnataka");
		addr.setLine1("asdsdsd");
		
		trainer.setCurrentAddress(addr);
		
		WorkExperience ex = new WorkExperience();
		
		AtommicWorkExp a = new AtommicWorkExp();
		a.setCompanyName("Verse");
		a.setEndYearWithMonth("July,2011");
		a.setStartYearWithMonth("April,2010");
		a.getDesignations().add("Software Developer");
		a.getTechnologies().add("JAVA");
		a.getTechnologies().add("C++");
		
		ex.getExps().add(a);
		
		trainer.setWorkExp(ex);
		
		Degree dg = new Degree();
		dg.setCollegeName("IIITA");
		dg.setDegree("M.Tech");
		dg.setEndYear("2011");
		dg.setStartYear("2009");
		trainer.getDegrees().add(dg);
		return trainer;
		
	}

}
