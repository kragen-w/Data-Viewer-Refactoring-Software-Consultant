package dataviewer3final;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;


public class DataLoader {

	// new variables:
    Database db;
	
	public DataLoader() {
		// setup database access
		db = Database.create();
	}
  
	public void loadData() throws FileNotFoundException {
		// reset the data storage in case this is a re-load
		db.setDataRaw(new ArrayList<DataRecord>()); // m_dataRaw = new ArrayList<DataRecord>();
	    db.setDataStates(new TreeSet<String>()); // m_dataStates = new TreeSet<String>();
	    db.setDataCountries(new TreeSet<String>()); // m_dataCountries = new TreeSet<String>();
	    db.setDataYears(new TreeSet<Integer>()); // m_dataYears = new TreeSet<Integer>();
	    db.setPlotData(null); // m_plotData = null;
	    
    	try (Scanner scanner = new Scanner(new File(db.getDataFile()))) {
    	    boolean skipFirst = true;
    	    while (scanner.hasNextLine()) {
    	    	String line = scanner.nextLine();
    	    	
    	    	if(!skipFirst) {
    	    		//New datarecord object is created by passing in the line of raw data along with the database
    	    		DataRecord record = DataRecordFactory.newDataRecord(line, db);
    	    		if(record != null) {
    	    			db.getDataRaw().add(record);
    	    		}
    	    	}
    	    	else {
    	    		skipFirst = false;
    	    	}
    	    }
    	    // update selections (not including country) for the newly loaded data
            db.setSelectedState(db.getDataStates().first()); // m_selectedState = db.getDataStates().first();
            db.setSelectedStartYear(db.getDataYears().first()); // m_selectedStartYear = db.getDataYears().first();
            db.setSelectedEndYear(db.getDataYears().last()); // m_selectedEndYear = db.getDataYears().last();

            Util.info("loaded %d data records", db.getDataRaw().size());
            Util.info("loaded data for %d states", db.getDataStates().size());
            Util.info("loaded data for %d years [%d, %d]", db.getDataYears().size(), db.getSelectedStartYear(), db.getSelectedEndYear());
    	}
    }
	
}
