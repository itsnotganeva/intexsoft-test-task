package by.ganevich.io;

import by.ganevich.io.commands.ICommand;
import by.ganevich.io.factory.CommandFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class CommandExecutor {
    private final CommandFactory commandFactory;

    Object executeCommand(CommandDescriptor commandDescriptor, Map<String, ICommand> commands) {
        Object commandResult = commandFactory.getCommand(commandDescriptor, commands).execute(commandDescriptor);
        return commandResult;
    }
}
