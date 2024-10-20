package databaseAttempt;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

public class MenuUI{
	
	// contants
    private final static double		MENU_STARTING_X = 40.0;
	private final static double 	MENU_STARTING_Y = 90.0;
	private final static double 	MENU_ITEM_SPACING = 5.0;
    //private final static int 		GUI_MODE_MAIN_MENU = 0;
    //private final static int 		GUI_MODE_DATA = 1;
	
	
	// new variables:
    Database db;
    DataViewerApp DVapp;
    DataProcessor dataProcessor;
    
   
	
	public MenuUI(DataViewerApp DVapp, DataProcessor dataProcessor) {
		// setup database access
		db = Database.create();
		
		//setup dataProcessor access
		this.dataProcessor = dataProcessor;
		
		// setup DataViewerApp access
		this.DVapp = DVapp;
		
	}
	
	 	
	public void drawMainMenu() {
    	Draw m_window = db.getWindow();
    	m_window.clear(Color.WHITE);

    	String[] menuItems = {
    			"Type the menu number to select that option:",
    			"",
    			String.format("C     Set country: [%s]", db.getSelectedCountry()),
    			String.format("T     Set state: [%s]", db.getSelectedState()),
    			String.format("S     Set start year [%d]", db.getSelectedStartYear()),
    			String.format("E     Set end year [%d]", db.getSelectedEndYear()),
    			String.format("V     Set visualization [%s]", db.getSelectedVisualization()),
    			String.format("P     Plot data"),
    			String.format("Q     Quit"),
    	};
    	
    	// enable drawing by "percentage" with the menu drawing
        m_window.setXscale(0, 100);
		m_window.setYscale(0, 100);
		
		// draw the menu
    	m_window.setPenColor(Color.BLACK);
		
		drawMenuItems(menuItems);
    }

	public void drawMenuItems(String[] menuItems) {
		double yCoord = MENU_STARTING_Y;
		Draw m_window = db.getWindow();
		
		for(int i=0; i<menuItems.length; i++) {
			m_window.textLeft(MENU_STARTING_X, yCoord, menuItems[i]);
			yCoord -= MENU_ITEM_SPACING;
		}
	}
	
	

}
