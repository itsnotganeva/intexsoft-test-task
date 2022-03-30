package by.ganevich.io.commands;

import by.ganevich.csv.archiver.Archiver;
import by.ganevich.csv.importCsv.BankAccountImporter;
import by.ganevich.csv.importCsv.BankImporter;
import by.ganevich.csv.importCsv.ClientImporter;
import by.ganevich.csv.importCsv.TransactionImporter;
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

    private final Archiver archiver;
    private final BankImporter bankImporter;
    private final ClientImporter clientImporter;
    private final BankAccountImporter bankAccountImporter;
    private final TransactionImporter transactionImporter;

    @Override
    public String getDescriptionValue() {
        String description = "importCsv";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        archiver.unpack();

        bankImporter.importCsv("importBanks.csv");
        clientImporter.importCsv("importClients.csv");
        bankAccountImporter.importCsv("importBankAccounts.csv");
        transactionImporter.importCsv("importTransactions.csv");

        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Import is complete!");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        return this;
    }
}
