package by.ganevich.io;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

@Component
public class ConsoleLineParser {

    public CommandDescriptor parseInput(String input) {

        CommandDescriptor commandDescriptor = new CommandDescriptor();

        String[] command = input.split(" ");

        String commandName = command[0] + " " + command[1];

        commandDescriptor.setCommandName(commandName);

        commandDescriptor.setParameters(new HashMap<>());

        Arrays.stream(command).skip(2)
                .forEach(c -> commandDescriptor.getParameters()
                        .put(Arrays.asList(command).indexOf(c)-2, c));

        return commandDescriptor;
    }
}
