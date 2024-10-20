package databaseAttempt;

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

public class DataViewerApp implements DrawListener{
	// Private constants (alphabetical)
    private final static int 		GUI_MODE_MAIN_MENU = 0;
    private final static int 		GUI_MODE_DATA = 1;
	private final static int 		WINDOW_HEIGHT = 720;
	private final static String 	WINDOW_TITLE = "DataViewer Application";
	private final static int 		WINDOW_WIDTH = 1320; // should be a multiple of 12
	private final static String[] 	VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };
		
	// Instance variables (alphabetized)
    // new variables:
    Database db;
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
        window.addListener(this);
        //window.addListener(menuUI);
        //window.addListener(plotUI);
        
        db.setWindow(window);

        
        // Load data
        dataProcessor.loadData();
        
        
        // draw the screen for the first time -- this will be the main menu
	    update();
    }

    
    
	public void update() {   
    	GUIMode m_guiMode = db.getGUIMode();
    	
    	if(m_guiMode instanceof MenuState) {
    		menuUI.drawMainMenu();
    	}
    	else if(m_guiMode instanceof PlotState) {
    		plotUI.drawData();
    	}
    	else {
    		throw new IllegalStateException(String.format("Unexpected drawMode=%d", m_guiMode));
    	}
        // for double-buffering
        db.getWindow().show();
    }
	
	public void updatePlotData() {
		// something changed with the data that needs to be plotted
		dataProcessor.updatePlotData();
	}
	
	public void loadData() throws FileNotFoundException {
		dataProcessor.loadData();
	}



	@Override
	public void keyPressed(int key) {
		Util.trace("key pressed '%c'", (char)key);
		
		// make a new list of commands
		List<Command> executeList = new ArrayList<Command>();
		
		
		if(key == 'Q') {
			executeList.add(new QuitCommand(db, executeList, this));
		}
		else if(key == 'P') {
			executeList.add(new PlotDataCommand(db, executeList, this));
		}
		else if(key == 'C') {
			executeList.add(new CountryCommand(db, executeList, this));
		}
		else if(key == 'T') {
			executeList.add(new StateCommand(db, executeList, this));
		}
		else if(key == 'S') {
			executeList.add(new StartYearCommand(db, executeList, this));
		}
		else if(key == 'E') {
			executeList.add(new EndYearCommand(db, executeList, this));
		}
		else if(key == 'V') {
			executeList.add(new VisualCommand(db, executeList, this));
		}
		else if(key == 'M') {
			executeList.add(new MenuCommand(db, executeList, this));
		}
		
		while (!executeList.isEmpty()) {
			executeList.get(0).execute();	// execute the first command
			executeList.remove(0);  // remove the first command from the list
		}



		
	}

	@Override
	public void keyReleased(int arg0) {}

	@Override
	public void keyTyped(char arg0) {}

	@Override
	public void mouseClicked(double arg0, double arg1) {}

	@Override
	public void mouseDragged(double arg0, double arg1) {}

	@Override
	public void mousePressed(double arg0, double arg1) {}

	@Override
	public void mouseReleased(double arg0, double arg1) {}

}