package dataviewer3final;

import java.util.List;

import javax.swing.JOptionPane;

public class VisualCommand extends Command{
	private final static String[] 	VISUALIZATION_MODES = { "Raw", "Extrema (within 10% of min/max)" };


	public VisualCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof MenuState) {
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
					executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
				}
			}
		}
		
	}



}
