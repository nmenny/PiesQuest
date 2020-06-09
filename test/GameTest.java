import javax.swing.JFrame;

import game.Game;
import game.IHM_Player;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameTest extends TestCase {
	
	public GameTest(String testName) {
		super(testName);
	}
	
	public void testDisplayLevel() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		Game game = ihm.getTheGame();
		game.chooseLevel(-5);
		assertEquals(game.getCurrentLevel(), 0); //It's equal to 0 since the value -5 is not applied
		
		game.chooseLevel(0);
		assertEquals(game.getCurrentLevel(), 0);
		
		game.chooseLevel(5);
		assertEquals(game.getCurrentLevel(), 5); //Since there's at least 6 levels, it load the sixth level
		
		game.chooseLevel(12);
		this.assertFalse(game.getCurrentLevel() == 12); //There are 12 levels, so the thirteenth does not exist
		
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new GameTest("testDisplayLevel"));
		return suite;
	}
	
}
