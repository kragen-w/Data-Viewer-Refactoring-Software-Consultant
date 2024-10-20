package dataviewer3final;

import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JOptionPane;

public class CountryCommand extends Command{

	public CountryCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof MenuState) {
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
						DVapp.loadData();
					}
					catch(FileNotFoundException e) {
						// convert to a runtime exception since
						// we can't add throws to this method
						throw new RuntimeException(e);
					}
					executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
					executeList.add(new UpdatePlotCommand(db, executeList, DVapp)); // needsUpdatePlotData = true;
				}
			}
		}
	}

		
}
