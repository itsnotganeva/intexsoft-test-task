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
import java.util.HashMap;
import java.util.Set;

@Component
@AllArgsConstructor
@Getter
public class ReadTransactionsCommand implements ICommand{

    private final String commandName = "read transactions";

    private final TransactionService transactionService;
    private final ClientService clientService;

    //read transactions sent/received Matvey 2022-02-27 2022-03-07
    @Override
    public Set<Transaction> execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client client = clientService.findClientByName(parameters.get(1));

        Date dateBefore = Date.valueOf(parameters.get(2));
        Date dateAfter = Date.valueOf(parameters.get(3));

        Set<Transaction> transactions = null;

        if (parameters.get(0).equals("sent")) {
            transactions =
                    transactionService.readAllByDateAndSender(dateBefore, dateAfter, client);
        } else if (parameters.get(0).equals("received")) {
            transactions =
                    transactionService.readAllByDateAndReceiver(dateBefore, dateAfter, client);
        }

        return transactions;
    }
}
