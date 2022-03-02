package by.ganevich.io;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class ConsoleInterpreter {

    private final ConsoleLineParser consoleLineParser;
    private final CommandExecutor commandExecutor;
    private final ConsoleCommandResultViewer consoleCommandResultViewer;

    public void invokeCommand() {
        while (true) {
            Scanner in = new Scanner(System.in);

            String command = in.nextLine();

            CommandDescriptor commandDescriptor = consoleLineParser.parseInput(command);

            Object commandResult = commandExecutor.executeCommand(commandDescriptor);

            consoleCommandResultViewer.viewResult(commandResult);
        }
    }
}
