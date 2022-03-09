package by.ganevich.io;

import by.ganevich.io.factory.CommandFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandExecutor {
    private final CommandFactory commandFactory;

    CommandResult executeCommand(CommandDescriptor commandDescriptor) {
        CommandResult commandResult = commandFactory.getCommand(commandDescriptor).execute(commandDescriptor);
        return commandResult;
    }
}
