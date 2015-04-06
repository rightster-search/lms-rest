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
    
    public static final String TEST_SQLTIME = "/rest/test/sqltime";
    public static final String ADD_CATEGORY = "/rest/miscellaneous/addCategory";
    public static final String GET_COURSE_CATEGORY = "/rest/miscellaneous/getCourseCategory";
    public static final String GET_COURSE_CATEGORY_SKELETON = "/rest/miscellaneous/getCategory/skeleton";
    
    public static final String GENERATE_PWD = "/rest/login/generatePassword";
    public static final String LOGIN = "/rest/login/enter";
    public static final String SAVEPWD = "/rest/login/savepwd";
    public static final String LOGOUT = "/rest/logout";

}
