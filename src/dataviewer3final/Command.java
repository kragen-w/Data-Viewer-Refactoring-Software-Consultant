package dataviewer3final;

import java.util.List;

public abstract class Command {
	protected Database db;
	protected DataViewerApp DVapp;
	protected List<Command> executeList;
	
	public Command(Database db, List<Command> executeList, DataViewerApp DVapp) {
		this.db = db;
		this.executeList = executeList;
		this.DVapp = DVapp;
	}
	
	public abstract void execute();
}
