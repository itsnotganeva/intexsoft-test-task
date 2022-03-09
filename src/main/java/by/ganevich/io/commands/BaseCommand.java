package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;

import java.util.Map;

public abstract class BaseCommand implements ICommand {

    public CommandResult getDescription() {
        return null;
    }

    public CommandResult execute(CommandDescriptor commandDescriptor) {
        Map<String, String> parameters = commandDescriptor.getParameters();
        if (parameters.containsValue("help")) {
           return getDescription();
        } else {
            return doExecute(parameters);
        }
    }

    public CommandResult doExecute(Map<String, String> parameters){
        return null;
    }
}
