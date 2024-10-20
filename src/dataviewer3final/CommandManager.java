package dataviewer3final;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// THIS WILL GET MADE AND OWNED BY DVAPP
public class CommandManager {
	private Map<String, Command> commands = new HashMap<>();
	private List<Command> executeList;

    public void addCommand(String key, Command command) {
        commands.put(key, command);
    }

    public Command getCommand(String key) {
        return commands.get(key);
    }

    public boolean hasCommand(String key) {
        return commands.containsKey(key);
    }
    
    public void initCommands() {
    	
    }
    
    // THIS WILL GO IN THE DATAVIEWER APP ON KEYPRESS
    // if (commandManager.hasCommand(key)) {
	//    Command command = commandManager.getCommand(key);
	//    command.execute();

    // now we should try the update command and the update plot command
    // those check if key is one of the few that will require either kind of update
    // update: v
    
	//	}
}
