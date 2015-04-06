package com.lms.spring.client;

import in.lms.controller.CourseRestURIConstants;
import in.lms.model.Course;
import in.lms.model.CustomFields;
import in.lms.model.DataTypeConstants;
import in.lms.model.SubSection;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
 




import java.util.Set;

import org.springframework.web.client.RestTemplate;

import com.lms.spring.controller.EmpRestURIConstants;
import com.lms.spring.model.Employee;
import com.spring.hibernate.rest.Account;
import com.spring.hibernate.rest.AccountRestURIConstants;
import com.spring.hibernate.rest.Bookmark;

public class TestSpringRestExample {
	
	public static final String SERVER_URI = "http://localhost:8080/lms-rest-0.1";
    
    public static void main(String args[]){
         
        /*testGetDummyEmployee();
        System.out.println("*****");
        testCreateEmployee();
        System.out.println("*****");
        testGetEmployee();
        System.out.println("*****");
        testGetAllEmployee();*/
    	//testCreateAccount();
    	/*Long id = testGetAllAccount();
    	if(id != null)
    	{
    		Account acc = testGetAccount(id);
    		acc.setUsername("Arka Dutta Hero");
    		
    		Set<Bookmark> bks = acc.getBookmarks();
    		Bookmark bk = new Bookmark(acc, "http://www.coursera.com", "Massive Online Open Courses");
    		bks.add(bk);
    		
    		testUpdateAccount(acc);
    	}*/
    	//testCreateCourse();
    	testgetCourseById(5);
    	
    }
    
    private static Course testgetCourseById(long id) {
    	RestTemplate restTemplate = new RestTemplate();
        Course course = restTemplate.getForObject(SERVER_URI+"/rest/course/data/"+id, Course.class);
        return course;
    }
    
    private static void testCreateSkeleton() {
        RestTemplate restTemplate = new RestTemplate();
        
        //
        Course course = restTemplate.getForObject(SERVER_URI+CourseRestURIConstants.COURSE_SKELETON,  Course.class);
        System.out.println(course);
        
        Course course2 = new Course();
        course2.setId(course.getId());
        course2.setCreationDate(course.getCreationDate());
        course2.setDescription(course.getDescription());
        course2.setIsActive(course.getIsActive());
        //course2.setSourceURL(course.getSourceURL());
        
        Collection<Date> updatesDates = course.getUpdateDates();
        Collection<Date> updatesDates2 = course2.getUpdateDates();
        
        for(Date aDt : updatesDates)
        {
        	updatesDates2.add(aDt);
        }
        
        Set<SubSection> sc1 = course.getSubsections();
        Set<SubSection> sc2 = course2.getSubsections();
        
        for(SubSection a : sc1)
        {
        	sc2.add(a);
        }
        
        Set<CustomFields> cfld3= course.getCustomFields();
        Set<CustomFields> cfld4 = course2.getCustomFields();
        
        for(CustomFields a: cfld3)
        {
        	cfld4.add(a);
        }
        
        CustomFields cfld1 =  new CustomFields();
        cfld1.setCourse(course);
        cfld1.setDataType(DataTypeConstants.DATE);
        cfld1.setIsActive(true);
        cfld1.setValue("");
        
        CustomFields cfld2 =  new CustomFields();
        cfld2.setCourse(course);
        cfld2.setDataType(DataTypeConstants.INTEGER);
        cfld2.setIsActive(true);
        cfld2.setValue("");
        
        course2.getCustomFields().add(cfld1);
        course2.getCustomFields().add(cfld2);
        
        Boolean bool = restTemplate.postForObject(SERVER_URI+CourseRestURIConstants.COURSE_SKELETON_SAVE, course2, Boolean.class);
        System.out.println(bool);
    }
    
    private static void testCreateCourse() {
        RestTemplate restTemplate = new RestTemplate();
        
        Course course2 = new Course();
        course2.setDescription("First Course Uploaded");
        //course2.setSourceURL("http://course1");
        
        Set<SubSection> sc2 = course2.getSubsections();
        SubSection s1 = new SubSection();
        s1.setCourse(course2);
        s1.setHeading("Java Core");
        Collection<String> bpts = s1.getBulletPoints();
        bpts.add("Java Beginners");
        bpts.add("Java Loop constructs");
        bpts.add("Java Threading");
        sc2.add(s1);
        
        CustomFields cfld1 =  new CustomFields();
        cfld1.setCourse(course2);
        cfld1.setDataType(DataTypeConstants.DATE);
        cfld1.setIsActive(true);
        cfld1.setValue("2015-02-08");
        
        CustomFields cfld2 =  new CustomFields();
        cfld2.setCourse(course2);
        cfld2.setDataType(DataTypeConstants.INTEGER);
        cfld2.setIsActive(true);
        cfld2.setValue("10");
        
        course2.getCustomFields().add(cfld1);
        course2.getCustomFields().add(cfld2);
        
        Boolean bool = restTemplate.postForObject(SERVER_URI+CourseRestURIConstants.COURSE_SAVE, course2, Boolean.class);
        System.out.println(bool);
    }
 
    private static Long testGetAllEmployee() {
        RestTemplate restTemplate = new RestTemplate();
        //we can't get List<Employee> because JSON convertor doesn't know the type of
        //object in the list and hence convert it to default JSON object type LinkedHashMap
        List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.GET_ALL_EMP, List.class);
        System.out.println(emps.size());
        Long id  = null;
        for(LinkedHashMap map : emps){
            System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));
            id = (Long) map.get("id");
        }
        
        return id;
    }
 
    private static void testUpdateAccount(Account acc) {
        RestTemplate restTemplate = new RestTemplate();
        
        //
        restTemplate.postForObject(SERVER_URI+AccountRestURIConstants.UPDATE_ACCOUNT, acc, Boolean.class);
        
    }
 
   
    
    private static Account testGetAccount(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        Account acc = restTemplate.getForObject(SERVER_URI+"/rest/account/"+id.toString(), Account.class);
        return acc;
        //printEmpData(emp);
    }
     
    public static void printEmpData(Employee emp){
        System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
    }
    
    private static Long testGetAllAccount() {
        RestTemplate restTemplate = new RestTemplate();
       
        
        //we can't get List<Account> because JSON convertor doesn't know the type of
        //object in the list and hence convert it to default JSON object type LinkedHashMap
        List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI+AccountRestURIConstants.GET_ALL_ACCOUNT, List.class);
        System.out.println(emps.size());
        Long id = null;
        for(LinkedHashMap map : emps){
        	System.out.println("Map value --- "+map);
            System.out.println("ID="+map.get("id")+",UserName="+map.get("username"));
            id = Long.parseLong(map.get("id").toString());
        }
        
        return id;
    }
 
    private static void testCreateAccount() {
        RestTemplate restTemplate = new RestTemplate();
        Account account = new Account("Shalmoli Dutta", "123456");
        Bookmark bookMark1 = new Bookmark(account, "http://twitter.com", "tweet account");
        Bookmark bookMark2 = new Bookmark(account, "http://facebook.com", "Social Networking ");
        Set<Bookmark> bookMarks = account.getBookmarks();
        bookMarks.add(bookMark1);
        bookMarks.add(bookMark2);
        boolean response = restTemplate.postForObject(SERVER_URI+AccountRestURIConstants.CREATE_ACC, account, Boolean.class);
        System.out.println("Account creation successfull");
        
        account = null;
        bookMarks = null;
        
        account = new Account("Kulbeer Singh", "123456");
        bookMark1 = new Bookmark(account, "http://gmail.com", "mail account");
        bookMark2 = new Bookmark(account, "http://facebook.com", "Social Networking ");
        bookMarks = account.getBookmarks();
        bookMarks.add(bookMark1);
        bookMarks.add(bookMark2);
        response = restTemplate.postForObject(SERVER_URI+AccountRestURIConstants.CREATE_ACC, account, Boolean.class);
        System.out.println("Account creation successfull");
        
    }

}
