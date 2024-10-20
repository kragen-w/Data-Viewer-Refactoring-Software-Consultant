package databaseAttempt;

import java.util.List;

import javax.swing.JOptionPane;

public class EndYearCommand extends Command{
	DataViewerApp DVapp;

	public EndYearCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
		this.DVapp = DVapp;
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof MenuState) {
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
						
						// add the new update commands to the list
						executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
						executeList.add(new UpdatePlotCommand(db, executeList, DVapp)); // needsUpdatePlotData = true;
					}
				}
			}
		}
	}

}
