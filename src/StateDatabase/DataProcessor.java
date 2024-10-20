package StateDatabase;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataProcessor {
	
	// new variables:
    Database db;
    DataLoader dataLoader;
	
	public DataProcessor() {
		// setup database access
		db = Database.create();
		
		//create the dataloader and store for access
		dataLoader = new DataLoader();
		
	}
	

	public void updatePlotData() {
		List<DataRecord> m_dataRaw = db.getDataRaw();
		
		//debug("raw data: %s", m_rawData.toString());
		// plot data is a map where the key is the Month, and the value is a sorted map where the key
		// is the year. 
		db.setPlotData(new TreeMap<Integer,SortedMap<Integer,Double>>()); // m_plotData = new TreeMap<Integer,SortedMap<Integer,Double>>();
		for(int month = 1; month <= 12; month++) {
			// any year/months not filled in will be null
			db.getPlotData().put(month, new TreeMap<Integer,Double>());
		}
		// now run through the raw data and if it is related to the current state and within the current
		// years, put it in a sorted data structure, so that we 
		// find min/max year based on data 
		db.setPlotMonthlyMaxValue(new TreeMap<Integer,Double>()); // m_plotMonthlyMaxValue = new TreeMap<Integer,Double>();
		db.setPlotMonthlyMinValue(new TreeMap<Integer,Double>()); // m_plotMonthlyMinValue = new TreeMap<Integer,Double>();
		// initialize
		for(int i = 1; i <= 12; i++) {
			db.getPlotMonthlyMaxValue().put(i, Double.MIN_VALUE);
			db.getPlotMonthlyMinValue().put(i, Double.MAX_VALUE);
		}
		for(DataRecord rec : m_dataRaw) {
			
			String state = (String)rec.getState();
			Integer year = (Integer)rec.getYear();
			
			// Check to see if they are the state and year range we care about
			if (state.equals(db.getSelectedState()) && 
			   ((year.compareTo(db.getSelectedStartYear()) >= 0 && year.compareTo(db.getSelectedEndYear()) <= 0))) {
						
				// Ok, we need to add this to the list of values for the month
				Integer month = (Integer)rec.getMonth();
				Double value = (Double)rec.getAvgTemp();
				
				if(!(db.getPlotMonthlyMinValue().containsKey(month)) || value.compareTo(db.getPlotMonthlyMinValue().get(month)) < 0) {
					db.getPlotMonthlyMinValue().put(month, value);
				}
				if(!(db.getPlotMonthlyMaxValue().containsKey(month)) || value.compareTo(db.getPlotMonthlyMaxValue().get(month)) > 0) {
					db.getPlotMonthlyMaxValue().put(month, value);
				}
	
				db.getPlotData().get(month).put(year, value);
			}
		}
		//debug("plot data: %s", m_plotData.toString());
	}
	
	public void loadData() throws FileNotFoundException {
		// redirect the load data call to the data loader!!
		dataLoader.loadData();
	}

}
