package by.ganevich.io.commands;

import by.ganevich.csv.archiver.Archiver;
import by.ganevich.csv.exportCsv.BankAccountExporter;
import by.ganevich.csv.exportCsv.BankExporter;
import by.ganevich.csv.exportCsv.ClientExporter;
import by.ganevich.csv.exportCsv.TransactionExporter;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Getter
@RequiredArgsConstructor
public class ExportCsvCommand extends BaseCommand{
    private final String commandName = "exportCsv";

    private final Archiver archiver;
    private final BankExporter bankExporter;
    private final BankAccountExporter bankAccountExporter;
    private final ClientExporter clientExporter;
    private final TransactionExporter transactionExporter;

    @Override
    public String getDescriptionValue() {
        String description = "exportCsv";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        File bankFile = bankExporter.exportCsv("exportBanks.csv");
        File bankAccountFile = bankAccountExporter.exportCsv("exportBankAccounts.csv");
        File clientFile = clientExporter.exportCsv("exportClients.csv");
        File transactionFile = transactionExporter.exportCsv("exportTransactions.csv");

        Set<File> files = new HashSet<>();
        files.add(bankFile);
        files.add(bankAccountFile);
        files.add(clientFile);
        files.add(transactionFile);

        archiver.pack(files);


        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Export is complete!");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        return this;
    }
}
