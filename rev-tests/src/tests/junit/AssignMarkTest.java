package tests.junit;

import static junit.framework.Assert.assertEquals;
import helpers.DateHelper;
import helpers.ScriptHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Module;
import model.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import services.UniversityServiceRemote;
import ds.ConnectionFactory;

public class AssignMarkTest {

	private static UniversityServiceRemote service = null;

	private Student s1 = new Student(1, "mohamed");
	private Module m1 = new Module(1, "Java EE");
	private int mark = 18;
	private String type = "EXAM";
	private Date date = DateHelper.toDate("11/03/13 10:30:00");

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
	public void itShouldAssignAMarkToAStudentInASpecifiedModule() {
		service.assignMark(s1, m1, date, type, mark);
		verifyStudentGotHisMark(s1, m1);
	}

	//Ceci ne vous concerne pas
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
		scriptHelper.executeScript("DELETE FROM T_MODULE", connection);
	}

	@Before
	public void setUp() {
		scriptHelper.executeScript("INSERT INTO T_STUDENT (ID,NAME) VALUES ('"
				+ s1.getId() + "', '" + s1.getName() + "');", connection);
		scriptHelper.executeScript("INSERT INTO T_MODULE (ID,NAME) VALUES ('"
				+ m1.getId() + "', '" + m1.getName() + "');", connection);
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
				found = new Module(resultSet.getInt("ID"),
						resultSet.getString("NAME"));
			}

		} catch (SQLException ex) {
			Logger.getLogger(AssignMarkTest.class.getName()).log(Level.SEVERE,
					"find module failed", ex);
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(AssignMarkTest.class.getName()).log(
						Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(module.getId(), found.getId());
		assertEquals(module.getName(), found.getName());
	}

	private void verifyStudentIsStillThere(Student student) {
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
				found = new Student(resultSet.getInt("ID"),
						resultSet.getString("NAME"));
			}

		} catch (SQLException ex) {
			Logger.getLogger(AssignMarkTest.class.getName()).log(Level.SEVERE,
					"find module failed", ex);
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(AssignMarkTest.class.getName()).log(
						Level.SEVERE, "free resourses failed", ex);
			}
		}
		assertEquals(student.getId(), found.getId());
		assertEquals(student.getName(), found.getName());

	}

	private void verifyStudentGotHisMark(Student student, Module module) {
		String sql = "SELECT * FROM T_MARK WHERE STUDENT_FK=? AND MODULE_FK=? AND DATE=?";
		int foundMark = -999;
		String foundType = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, student.getId());
			preparedStatement.setInt(2, module.getId());
			preparedStatement.setTimestamp(3, new Timestamp(date.getTime()));
			resultSet = preparedStatement.executeQuery();

			System.out.println(sql);
			if (resultSet.next()) {
				foundMark = resultSet.getInt("MARK");
				foundType = resultSet.getString("TYPE");
			}

		} catch (SQLException ex) {
			Logger.getLogger(AssignMarkTest.class.getName()).log(Level.SEVERE,
					"failed", ex);
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ex) {
				Logger.getLogger(AssignProfToModuleTest.class.getName()).log(
						Level.SEVERE, "free resourses failed", ex);
			}
		}
		verifyModuleIsStillThere(module);
		verifyStudentIsStillThere(student);
		assertEquals(mark, foundMark);
		assertEquals(type, foundType);

	}

}
