package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;

import java.util.Map;

public abstract class BaseCommand implements ICommand {

    public CommandResult getDescription() {
        CommandResult commandResult = new CommandResult();
        commandResult.setResult(getDescriptionValue());
        return commandResult;
    }

    public abstract String getDescriptionValue();

    public CommandResult execute(CommandDescriptor commandDescriptor) {
        Map<String, String> parameters = commandDescriptor.getParameters();
            if (parameters.containsValue("help")) {
                return getDescription();
            } else {
                return doExecute(parameters);
            }
    }

    public abstract CommandResult doExecute(Map<String, String> parameters);
}
