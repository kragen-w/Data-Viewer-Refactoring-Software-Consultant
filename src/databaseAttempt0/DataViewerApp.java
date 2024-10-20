package databaseAttempt0;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

public class DataViewerApp {
	// Private constants (alphabetical)
    private final static int 		GUI_MODE_MAIN_MENU = 0;
    private final static int 		GUI_MODE_DATA = 1;
	private final static int 		WINDOW_HEIGHT = 720;
	private final static String 	WINDOW_TITLE = "DataViewer Application";
	private final static int 		WINDOW_WIDTH = 1320; // should be a multiple of 12
		
	// Instance variables (alphabetized)
    // new variables:
    private Database db;
    MenuUI menuUI;
    PlotUI plotUI;
    DataProcessor dataProcessor;
    
	/**
	 * Constructor sets up the window and loads the specified data file.
	 */
    public DataViewerApp(String dataFile) throws FileNotFoundException {
    	
    	// Get a database (making the only one)
    	db = Database.create();
    	
    	// Construct the data processors!
    	dataProcessor = new DataProcessor();
    	
    	// create the MenuUI. pass self as reference so it can call methods. same for dataprocessor because the menu will ask to process data!
    	menuUI = new MenuUI(this, dataProcessor);
    	
    	// create the PlotUI. pass self as reference so it can call methods (basically just update.
    	plotUI = new PlotUI(this);
    	
    	// save the data file name for later use if user switches country IN THE DATABASE
    	db.setDataFile(dataFile); // m_dataFile = dataFile;
    	
    	// Setup the DuDraw board
        Draw window = new Draw(WINDOW_TITLE);
        window.setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.enableDoubleBuffering(); // Too slow otherwise -- need to use .show() later
        
        // Add the mouse/key listeners
        //window.addListener(this);
        window.addListener(menuUI);
        window.addListener(plotUI);
        
        db.setWindow(window);

        
        // Load data
        dataProcessor.loadData();
        
        
        // draw the screen for the first time -- this will be the main menu
	    update();
    }

    
    
	public void update() {   
    	int m_guiMode = db.getGuiMode();
    	
    	if(m_guiMode == GUI_MODE_MAIN_MENU) {
    		menuUI.drawMainMenu();
    	}
    	else if(m_guiMode == GUI_MODE_DATA) {
    		plotUI.drawData();
    	}
    	else {
    		throw new IllegalStateException(String.format("Unexpected drawMode=%d", m_guiMode));
    	}
        // for double-buffering
        db.getWindow().show();
    }

}