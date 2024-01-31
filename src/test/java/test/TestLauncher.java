package test;
import junit.framework.*;
/**
 * Launches the test session
 */
public class TestLauncher extends TestCase {
	/**
	 * The test suite of this test
	 * @return the tests to do
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTest(new GameTest("testChooseLevel"));
		suite.addTest(new GameTest("testDisplayLevel"));
		suite.addTest(new GameTest("testSetCurrentSelection"));
		
		suite.addTest(new PlayerInterfaceTest("testDisplayMenu"));
		return suite;
	}
}
