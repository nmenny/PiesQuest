package test;

import javax.swing.JFrame;

import game.IHM_Player;
import junit.framework.*;

/**
 * Tests the class IHM_Player
 */
public class IHM_PlayerTest extends TestCase {
	
	public IHM_PlayerTest(String testName) {
		super(testName);
	}
	
	/**
	 * Tests the method displayMenu
	 */
	public void testDisplayMenu() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		
		ihm.displayMenu(0);
		TestCase.assertEquals(ihm.getDisplayedMenu(), 0);
		
		
	}
	
}
