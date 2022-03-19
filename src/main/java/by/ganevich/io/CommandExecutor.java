package by.ganevich.io;

import by.ganevich.io.commands.ICommand;
import by.ganevich.io.factory.CommandFactory;
import by.ganevich.validator.CommandValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandExecutor {
    private final CommandFactory commandFactory;
    private final CommandValidator<ICommand> commandValidator;

    CommandResult executeCommand(CommandDescriptor commandDescriptor) {
        ICommand command = commandFactory.getCommand(commandDescriptor);

        if (commandDescriptor.getParameters().containsValue("help")) {
            return command.execute(commandDescriptor);
        } else {
            command.setParameters(commandDescriptor);
            if (commandValidator.validateCommand(command).isEmpty()) {
                return command.execute(commandDescriptor);
            } else {
                CommandResult commandResult = new CommandResult();
                commandResult.setResult(commandValidator.validateCommand(command));
                return commandResult;
            }
        }

    }
}
