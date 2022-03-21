package by.ganevich.io.commands;

import by.ganevich.csv.csvMapper.*;
import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class ExportCsvCommand extends BaseCommand{
    private final String commandName = "exportCsv";

    private final ClientService clientService;
    private final TransactionService transactionService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;

    @Pattern(regexp = "^clients$|^banks$|^bankAccounts$|^transactions$")
    @NotEmpty(message = "TableName must not be empty")
    private String tableName;

    @Override
    public String getDescriptionValue() {
        String description = "exportCsv tableName=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws IOException {

        if (parameters.get("tableName").equals("clients")) {
            CsvClientMapper clientMapper = new CsvClientMapper();
            List<Client> clients = clientService.readClients();
            for (Client c : clients) {
                clientMapper.toCsv("exportClients.csv", c);
            }
        } else if (parameters.get("tableName").equals("banks")) {
            CsvBankMapper bankMapper = new CsvBankMapper();
            List<Bank> banks = bankService.readBanks();
            for (Bank b : banks) {
                bankMapper.toCsv("exportBanks.csv", b);
            }
        } else if (parameters.get("tableName").equals("bankAccounts")) {
            CsvBankAccountMapper bankAccountMapper = new CsvBankAccountMapper();
            List<BankAccount> bankAccounts = bankAccountService.readAll();
            for (BankAccount b : bankAccounts) {
                bankAccountMapper.toCsv("exportBankAccounts.csv", b);
            }
        } else if (parameters.get("tableName").equals("transactions")) {
            CsvTransactionMapper transactionMapper = new CsvTransactionMapper();
            List<Transaction> transactions = transactionService.readAll();
            for (Transaction t : transactions) {
                transactionMapper.toCsv("exportTransactions.csv", t);
            }
        }
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Export is complete!");
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.tableName = commandDescriptor.getParameters().get("tableName");
        return this;
    }
}
