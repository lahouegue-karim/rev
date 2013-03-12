package tests.junit;

import static junit.framework.Assert.assertEquals;
import helpers.ScriptHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Prof;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.UniversityServiceRemote;
import ds.ConnectionFactory;

public class AssignProfToModuleTest {

	private static UniversityServiceRemote service = null;

	private Module m1 = new Module(1, "Java EE");;
	private Prof p1 = new Prof(1, "Karim");;
	private Prof p2= new Prof(2, "Mohamed Ali");;

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
	public void itSouldAssignProfsToModule() {
		List<Prof> profs = new ArrayList<Prof>();
		profs.add(p1);
		profs.add(p2);
		
		
		service.assignProfToModule(profs, m1);
		
		verifyProfWasAssignedToModule(p1,m1);
		verifyProfWasAssignedToModule(p2,m1);
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
		scriptHelper.executeScript("DELETE FROM T_PROF", connection);
		scriptHelper.executeScript("DELETE FROM T_MODULE", connection);
	}

	@Before
	public void setUp() {
		scriptHelper
				.executeScript(
						"INSERT INTO T_PROF (ID,NAME) VALUES ('"+p1.getId()+"', '"+p1.getName()+"'),('"+p2.getId()+"', '"+p2.getName()+"');",
						connection);
		scriptHelper.executeScript(
				"INSERT INTO T_MODULE (ID,NAME) VALUES ('"+m1.getId()+"', '"+m1.getName()+"');",
				connection);
	}

	private void verifyModuleIsStillThere(Module module) {
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
	
	private void verifyProfIsStillThere(Prof prof) {
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
			Logger.getLogger(AssignProfToModuleTest.class.getName()).log(Level.SEVERE, "find module failed", ex);
		} finally {
			
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(AssignProfToModuleTest.class.getName()).log(Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(prof.getId(), found.getId());
		assertEquals(prof.getName(), found.getName());
		
	}
	
	private void verifyProfWasAssignedToModule(Prof prof, Module module) {
		String sql = "SELECT * FROM T_PROF WHERE ID=?";
		int foundModuleFK = -1;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, prof.getId());
			resultSet = preparedStatement.executeQuery();

			System.out.println(sql);
			if (resultSet.next()) {
				foundModuleFK = resultSet.getInt("MODULE_FK");
			}

		} catch (SQLException ex) {
			Logger.getLogger(AssignProfToModuleTest.class.getName())
					.log(Level.SEVERE, "find Prof failed", ex);
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(
						AssignProfToModuleTest.class.getName()).log(
						Level.SEVERE, "free resourses failed", ex);
			}
		}
		verifyModuleIsStillThere(module);
		verifyProfIsStillThere(prof);
		assertEquals(module.getId(), foundModuleFK);
		
	}

	

}
