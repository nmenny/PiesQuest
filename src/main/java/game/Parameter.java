package game;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the parameters of the game to configure it
 */
public class Parameter {
	
	/**
	 * Stores all the different format of the screen
	 */
	public static final String[] DISPLAY_FORMATS = {"800x600", "1080x720", "1920x1080"};
	
	/**
	 * The sound volume of the game (in percent)
	 */
	private int volume;
	
	/**
	 * The format in which the game will be displayed (format: width_x_height)
	 */
	private String displayFormat;

	/**
	 * Is the parameter menu displayed
	 */
	private boolean isDisplayed;
	
	/**
	 * Give standard parameters
	 */
	public Parameter() {
		this.volume = 50;
		this.displayFormat = "1080x720"; //The default display format
		this.isDisplayed = false;
	}
	
	/**
	 * Displays the menu at the screen
	 * @param frame the container to put elements in
	 * @param theInterface the interface
	 */
	public void displayMenu(JFrame frame, PlayerInterface theInterface) {
		this.isDisplayed = true;
		
		Font componentFont = new Font("Arial", Font.PLAIN, 15);
		
		//Creating a panel to contain all the components
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		//Adding a constraint to place the components on the grid
		GridBagConstraints c = new GridBagConstraints();
		
		//--- Format components ---
		JLabel formatLabel = new JLabel("Format : ");
		formatLabel.setFont(componentFont);
		JComboBox<String> cmb = new JComboBox<String>(Parameter.DISPLAY_FORMATS);
		cmb.setFont(componentFont);
		cmb.setSelectedIndex(Parameter.findFormatIndex(this.displayFormat));
		
		cmb.setVisible(true);
		formatLabel.setVisible(true);
		
		//Placing the components on the grid
		c.gridx = 0;
		c.gridy = 0;
		panel.add(formatLabel, c);
		c.gridx = 1;
		panel.add(cmb, c);
		
		
		//--- Close button ---
		JButton quitBtn = new JButton("Back to menu");
		quitBtn.setFont(componentFont);
		
		
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		//Adding a top padding
		c.insets = new Insets(50, 0, 0, 0);
		panel.add(quitBtn, c);
		
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		frame.add(panel);
		
		
		frame.setVisible(true);
		
		//--- Events ---
		
		//When the value of the combobox changes
		cmb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFormat((String) cmb.getSelectedItem());
				frame.setSize(getWidth(), getHeight());
				theInterface.setSize(getWidth(), getHeight());
				panel.setVisible(false);
				
				displayMenu(frame, theInterface);
			}
		});
		
		//When we click on the "return to menu" button
		quitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isDisplayed = false;
				panel.setVisible(false);
				frame.remove(panel);
				theInterface.displayMenu(0);
			}
		});
	}
	
	/**
	 * Sets the new volume
	 * @param theVolume the new volume
	 */
	public void setVolume(int theVolume) {
		//TODO implement the method
	}
	
	/**
	 * Sets the new format of the screen
	 * @param theFormat the new format of the screen
	 */
	public void setFormat(String theFormat) {
		//If the given format is in the format array, we can set it
		if(Parameter.findFormatIndex(theFormat) != -1) {
			this.displayFormat = theFormat;
		}
	}
	
	/**
	 * Gives the current volume of the game
	 * @return the volume of the game
	 */
	public int getVolume() {
		return this.volume;
	}
	
	/**
	 * Gives the current display format of the game
	 * @return the current format of the screen
	 */
	public String getDisplayFormat() {
		return this.displayFormat;
	}
	
	/**
	 * Gets screen width
	 * @return the width of the screen
	 */
	public int getWidth() {
		return Integer.parseInt(this.displayFormat.split("x")[0]);
	}
	
	/**
	 * Gets the screen height
	 * @return the height of the screen
	 */
	public int getHeight() {
		return Integer.parseInt(this.displayFormat.split("x")[1]);
	}

	/**
	 * Allows us to know if the parameters are displayed
	 * @return <tt>true</tt> if the parameters are displayed, <tt>false</tt> else
	 */
	public boolean isDisplayed() {
		return this.isDisplayed;
	}
	
	/**
	 * Allows us to close and open the menu
	 * @param isDisplayed <tt>true</tt> if you want to display it, <tt>false</tt> if you want to close it
	 */
	public void setDisplay(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	
	/**
	 * Finds the index in the DISPLAY_FORMATS array of given format
	 * @param format The searched format
	 * @return the index corresponding to the format in DISPLAY_FORMATS or -1 if not found
	 */
	public static int findFormatIndex(String format) {
		int formatIndex = 0;
		while(formatIndex < Parameter.DISPLAY_FORMATS.length) {
			if(Parameter.DISPLAY_FORMATS[formatIndex].equals(format)) {
				return formatIndex;
			}
			formatIndex++;
		}
		return -1;
	}
	
}
