package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExitCommand implements ICommand {

    private final String commandName = "exit";

    @Override
    public Object execute(CommandDescriptor commandDescriptor) {
        System.exit(0);
        return null;
    }
}
