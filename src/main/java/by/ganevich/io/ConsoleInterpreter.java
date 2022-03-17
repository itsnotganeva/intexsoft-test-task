package by.ganevich.io;

import by.ganevich.io.factory.CommandFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class ConsoleInterpreter {

    private final ConsoleLineParser consoleLineParser;
    private final CommandExecutor commandExecutor;
    private final ConsoleCommandResultViewer consoleCommandResultViewer;
    private final CommandFactory commandFactory;

    public void invokeCommand() {

        while (true) {

            Scanner in = new Scanner(System.in);

            String command = in.nextLine();

            if (command.isEmpty()) {
                System.err.println("Input is empty! Try again!");
            } else {
                CommandDescriptor commandDescriptor = consoleLineParser.parseInput(command);

                try {
                    CommandResult commandResult = commandExecutor.executeCommand(commandDescriptor);
                    consoleCommandResultViewer.viewResult(commandResult);
                } catch (NullPointerException e) {
                    System.err.println("Wrong input of command! Try again!");
                }
            }
        }
    }
}
