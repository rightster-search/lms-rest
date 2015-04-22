package in.lms.controller;

public class CourseRestURIConstants {
	
	public static final String DUMMY_ACC = "/rest/account/dummy";
    public static final String GET_ACCOUNT = "/rest/account/{id}";
    public static final String GET_ALL_ACCOUNT = "/rest/accs";
    public static final String CREATE_ACC = "/rest/acc/create";
    public static final String DELETE_EMP = "/rest/emp/delete/{id}";
    public static final String UPDATE_ACCOUNT = "/rest/acc/update";
    
    public static final String COURSE_SKELETON = "/rest/course/skeleton";
    public static final String COURSE_SKELETON_SAVE = "/rest/course/template/save";
    public static final String COURSE_SAVE = "/rest/course/data/save";
    public static final String UPDATE_COURSE = "/rest/course/data/update";
    public static final String GET_COURSE = "/rest/course/data/{id}";
    public static final String GET_ALL_COURSE = "/rest/course/data/all";
    
    public static final String ADD_SCHEDULE_TO_COURSE = "/rest/course/data/schedule/save";
    public static final String GET_ALL_SCHEDULE_FOR_A_COURSE = "/rest/course/data/schedule/all/{id}";
    public static final String GET_SCHEDULE_BY_ID = "/rest/schedule/data/{id}";
    
    public static final String TRAINER_SAVE = "/rest/trainer/data/save";
    public static final String TRAINER_SKELETON = "/rest/trainer/skeleton";
    public static final String ATTACH_TRAINER_TO_SCHEDULES = "/rest/trainer/attach/schedule";
    public static final String GET_ALL_TRAINER_FOR_A_SCHEDULE = "/rest/trainer/data/perschedule/all/{id}";
    public static final String GET_TRAINER_FOR_A_ID = "/rest/trainer/data/{id}";
    
    
    public static final String TEST_SQLTIME = "/rest/test/sqltime";
    public static final String TEST_MODEL = "/rest/test/demo";
    public static final String GET_TEST_MODEL = "/rest/get/test/demo";
    public static final String ADD_CATEGORY = "/rest/miscellaneous/addCategory";
    public static final String GET_COURSE_CATEGORY = "/rest/miscellaneous/getCourseCategory";
    public static final String GET_COURSE_CATEGORY_SKELETON = "/rest/miscellaneous/getCategory/skeleton";
    
    public static final String GENERATE_PWD = "/rest/login/generatePassword";
    public static final String LOGIN = "/rest/login/enter";
    public static final String SAVEPWD = "/rest/login/savepwd";
    public static final String LOGOUT = "/rest/logout";
    
    public static final String SEND_MAIL = "/send/mail";

}
