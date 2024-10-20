package databaseAttempt;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
	protected Database db;
	protected DataViewerApp DVapp;
	protected List<Command> executeList = new ArrayList<>();;
	
	public Command(Database database, List<Command> executeList, DataViewerApp DVapp) {
		this.db = database;
		this.executeList = executeList;
		this.DVapp = DVapp;
	}
	
	public abstract void execute();
}
