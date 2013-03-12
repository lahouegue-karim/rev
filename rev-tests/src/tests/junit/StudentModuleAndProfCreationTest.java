package tests.junit;

import static junit.framework.Assert.assertEquals;
import helpers.ScriptHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Prof;
import model.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.UniversityServiceRemote;
import ds.ConnectionFactory;

public class StudentModuleAndProfCreationTest {
	
	
	private static UniversityServiceRemote service = null;
	
	private Student s1 = new Student(1, "mohamed");
	private Module m1 = new Module(1, "Java EE");
	private Prof p1 = new Prof(1, "Karim");
	
	

	@BeforeClass
	public static void initUniversityService(){
		service  = null;
		try {
			service  = (UniversityServiceRemote)new InitialContext().lookup("ejb:/rev-ejb/UniversityService!services.UniversityServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void itShouldCreateStudent(){
		
		service.create(s1);
		verifyStudentWasCreated(s1);
	}
	
	@Test
	public void itShouldCreateModule(){
		
		service.create(m1);
		verifyModuleWasCreated(m1);
	}
	
	@Test
	public void itShouldCreateProf(){
		service.create(p1);
		verifyProfWasCreated(p1);
	}
	
	


	




	private static ScriptHelper scriptHelper = null;
	private static Connection connection = null;
	
	@BeforeClass
	public static void initTestUtilities(){
		scriptHelper = ScriptHelper.getInstance();
		connection = ConnectionFactory.getInstance().createConnection("./conf/app-ds.xml");
	}
	@After
	public void tearDown(){
		scriptHelper.executeScript("DELETE FROM T_STUDENT", connection);
		scriptHelper.executeScript("DELETE FROM T_MODULE", connection);
		scriptHelper.executeScript("DELETE FROM T_PROF", connection);
	}
	@Before
	public void setUp(){
		
	}
	private void verifyStudentWasCreated(Student student) {
		Student found = null;
		String sql = "SELECT * FROM T_STUDENT WHERE ID=?"; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, student.getId());
			resultSet = preparedStatement.executeQuery(); 
			
			
			System.out.println(sql);
			if (resultSet.next()) {
				found = new Student(resultSet.getInt("ID"), resultSet.getString("NAME"));
			}
			
			
		} catch (SQLException ex) {
			Logger.getLogger(StudentModuleAndProfCreationTest.class.getName()).log(Level.SEVERE, "find student failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(StudentModuleAndProfCreationTest.class.getName()).log(Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(student.getId(), found.getId());
		assertEquals(student.getName(), found.getName());
	}
	private void verifyModuleWasCreated(Module module) {
		Module found = null;
		String sql = "SELECT * FROM T_MODULE WHERE ID=?"; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, module.getId());
			resultSet = preparedStatement.executeQuery(); 
			
			
			System.out.println(sql);
			if (resultSet.next()) {
				found = new Module(resultSet.getInt("ID"), resultSet.getString("NAME"));
			}
			
			
		} catch (SQLException ex) {
			Logger.getLogger(StudentModuleAndProfCreationTest.class.getName()).log(Level.SEVERE, "find module failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(StudentModuleAndProfCreationTest.class.getName()).log(Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(module.getId(), found.getId());
		assertEquals(module.getName(), found.getName());
	}
	private void verifyProfWasCreated(Prof prof) {
		Prof found = null;
		String sql = "SELECT * FROM T_PROF WHERE ID=?"; 
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, prof.getId());
			resultSet = preparedStatement.executeQuery(); 
			
			
			System.out.println(sql);
			if (resultSet.next()) {
				found = new Prof(resultSet.getInt("ID"), resultSet.getString("NAME"));
			}
			
			
		} catch (SQLException ex) {
			Logger.getLogger(StudentModuleAndProfCreationTest.class.getName()).log(Level.SEVERE, "find module failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(StudentModuleAndProfCreationTest.class.getName()).log(Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(prof.getId(), found.getId());
		assertEquals(prof.getName(), found.getName());
		
	}
	
}
