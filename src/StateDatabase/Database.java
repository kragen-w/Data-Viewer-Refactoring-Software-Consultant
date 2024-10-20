package StateDatabase;

import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import edu.du.dudraw.Draw;

public class Database {
	
	private final static int GUI_MODE_MAIN_MENU = 0;
	private final static String DEFAULT_COUNTRY = "United States";
	private final static String[] VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };

	// data storage
	private String dataFile; // removed final declaration
	private List<DataRecord> dataRaw;
	private SortedSet<String> dataStates;
	private SortedSet<String> dataCountries;
	private SortedSet<Integer> dataYears;

	// GUI-related settings
	private int guiMode = GUI_MODE_MAIN_MENU; // Menu by default

	// user selections
	private String selectedCountry = DEFAULT_COUNTRY;
	private Integer selectedEndYear;
	private String selectedState;
	private Integer selectedStartYear;
	private String selectedVisualization = VISUALIZATION_MODES[0];

	// plot-related data
	private TreeMap<Integer, SortedMap<Integer, Double>> plotData = null;
	private TreeMap<Integer, Double> plotMonthlyMaxValue = null;
	private TreeMap<Integer, Double> plotMonthlyMinValue = null;

	// Window-variables
	private Draw window;
	
	private static Database instance = null;
	
	private Database() {
		// empty by design
	}
	
	public static Database create() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	// WHOLE LOTTA GETTERS AND SETTERS!!!
	// ----------------------------------------------------------------------------
	// DATA FILE methods
	public String getDataFile() {
		return dataFile;
	}
	public void setDataFile(String fileName) {
		this.dataFile = fileName;
	}
	
	
	// DATA RAW methods
	public List<DataRecord> getDataRaw() {
		return dataRaw;
	}
	public void setDataRaw(List<DataRecord> dataRaw) {
		this.dataRaw = dataRaw;
	}


	// DATA STATES methods
	public SortedSet<String> getDataStates() {
		return dataStates;
	}
	public void setDataStates(SortedSet<String> dataStates) {
		this.dataStates = dataStates;
	}


	// DATA COUNTRIES methods
	public SortedSet<String> getDataCountries() {
		return dataCountries;
	}
	public void setDataCountries(SortedSet<String> dataCountries) {
		this.dataCountries = dataCountries;
	}


	// DATA YEARS methods
	public SortedSet<Integer> getDataYears() {
		return dataYears;
	}

	public void setDataYears(SortedSet<Integer> dataYears) {
		this.dataYears = dataYears;
	}
	

	
	public int getGuiMode() {
		return guiMode;
	}
	public void setGuiMode(int guiMode) {
		this.guiMode = guiMode;
	}
	
	
	// USER SELECTION METHODS
	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public Integer getSelectedEndYear() {
		return selectedEndYear;
	}

	public void setSelectedEndYear(Integer selectedEndYear) {
		this.selectedEndYear = selectedEndYear;
	}

	public String getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}

	public Integer getSelectedStartYear() {
		return selectedStartYear;
	}

	public void setSelectedStartYear(Integer selectedStartYear) {
		this.selectedStartYear = selectedStartYear;
	}

	public String getSelectedVisualization() {
		return selectedVisualization;
	}

	public void setSelectedVisualization(String selectedVisualization) {
		this.selectedVisualization = selectedVisualization;
	}

	public TreeMap<Integer, SortedMap<Integer, Double>> getPlotData() {
		return plotData;
	}

	public void setPlotData(TreeMap<Integer, SortedMap<Integer, Double>> plotData) {
		this.plotData = plotData;
	}

	public TreeMap<Integer, Double> getPlotMonthlyMaxValue() {
		return plotMonthlyMaxValue;
	}

	public void setPlotMonthlyMaxValue(TreeMap<Integer, Double> plotMonthlyMaxValue) {
		this.plotMonthlyMaxValue = plotMonthlyMaxValue;
	}

	public TreeMap<Integer, Double> getPlotMonthlyMinValue() {
		return plotMonthlyMinValue;
	}

	public void setPlotMonthlyMinValue(TreeMap<Integer, Double> plotMonthlyMinValue) {
		this.plotMonthlyMinValue = plotMonthlyMinValue;
	}

	public Draw getWindow() {
		return window;
	}

	public void setWindow(Draw window) {
		this.window = window;
	}
}
