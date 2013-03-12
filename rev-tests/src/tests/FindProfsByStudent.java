package tests;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Prof;
import model.Student;

import services.UniversityServiceRemote;

public class FindProfsByStudent {
	
	public static void main(String[] args) {
		UniversityServiceRemote service = null;
		try {
			service = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Student s1 = new Student(1, "mohamed");
		
		List<Prof> profs1 = service.findProfsByStudent(s1);
		System.out.println("REGULAR");
		for(Prof p:profs1){
			System.out.println(p);
		}
		
		List<Prof> profs2 = service.findProfsByStudentAlternative(s1);
		System.out.println("ALTERNATIVE");
		for(Prof p:profs2){
			System.out.println(p);
		}
	}

}
