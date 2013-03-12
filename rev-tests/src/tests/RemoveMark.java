package tests;

import helpers.DateHelper;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Student;
import services.UniversityServiceRemote;

public class RemoveMark {
	
	public static void main(String[] args) {
		UniversityServiceRemote service = null;
		try {
			service = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Module m1 = new Module(1, "Java EE");
		
		Student s1 = new Student(1, "mohamed");
		
		
		service.removeMark(s1, m1, DateHelper.toDate("15/03/13 12:00:00"));
	}

}
