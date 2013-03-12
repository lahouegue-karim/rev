package tests.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssignMarkTest.class, AssignProfToModuleTest.class,
		JPQLTests.class, StudentModuleAndProfCreationTest.class })
public class AllTests {

}
