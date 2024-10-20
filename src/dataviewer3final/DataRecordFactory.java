package dataviewer3final;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataRecordFactory {
	
	//constants
	private final static int 		FILE_COUNTRY_IDX = 4;
	private final static int 		FILE_DATE_IDX = 0;
	private final static int 		FILE_NUM_COLUMNS = 5;
	private final static int 		FILE_STATE_IDX = 3;
	private final static int 		FILE_TEMPERATURE_IDX = 1;
	private final static int 		FILE_UNCERTAINTY_IDX = 2;
	
	public static DataRecord newDataRecord(String line, Database db) {
		
        List<String> rawValues = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                rawValues.add(rowScanner.next());
            }
        }
        db.getDataCountries().add(rawValues.get(FILE_COUNTRY_IDX));
        if(rawValues.size() != FILE_NUM_COLUMNS) {
        	Util.trace("malformed line '%s'...skipping", line);
        	return null;
        }
        else if(!rawValues.get(FILE_COUNTRY_IDX).equals(db.getSelectedCountry())) {
        	Util.trace("skipping non-USA record: %s", rawValues);
        	return null;
        }
        else {
        	Util.trace("processing raw data: %s", rawValues.toString());
        }
        try {
        	// Parse these into more useful objects than String
        	//List<Object> values = new ArrayList<Object>(4);
        	DataRecord record = new DataRecord();
        	
        	Integer year = parseYear(rawValues.get(FILE_DATE_IDX));
        	if(year == null) {
        		return null;
        	}
        	//values.add(year);
        	record.setYear(year);
        	
        	Integer month = parseMonth(rawValues.get(FILE_DATE_IDX));
        	if(month == null) {
        		return null;
        	}
        	//values.add(month);
        	record.setMonth(month);
        	
        	record.setAvgTemp(Double.parseDouble(rawValues.get(FILE_TEMPERATURE_IDX)));
        	//values.add(Double.parseDouble(rawValues.get(FILE_TEMPERATURE_IDX)));
        	
        	//not going to use UNCERTAINTY yet
        	//values.add(Double.parseDouble(rawValues.get(FILE_UNCERTAINTY_IDX)));
        	
        	record.setState(rawValues.get(FILE_STATE_IDX));
        	//values.add(rawValues.get(FILE_STATE_IDX));
        	
        	record.setCountry(rawValues.get(FILE_COUNTRY_IDX));
        	// since all are the same country
        	//values.add(rawValues.get(FILE_COUNTRY_IDX));
        	
        	// if we got here, add the state to the list of states
        	db.getDataStates().add(rawValues.get(FILE_STATE_IDX));
        	db.getDataYears().add(year);
        	return record;
        }
        catch(NumberFormatException e) {
        	Util.trace("unable to parse data line, skipping...'%s'", line);
        	return null;
        }
    }
	private static Integer parseYear(String dateString) {
    	Integer ret = null;
    	if(dateString.indexOf("/") != -1) {
    		// Assuming something like 1/20/1823
    		String[] parts = dateString.split("/");
    		if(parts.length == 3) {
	    		ret = Integer.parseInt(parts[2]);
    		}
    	}
    	else if(dateString.indexOf("-") != -1) {
    		// Assuming something like 1823-01-20
    		String[] parts = dateString.split("-");
    		if(parts.length == 3) {
    			ret = Integer.parseInt(parts[0]);
    		}
    	}
    	else {
    		throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
    	}
    	if(ret == null) {
    		Util.trace("Unable to parse year from date: '%s'", dateString);
    	}
    	return ret;
    }
    
    private static Integer parseMonth(String dateString) {
    	Integer ret = null;
    	if(dateString.indexOf("/") != -1) {
    		// Assuming something like 1/20/1823
    		String[] parts = dateString.split("/");
    		if(parts.length == 3) {
	    		ret = Integer.parseInt(parts[0]);
    		}
    	}
    	else if(dateString.indexOf("-") != -1) {
    		// Assuming something like 1823-01-20
    		String[] parts = dateString.split("-");
    		if(parts.length == 3) {
    			ret = Integer.parseInt(parts[1]);
    		}
    	}
    	else {
    		throw new RuntimeException(String.format("Unexpected date delimiter: '%s'", dateString));
    	}
    	if(ret == null || ret.intValue() < 1 || ret.intValue() > 12) {
    		Util.trace("Unable to parse month from date: '%s'", dateString);
    		return null;
    	}
    	return ret;
	}
}

