package by.ganevich.io.commands;

import by.ganevich.csv.archiver.Archiver;
import by.ganevich.csv.importCsv.CsvImporter;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class ImportCsvCommand extends BaseCommand {

    private final String commandName = "importCsv";

    private final Archiver archiver;
    @Autowired
    private List<CsvImporter> importers;

    @Override
    public String getDescriptionValue() {
        String description = "importCsv";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        archiver.unpack();

        for (CsvImporter importer : importers) {
            importer.importCsv();
        }

        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Import is complete!");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        return this;
    }
}
