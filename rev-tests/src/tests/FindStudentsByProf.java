package tests;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Prof;
import model.Student;

import services.UniversityServiceRemote;

public class FindStudentsByProf {
   
	public static void main(String[] args) {
		UniversityServiceRemote service = null;
		try {
			service = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Prof p1 = new Prof(1, "Karim");
		
		
		List<Student> students1 = service.findStudentsByProf(p1);
		System.out.println("REGULAR");
		for(Student s:students1)
			System.out.println(s);
		
		
		List<Student> students2 = service.findStudentsByProfAlternative(p1);
		System.out.println("ALTERNATIVE");
		for(Student s:students2)
			System.out.println(s);
	}
   
   

}
