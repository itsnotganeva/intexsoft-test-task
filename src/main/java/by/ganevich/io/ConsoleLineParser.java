package by.ganevich.io;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConsoleLineParser {

    public CommandDescriptor parseInput(String input) {

        CommandDescriptor commandDescriptor = new CommandDescriptor();

        String[] command = input.split(" ");

        String commandName = command[0];

        commandDescriptor.setCommandName(commandName);

        Map<String, String> parsedCommand;

        if (command.length > 1 && command[1].equals("help")) {
            parsedCommand = new HashMap<>();
            parsedCommand.put("help", "help");
        } else {
            parsedCommand = Arrays.stream(command).map(elem -> elem.split("="))
                    .filter(elem -> elem.length == 2)
                    .collect(Collectors.toMap(e -> e[0], e -> e[1]));

        }
        commandDescriptor.setParameters(parsedCommand);

        return commandDescriptor;

      //  createClient name=Matvey type=individual
    }
}
