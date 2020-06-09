package test;
import javax.swing.JFrame;

import game.Game;
import game.IHM_Player;
import junit.framework.*;

/**
 * A test for the class Game
 */
public class GameTest extends TestCase {
	
	/**
	 * creates a new test with a given name
	 * @param testName the name of the test
	 */
	public GameTest(String testName) {
		super(testName);
	}
	
	/**
	 * Tests the chooseLevel method
	 */
	public void testChooseLevel() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		Game game = ihm.getTheGame();
		game.chooseLevel(-5);
		TestCase.assertEquals(game.getCurrentLevel(), 0); //It's equal to 0 since the value -5 is not applied
		
		game.chooseLevel(0);
		TestCase.assertEquals(game.getCurrentLevel(), 0);
		
		game.chooseLevel(5);
		TestCase.assertEquals(game.getCurrentLevel(), 5); //Since there's at least 6 levels, it load the sixth level
		
		game.chooseLevel(12);
		TestCase.assertFalse(game.getCurrentLevel() == 12); //There are 12 levels, so the thirteenth does not exist
		
	}
	
	/**
	 * Tests the displayLevel method
	 */
	public void testDisplayLevel() {
		
	}
	
	/**
	 * The test suite of this test
	 * @return
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new GameTest("testChooseLevel"));
		suite.addTest(new GameTest("testDisplayLevel"));
		return suite;
	}
	
}
