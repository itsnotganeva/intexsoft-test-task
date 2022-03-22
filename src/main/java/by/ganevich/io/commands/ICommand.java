package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;


public interface ICommand {
    String getCommandName();
    ICommand setParameters(CommandDescriptor commandDescriptor);
    CommandResult execute(CommandDescriptor commandDescriptor);
}
