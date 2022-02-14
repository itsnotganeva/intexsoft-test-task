package by.ganevich.io.subinteractive;

import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Set;

@Component
@AllArgsConstructor
public class TransactionMenuInteractive {

    private TransactionService transactionService;
    private ClientService clientService;

    public void transactionMenuPrint() {
        System.out.println("1 - Show transactions of client");
        System.out.println("2 - Back");
    }

    public Set<Transaction> showTransactions() {
        System.out.println("Enter the name of the client whose transactions you want to view:");
        Client client = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the date before sending (YYYY-MM-DD):");
        String dateBeforeStr = InputManager.inputString();

        Date dateBefore = Date.valueOf(dateBeforeStr);

        System.out.println("Enter the date after sending:");
        String dateAfterStr = InputManager.inputString();

        Date dateAfter = Date.valueOf(dateAfterStr);

        Set<Transaction> transactions =
                transactionService.readAllByDateAndSender(dateBefore, dateAfter, client);

        System.out.println(transactions.toString());
        return transactions;

    }


}
