package dataviewer3final;

import java.util.List;

public class MenuCommand extends Command{

	public MenuCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
	}

	@Override
	public void execute() {
		if(db.getGUIMode() instanceof PlotState) {

			db.setGUIMode(db.guiMode.menu()); // m_guiMode = GUI_MODE_MAIN_MENU;
			executeList.add(new UpdateCommand(db, executeList, DVapp));// needsUpdate = true;
		}
		
	}

}
