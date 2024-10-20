package dataviewer3final;

import java.util.List;

import javax.swing.JOptionPane;

public class StateCommand extends Command{

	public StateCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof MenuState) {
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
					executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
					executeList.add(new UpdatePlotCommand(db, executeList, DVapp)); // needsUpdatePlotData = true;
				}
			}
		}
	}



}
