package tests.junit;

import helpers.DateHelper;
import helpers.ScriptHelper;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.UniversityServiceRemote;
import ds.ConnectionFactory;
import model.Mark;
import model.Module;
import model.Prof;
import model.Student;

public class JPQLTests {

	private static UniversityServiceRemote service = null;

	private Student s1 = new Student(1, "mohamed");
	private Student s2 = new Student(2, "amina");
	private Student s3 = new Student(3, "salah");

	private Module m1 = new Module(1, "Java EE");
	private Module m2 = new Module(2, "Spring");

	private Prof p1 = new Prof(1, "Karim");
	private Prof p2 = new Prof(2, "Mohamed Ali");
	private Prof p3 = new Prof(3, "Zoubeir");
	
	private Mark mark1 = new Mark(s1, m1, DateHelper.toDate("15/03/13 12:00:00"), "DS", 10);
	private Mark mark2 = new Mark(s2, m1, DateHelper.toDate("25/03/13 16:00:00"), "DS", 12);
	private Mark mark3 = new Mark(s1, m1, DateHelper.toDate("11/06/13 17:15:00"), "EXAM", 14);

	@BeforeClass
	public static void initUniversityService() {
		service = null;
		try {
			service = (UniversityServiceRemote) new InitialContext()
					.lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void itShouldFindStudentsByProf() {
		List<Student> students = service.findStudentsByProf(p1);
		verifyListcontains(students,s1,s2);
		
	}
	
	@Test
	public void itShouldFindStudentsByProfAlternative() {
		List<Student> students = service.findStudentsByProfAlternative(p1);
		verifyListcontains(students,s1,s2);
		
	}
	
	@Test
	public void itShouldFindProfsByStudent() {
		List<Prof> profs = service.findProfsByStudent(s1);
		verifyListcontains(profs,p1,p2);
		
	}
	
	@Test
	public void itShouldFindProfsByStudentAlternative() {
		List<Prof> profs = service.findProfsByStudentAlternative(s1);
		verifyListcontains(profs,p1,p2);
		
	}
	
	


	


	private static ScriptHelper scriptHelper = null;
	private static Connection connection = null;

	@BeforeClass
	public static void initTestUtilities() {
		scriptHelper = ScriptHelper.getInstance();
		connection = ConnectionFactory.getInstance().createConnection(
				"./conf/app-ds.xml");
	}

	@After
	public void tearDown() {
		scriptHelper.executeScript("DELETE FROM T_MARK", connection);
		scriptHelper.executeScript("DELETE FROM T_STUDENT", connection);
		scriptHelper.executeScript("DELETE FROM T_PROF", connection);
		scriptHelper.executeScript("DELETE FROM T_MODULE", connection);
	}

	@Before
	public void setUp() {
		
		scriptHelper.executeScript("INSERT INTO T_STUDENT (ID,NAME) VALUES ('" + s1.getId() + "', '" + s1.getName() + "'),('" + s2.getId() + "', '" + s2.getName() + "'), ('" + s3.getId() + "', '" + s3.getName() + "');", connection);
		scriptHelper.executeScript("INSERT INTO T_MODULE (ID,NAME) VALUES ('" + m1.getId() + "', '" + m1.getName() + "'),('" + m2.getId() + "', '" + m2.getName() + "');", connection);
		scriptHelper.executeScript("INSERT INTO T_PROF (ID,NAME,MODULE_FK) VALUES ('" + p1.getId() + "', '" + p1.getName() + "', '" + m1.getId() + "'),('" + p2.getId() + "', '" + p2.getName() + "', '" + m1.getId() + "'), ('" + p3.getId() + "', '" + p3.getName() + "','" + m2.getId() + "');", connection);
		scriptHelper.executeScript("INSERT INTO T_MARK(STUDENT_FK, MODULE_FK, DATE, TYPE, MARK) VALUES ('"+mark1.getStudent().getId()+"','"+mark1.getModule().getId()+"','"+new Timestamp(mark1.getPk().getDate().getTime())+"','"+mark1.getType()+"','"+mark1.getMark()+"'), ('"+mark2.getStudent().getId()+"','"+mark2.getModule().getId()+"','"+new Timestamp(mark2.getPk().getDate().getTime())+"','"+mark2.getType()+"','"+mark2.getMark()+"'), ('"+mark3.getStudent().getId()+"','"+mark3.getModule().getId()+"','"+new Timestamp(mark3.getPk().getDate().getTime())+"','"+mark3.getType()+"','"+mark3.getMark()+"');", connection);
	}
	private void verifyListcontains(List<Student> students, Student... studs) {
		assertEquals(studs.length, students.size());
		for(Student stud:studs){
			boolean found = false;
			for(Student student:students){
				if((stud.getId() == student.getId()) 
						&& (stud.getName().equals(student.getName()))){
					found = true;
				}
			}
			assertTrue(found);
		}
		
	}
	
	private void verifyListcontains(List<Prof> profs, Prof... ps) {
		assertEquals(ps.length, profs.size());
		for(Prof p:ps){
			boolean found = false;
			for(Prof prof:profs){
				if((p.getId() == prof.getId()) 
						&& (p.getName().equals(prof.getName()))){
					found = true;
				}
			}
			assertTrue(found);
		}
		
		
		
	}

}
