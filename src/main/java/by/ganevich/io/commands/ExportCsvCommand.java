package by.ganevich.io.commands;

import by.ganevich.csv.exportCsv.BankAccountExporter;
import by.ganevich.csv.exportCsv.BankExporter;
import by.ganevich.csv.exportCsv.ClientExporter;
import by.ganevich.csv.exportCsv.TransactionExporter;
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

    private final BankExporter bankExporter;
    private final ClientExporter clientExporter;
    private final BankAccountExporter bankAccountExporter;
    private final TransactionExporter transactionExporter;

    @Override
    public String getDescriptionValue() {
        String description = "exportCsv";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        bankExporter.doExport("exportBanks.csv");
        clientExporter.doExport("exportClients.csv");
        bankAccountExporter.doExport("exportBankAccounts.csv");
        transactionExporter.doExport("exportTransactions.csv");

        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Export is complete!");
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        return this;
    }
}
