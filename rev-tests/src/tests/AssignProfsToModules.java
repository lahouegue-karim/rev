package tests;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Prof;
import services.UniversityServiceRemote;

public class AssignProfsToModules {
	
	public static void main(String[] args) {
		UniversityServiceRemote service = null;
		try {
			service = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		Module m1 = new Module(1, "Java EE");
		Module m2 = new Module(2, "Spring");
		
		Prof p1 = new Prof(1, "Karim");
		Prof p2 = new Prof(2, "Mohamed Ali");
		Prof p3 = new Prof(3, "Zoubeir");
		
		List<Prof> m1Profs = new ArrayList<Prof>();
		m1Profs.add(p1); 
		m1Profs.add(p2);
		List<Prof> m2Profs = new ArrayList<Prof>();
		m2Profs.add(p3); 
		
		service.assignProfToModule(m1Profs, m1);
		service.assignProfToModule(m2Profs, m2);
	}

}
