package dataviewer3final;

import java.util.List;

public class QuitCommand extends Command{

	public QuitCommand(Database database, List<Command> executeList, DataViewerApp DVapp) {
		super(database, executeList, DVapp);
	}

	@Override
	public void execute() {
		System.out.println("Bye");
		System.exit(0);
	}


}
