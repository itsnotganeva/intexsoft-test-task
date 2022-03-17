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
        if (validate(commandDescriptor)) {
            if (parameters.containsValue("help")) {
                return getDescription();
            } else {
                try {
                    return doExecute(parameters);
                } catch (NumberFormatException e) {
                    System.err.println("Wrong input of parameters!");
                    return null;
                }
            }
        } else {
            return null;
        }

    }

    public boolean validate(CommandDescriptor commandDescriptor) {
        Map<String, String> parameters = commandDescriptor.getParameters();
        if (parameters.containsValue("")) {
            System.err.println("Fill in all command parameters");
            return false;
        } else {
            return true;
        }
    }

    public abstract CommandResult doExecute(Map<String, String> parameters);
}
