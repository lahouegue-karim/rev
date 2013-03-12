package tests;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Prof;
import model.Student;
import services.UniversityServiceRemote;

public class AddSomeStudentsModulesAndProfs {
	
	public static void main(String[] args) {
		
		UniversityServiceRemote service = null;
		try {
			service = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Student s1 = new Student(1, "mohamed");
		Student s2 = new Student(2, "amina");
		Student s3 = new Student(3, "salah"); 
		
		service.create(s1);
		service.create(s2);
		service.create(s3);
		
		Module m1 = new Module(1, "Java EE");
		Module m2 = new Module(2, "Spring");
		
		service.create(m1);
		service.create(m2);
		
		Prof p1 = new Prof(1, "Karim");
		Prof p2 = new Prof(2, "Mohamed Ali");
		Prof p3 = new Prof(3, "Zoubeir");
		
		service.create(p1);
		service.create(p2);
		service.create(p3);
		
		
		
		
		
		
	}

}
