package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.CommandDescriptor;
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
public class ReadTransactionsCommand implements ICommand{

    private final String commandName = "readTransactions";

    private final TransactionService transactionService;
    private final ClientService clientService;

    @Override
    public Set<Transaction> execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "readTransactions type=sent/received clientName=? fromDate=YYYY-MM-DD toDate=YYYY-MM-DD";

            System.out.println(help);
            return null;
        }

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

        return transactions;
    }
}
