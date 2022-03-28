package by.ganevich.io.commands;

import by.ganevich.csv.zipping.Archiver;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class ExportCsvCommand extends BaseCommand{
    private final String commandName = "exportCsv";

    private final Archiver archiver;

    @Override
    public String getDescriptionValue() {
        String description = "exportCsv";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        archiver.pack();

        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Export is complete!");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        return this;
    }
}
