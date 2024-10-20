package databaseAttempt0;

import java.awt.Color;
import java.util.SortedMap;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

public class PlotUI implements DrawListener{
	
	// constants
	private final static double 	DATA_WINDOW_BORDER = 50.0;
	private final static double 	EXTREMA_PCT = 0.1;

	private final static String[] 	MONTH_NAMES = { "", // 1-based
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private final static double		TEMPERATURE_MAX_C = 30.0;
	private final static double		TEMPERATURE_MIN_C = -10.0;
	private final static double		TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;
	private final static String[] 	VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };
	private final static int		VISUALIZATION_EXTREMA_IDX = 1;
	private final static int 		WINDOW_HEIGHT = 720;
	private final static int 		WINDOW_WIDTH = 1320; // should be a multiple of 12
    private final static int 		GUI_MODE_MAIN_MENU = 0;
    private final static int 		GUI_MODE_DATA = 1;
	
	// new variables:
    Database db;
    DataViewerApp DVapp;
	
	public PlotUI(DataViewerApp DVapp) {
		// setup database access
		db = Database.create();
		
		// setup DataViewerApp access
		this.DVapp = DVapp;
	}
	
	public void drawData() {
    	Draw m_window = db.getWindow();
    	
    	// Give a buffer around the plot window
        m_window.setXscale(-DATA_WINDOW_BORDER, WINDOW_WIDTH+DATA_WINDOW_BORDER);
		m_window.setYscale(-DATA_WINDOW_BORDER, WINDOW_HEIGHT+DATA_WINDOW_BORDER);

    	// gray background
    	m_window.clear(Color.LIGHT_GRAY);

    	// white plot area
		m_window.setPenColor(Color.WHITE);
		m_window.filledRectangle(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0, WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0);  

    	m_window.setPenColor(Color.BLACK);
    	
    	double nCols = 12; // one for each month
    	double nRows = db.getSelectedEndYear() - db.getSelectedStartYear() + 1; // for the years
    	
    	Util.debug("nCols = %f, nRows = %f", nCols, nRows);
 		
        double cellWidth = WINDOW_WIDTH / nCols;
        double cellHeight = WINDOW_HEIGHT / nRows;
        
        Util.debug("cellWidth = %f, cellHeight = %f", cellWidth, cellHeight);
        
        boolean extremaVisualization = (db.getSelectedVisualization()).equals(VISUALIZATION_MODES[VISUALIZATION_EXTREMA_IDX]);
        Util.info("visualization: %s (extrema == %b)", db.getSelectedVisualization(), extremaVisualization);
        
        for(int month = 1; month <= 12; month++) {
            double fullRange = db.getPlotMonthlyMaxValue().get(month) - db.getPlotMonthlyMinValue().get(month);
            double extremaMinBound = db.getPlotMonthlyMinValue().get(month) + EXTREMA_PCT * fullRange;
            double extremaMaxBound = db.getPlotMonthlyMaxValue().get(month) - EXTREMA_PCT * fullRange;


            // draw the line separating the months and the month label
        	m_window.setPenColor(Color.BLACK);
        	double lineX = (month-1.0)*cellWidth;
        	m_window.line(lineX, 0.0, lineX, WINDOW_HEIGHT);
        	m_window.text(lineX+cellWidth/2.0, -DATA_WINDOW_BORDER/2.0, MONTH_NAMES[month]);
        	
        	// there should always be a map for the month
        	SortedMap<Integer,Double> monthData = db.getPlotData().get(month);
        	
        	for(int year = db.getSelectedStartYear(); year <= db.getSelectedEndYear(); year++) {

        		// month data structure might not have every year
        		if(monthData.containsKey(year)) {
        			Double value = monthData.get(year);
        			
        			double x = (month-1.0)*cellWidth + 0.5 * cellWidth;
        			double y = (year-db.getSelectedStartYear())*cellHeight + 0.5 * cellHeight;
        			
        			Color cellColor = null;
        			
        			// get either color or grayscale depending on visualization mode
        			if(extremaVisualization && value > extremaMinBound && value < extremaMaxBound) {
        				cellColor = getDataColor(value, true);
        			}
        			else if(extremaVisualization) {
        				// doing extrema visualization, show "high" values in red "low" values in blue.
        				if(value >= extremaMaxBound) {
        					cellColor = Color.RED;
        				}
        				else {
        					cellColor = Color.BLUE;
        				}
        			}
        			else {
        				cellColor = getDataColor(value, false);
        			}
        			
        			// draw the rectangle for this data point
        			m_window.setPenColor(cellColor);
        			Util.trace("month = %d, year = %d -> (%f, %f) with %s", month, year, x, y, cellColor.toString());
        			m_window.filledRectangle(x, y, cellWidth/2.0, cellHeight/2.0);
        		}
        	}
        }
        
        // draw the labels for the y-axis
        m_window.setPenColor(Color.BLACK);

        double labelYearSpacing = (db.getSelectedEndYear() - db.getSelectedStartYear()) / 5.0;
        double labelYSpacing = WINDOW_HEIGHT/5.0;
        // spaced out by 5, but need both the first and last label, so iterate 6
        for(int i=0; i<6; i++) {
        	int year = (int)Math.round(i * labelYearSpacing + db.getSelectedStartYear());
        	String text = String.format("%4d", year);
        	
        	m_window.textRight(0.0, i*labelYSpacing, text);
        	m_window.textLeft(WINDOW_WIDTH, i*labelYSpacing, text);
        }
     
        // draw rectangle around the whole data plot window
        m_window.rectangle(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0, WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0);
        
        // put in the title
        String title = String.format("%s, %s from %d to %d. Press 'M' for Main Menu.  Press 'Q' to Quit.",
        		db.getSelectedState(), db.getSelectedCountry(), db.getSelectedStartYear(), db.getSelectedEndYear());
        m_window.text(WINDOW_WIDTH/2.0, WINDOW_HEIGHT+DATA_WINDOW_BORDER/2.0, title);
	}
    
    /**
     * Return a Color object based on the value passed in.
     * @param value - controls the color
     * @param doGrayscale - if true, return a grayscale value (r, g, b are all equal);
     * 	otherwise return a range of red to green.
     * @return null is value is null, otherwise return a Color object
     */
    public Color getDataColor(Double value, boolean doGrayscale) {
    	if(null == value) {
    		return null;
    	}
    	double pct = (value - TEMPERATURE_MIN_C) / TEMPERATURE_RANGE;
    	Util.trace("converted %f raw value to %f %%", value, pct);
    
    	if (pct > 1.0) {
            pct = 1.0;
        }
        else if (pct < 0.0) {
            pct = 0.0;
        }
        int r, g, b;
        // Replace the color scheme with my own
        if (!doGrayscale) {
        	r = (int)(255.0 * pct);
        	g = 0;
        	b = (int)(255.0 * (1.0-pct));
        	
        } else {
        	// Grayscale for the middle extema
        	r = g = b = (int)(255.0 * pct);
        }
        
        Util.trace("converting %f to [%d, %d, %d]", value, r, g, b);

		return new Color(r, g, b);
	}
	
	@Override
	public void keyPressed(int key) {
		// handle keypressed by sending to current UI
		
				if (db.getGuiMode() == GUI_MODE_MAIN_MENU) {
					System.out.println("letting menu UI handle it");
					return;
				}
				
				// logging
				System.out.println("handling plot keypress in plot");
					
				
				
				boolean needsUpdate = false;
				boolean needsUpdatePlotData = false;
				Util.trace("key pressed '%c'", (char)key);
				// regardless of draw mode, 'Q' or 'q' means quit:
				if(key == 'Q') {
					System.out.println("Bye");
					System.exit(0);
				}
//				
				else if (db.getGuiMode() == GUI_MODE_DATA) {
					if(key == 'M') {
						db.setGuiMode(GUI_MODE_MAIN_MENU); // m_guiMode = GUI_MODE_MAIN_MENU;
						needsUpdate = true;
					}
				}
				else {
					throw new IllegalStateException(String.format("unexpected mode: %d", db.getGuiMode()));
				}
				
				// plot ui isn't going to update its self  ever so no need for this method
//				if(needsUpdatePlotData) {
//					// something changed with the data that needs to be plotted
//					DVapp.updatePlotData();
//				}
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
