package databaseAttempt;

import java.util.List;

public class UpdateCommand extends Command{
	DataViewerApp DVapp;

	public UpdateCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
		this.DVapp = DVapp;
	}

	@Override
	public void execute() {
		DVapp.update();
	}

}
