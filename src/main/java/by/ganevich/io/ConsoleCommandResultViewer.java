package by.ganevich.io;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsoleCommandResultViewer {

    public void viewResult(CommandResult commandResult) {
        if (commandResult.getT() == null) {
            return;
        } else {
            System.out.println(commandResult.getT().toString());
        }
    }
}
