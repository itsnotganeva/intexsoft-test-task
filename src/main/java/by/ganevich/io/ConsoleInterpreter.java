package by.ganevich.io;

import by.ganevich.io.commands.ICommand;
import by.ganevich.io.factory.CommandFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class ConsoleInterpreter {

    private final ConsoleLineParser consoleLineParser;
    private final CommandExecutor commandExecutor;
    private final ConsoleCommandResultViewer consoleCommandResultViewer;
    private final CommandFactory commandFactory;

    public void invokeCommand() {

        Map<String, ICommand> commands = commandFactory.collectCommands();

        while (true) {
            Scanner in = new Scanner(System.in);

            String command = in.nextLine();

            CommandDescriptor commandDescriptor = consoleLineParser.parseInput(command);

            Object commandResult = commandExecutor.executeCommand(commandDescriptor, commands);

            consoleCommandResultViewer.viewResult(commandResult);
        }
    }
}
