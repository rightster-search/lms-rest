package in.lms.service;

import java.util.List;

import in.lms.model.CourseCategory;
import in.lms.model.CourseCategoryEnvelope;
import in.lms.model.LoginEnvelope;
import in.lms.model.SessionWrapper;
import in.lms.model.TestModel;

public interface MiscellaneousService {
	
	public Boolean addCourseCategory(CourseCategoryEnvelope envelope);
	
	public List<CourseCategory> getCourseCategories();
	
	public Boolean registerUser(String username, String password);
	
	public Boolean saveLoginEnvelope(LoginEnvelope envelope);
	
	public Boolean updateLoginEnvelope(LoginEnvelope envelope);
	
	public LoginEnvelope getLoginEnvelope(String username, String role);
	
	public SessionWrapper getSessionWrapper(String sessionId);
	
	public Boolean updateSessionWrapper(SessionWrapper session);
	
	public LoginEnvelope getLoginEnvelopeFromUID(String uid);
	
	public Boolean addTestModel(TestModel demo);
	
	public List<TestModel> getTestModel();

}
