package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;

public interface ICommand<T> {
    String getCommandName();
    T execute(CommandDescriptor commandDescriptor);
}
