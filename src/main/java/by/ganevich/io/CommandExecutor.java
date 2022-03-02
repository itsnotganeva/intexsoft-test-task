package by.ganevich.io;

import by.ganevich.io.factory.CommandFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandExecutor {
    private final CommandFactory commandFactory;

    Object executeCommand(CommandDescriptor commandDescriptor) {
        Object commandResult = commandFactory.getCommand(commandDescriptor).execute(commandDescriptor);
        return commandResult;
    }
}
