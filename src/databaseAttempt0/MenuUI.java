package databaseAttempt0;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

public class MenuUI implements DrawListener{
	
	// contants
    private final static double		MENU_STARTING_X = 40.0;
	private final static double 	MENU_STARTING_Y = 90.0;
	private final static double 	MENU_ITEM_SPACING = 5.0;
    private final static int 		GUI_MODE_MAIN_MENU = 0;
    private final static int 		GUI_MODE_DATA = 1;
	private final static String[] 	VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };

	
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
	
	@Override
	public void keyPressed(int key) {
		
		// if not in gui mode then do nothing
		if (db.getGuiMode() == GUI_MODE_DATA) {
			return;
		}
		
		System.out.println("handling key press in menuUI");

		boolean needsUpdate = false;
		boolean needsUpdatePlotData = false;
		Util.trace("key pressed '%c'", (char)key);
		// regardless of draw mode, 'Q' or 'q' means quit:
		if(key == 'Q') {
			System.out.println("Bye");
			System.exit(0);
		}
		else if(db.getGuiMode() == GUI_MODE_MAIN_MENU) {
			if(key == 'P') {
				// plot the data
				db.setGuiMode(GUI_MODE_DATA); // m_guiMode = GUI_MODE_DATA;
				
				if(db.getPlotData() == null) {
					// first time going to render data need to generate the plot data
					needsUpdatePlotData = true;
				}
				needsUpdate = true;
			}
			else if(key == 'C') {
				// set the Country
				Object selectedValue = JOptionPane.showInputDialog(null,
			             "Choose a Country", "Input",
			             JOptionPane.INFORMATION_MESSAGE, null,
			             db.getDataCountries().toArray(), db.getSelectedCountry());
				
				if(selectedValue != null) {
					Util.info("User selected: '%s'", selectedValue);
					if(!selectedValue.equals(db.getSelectedCountry())) {
						// change in data
						db.setSelectedCountry((String)selectedValue); // m_selectedCountry = (String)selectedValue;
						try {
							dataProcessor.loadData();
						}
						catch(FileNotFoundException e) {
							// convert to a runtime exception since
							// we can't add throws to this method
							throw new RuntimeException(e);
						}
						needsUpdate = true;
						needsUpdatePlotData = true;
					}
				}
			}

			else if(key == 'T') {
				// set the state
				Object selectedValue = JOptionPane.showInputDialog(null,
			             "Choose a State", "Input",
			             JOptionPane.INFORMATION_MESSAGE, null,
			             db.getDataStates().toArray(), db.getSelectedState());
				
				if(selectedValue != null) {
					Util.info("User selected: '%s'", selectedValue);
					if(!selectedValue.equals(db.getSelectedState())) {
						// change in data
						db.setSelectedState((String)selectedValue); // m_selectedState = (String)selectedValue;
						needsUpdate = true;
						needsUpdatePlotData = true;
					}
				}
			}
			else if(key == 'S') {
				// set the start year
				Object selectedValue = JOptionPane.showInputDialog(null,
			             "Choose the start year", "Input",
			             JOptionPane.INFORMATION_MESSAGE, null,
			             db.getDataYears().toArray(), db.getSelectedStartYear());
				
				if(selectedValue != null) {
					Util.info("User seleted: '%s'", selectedValue);
					Integer year = (Integer)selectedValue;
					if(year.compareTo(db.getSelectedEndYear()) > 0) {
						Util.error("new start year (%d) must not be after end year (%d)", year, db.getSelectedEndYear());
					}
					else {
						if(!(db.getSelectedStartYear().equals(year))) {
							db.setSelectedStartYear(year); // m_selectedStartYear = year;
							needsUpdate = true;
							needsUpdatePlotData = true;
						}
					}
				}
			}
			else if(key == 'E') {
				// set the end year
				Object selectedValue = JOptionPane.showInputDialog(null,
			             "Choose the end year", "Input",
			             JOptionPane.INFORMATION_MESSAGE, null,
			             db.getDataYears().toArray(), db.getSelectedEndYear());
				
				if(selectedValue != null) {
					Util.info("User seleted: '%s'", selectedValue);
					Integer year = (Integer)selectedValue;
					if(year.compareTo(db.getSelectedStartYear()) < 0) {
						Util.error("new end year (%d) must be not be before start year (%d)", year, db.getSelectedStartYear());
					}
					else {
						if(!(db.getSelectedEndYear().equals(year))) {
							db.setSelectedEndYear(year); // m_selectedEndYear = year;
							needsUpdate = true;
							needsUpdatePlotData = true;
						}
					}
				}
			}
			else if(key == 'V') {
				// set the visualization
				Object selectedValue = JOptionPane.showInputDialog(null,
						"Choose the visualization mode", "Input",
						JOptionPane.INFORMATION_MESSAGE, null,
						VISUALIZATION_MODES, db.getSelectedVisualization());

				if(selectedValue != null) {
					Util.info("User seleted: '%s'", selectedValue);
					String visualization = (String)selectedValue;
					if(!(db.getSelectedVisualization().equals(visualization))) {
						db.setSelectedVisualization(visualization); // m_selectedVisualization = visualization;
						needsUpdate = true;
					}
				}
			}

		}
		// menu doesnt use the m key for anything so no need to have this method
//		else if (db.getGuiMode() == GUI_MODE_DATA) {
//			if(key == 'M') {
//				db.setGuiMode(GUI_MODE_MAIN_MENU); // m_guiMode = GUI_MODE_MAIN_MENU;
//				needsUpdate = true;
//			}
//		}
		else {
			throw new IllegalStateException(String.format("unexpected mode: %d", db.getGuiMode()));
		}
		if(needsUpdatePlotData) {
			// something changed with the data that needs to be plotted
			dataProcessor.updatePlotData();
		}
		if(needsUpdate) {
			DVapp.update();
		}
		
	}

	@Override
	public void keyReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(char arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
