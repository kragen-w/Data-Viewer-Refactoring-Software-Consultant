package databaseAttempt;

import java.util.List;

import javax.swing.JOptionPane;

public class StartYearCommand extends Command{

	public StartYearCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof MenuState) {
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
						executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
						executeList.add(new UpdatePlotCommand(db, executeList, DVapp)); // needsUpdatePlotData = true;
					}
				}
			}
		}
		
	}
}
