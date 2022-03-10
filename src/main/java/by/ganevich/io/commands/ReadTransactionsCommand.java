package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
@Getter
public class ReadTransactionsCommand extends BaseCommand {

    private final String commandName = "readTransactions";

    private final TransactionService transactionService;
    private final ClientService clientService;

    @Override
    public String getDescriptionValue() {
        String description = "readTransactions type=sent/received clientName=? "
                + "fromDate=YYYY-MM-DD toDate=YYYY-MM-DD";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        Date dateBefore = Date.valueOf(parameters.get("fromDate"));
        Date dateAfter = Date.valueOf(parameters.get("toDate"));

        Set<Transaction> transactions = null;

        if (parameters.get("type").equals("sent")) {
            transactions =
                    transactionService.readAllByDateAndSender(dateBefore, dateAfter, client);
        } else if (parameters.get("type").equals("received")) {
            transactions =
                    transactionService.readAllByDateAndReceiver(dateBefore, dateAfter, client);
        }

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(transactions);
        return commandResult;
    }

}
