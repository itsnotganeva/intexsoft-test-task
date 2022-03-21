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
import java.util.List;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class ImportCsvCommand extends BaseCommand {

    private final String commandName = "importCsv";

    private final ClientService clientService;
    private final TransactionService transactionService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;

    @Pattern(regexp = "^clients$|^banks$|^bankAccounts$|^transactions$")
    @NotEmpty(message = "TableName must not be empty")
    private String tableName;

    @Override
    public String getDescriptionValue() {
        String description = "importCsv tableName=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) throws FileNotFoundException {
        if (parameters.get("tableName").equals("clients")) {
            CsvClientMapper clientMapper = new CsvClientMapper();
            List<Client> clients = clientMapper.toEntity("importClients.csv");
            for (Client c : clients) {
                clientService.saveClient(c);
            }
        } else if (parameters.get("tableName").equals("banks")) {
            CsvBankMapper bankMapper = new CsvBankMapper();
            List<Bank> banks = bankMapper.toEntity("importBanks.csv");
            for (Bank b : banks) {
                bankService.saveBank(b);
            }
        } else if (parameters.get("tableName").equals("bankAccounts")) {
            CsvBankAccountMapper bankAccountMapper = new CsvBankAccountMapper();
            List<BankAccount> bankAccounts = bankAccountMapper
                    .toEntity("importBankAccounts.csv");
            for (BankAccount b : bankAccounts) {
                bankAccountService.saveBankAccount(b);
            }
        } else if (parameters.get("tableName").equals("transactions")) {
            CsvTransactionMapper transactionMapper = new CsvTransactionMapper();
            List<Transaction> transactions = transactionMapper
                    .toEntity("importTransactions.csv");
            for (Transaction t : transactions) {
                transactionService.saveTransaction(t);
            }
        }
        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Import is complete!");
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.tableName = commandDescriptor.getParameters().get("tableName");
        return this;
    }
}
