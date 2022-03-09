package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;

import java.util.Map;

public abstract class BaseCommand implements ICommand {

    public String getDescription() {
        return null;
    }

    public Object execute(CommandDescriptor commandDescriptor) {
        Map<String, String> parameters = commandDescriptor.getParameters();
        if (parameters.containsValue("help")) {
           return getDescription();
        } else {
            return doExecute(parameters);
        }
    }

    public Object doExecute(Map<String, String> parameters){
        return null;
    }
}
