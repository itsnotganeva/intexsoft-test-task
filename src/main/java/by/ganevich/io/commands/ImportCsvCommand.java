package by.ganevich.io.commands;

import by.ganevich.csv.unzipping.Unarchiver;
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
public class ImportCsvCommand extends BaseCommand {

    private final String commandName = "importCsv";

    private final Unarchiver unarchiver;

    @Override
    public String getDescriptionValue() {
        String description = "importCsv";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        unarchiver.unpack();

        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Import is complete!");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        return this;
    }
}
