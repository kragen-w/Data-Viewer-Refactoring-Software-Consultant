package databaseAttempt;

import java.util.List;

public class UpdatePlotCommand extends Command{

	public UpdatePlotCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		DVapp.updatePlotData();
	}

}
