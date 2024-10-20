package dataviewer3final;

import java.util.List;

public class PlotDataCommand extends Command{

	public PlotDataCommand(Database db, List<Command> executeList, DataViewerApp DVapp) {
		super(db, executeList, DVapp);
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof MenuState) {
			// plot the data
			db.setGUIMode(db.guiMode.plot()); // m_guiMode = GUI_MODE_DATA;
			
			// add the new update commands to the list
			if(db.getPlotData() == null) {
				// first time going to render data need to generate the plot data
				executeList.add(new UpdatePlotCommand(db, executeList, DVapp)); // needsUpdatePlotData = true;
			}
			executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
		}
	}
}
