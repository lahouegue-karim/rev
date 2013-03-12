package tests;

import helpers.DateHelper;

import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Student;
import services.UniversityServiceRemote;

public class GiveSomeMarks {
	
	public static void main(String[] args) {
		UniversityServiceRemote service = null;
		try {
			service = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Module m1 = new Module(1, "Java EE");
		Module m2 = new Module(2, "Spring");
		
		Student s1 = new Student(1, "mohamed");
		Student s2 = new Student(2, "amina");
		Student s3 = new Student(3, "salah");
		
		
		service.assignMark(s1, m1, DateHelper.toDate("15/03/13 12:00:00"), "DS", 10);
		service.assignMark(s2, m1, DateHelper.toDate("25/03/13 16:00:00"), "DS", 12);
		service.assignMark(s1, m1, DateHelper.toDate("11/06/13 17:15:00"), "EXAM", 14);
	}

}
