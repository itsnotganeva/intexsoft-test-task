package by.ganevich.io.subinteractive;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.inputmanager.InputManager;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

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
//
//        String[] subStr;
//        subStr = dateBeforeStr.split("-");
//
//        Integer[] arr = Stream.of(subStr).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
//
//        Date dateBefore = new Date(arr[0], 02, arr[2]);

        System.out.println("Enter the date after sending:");
        String dateAfterStr = InputManager.inputString();

        Date dateAfter = Date.valueOf(dateAfterStr);

//        subStr = dateAfterStr.split("-");
//
//        arr = Stream.of(subStr).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
//
//        Date dateAfter = new Date(arr[0], 02, arr[2]);

        Set<Transaction> transactions =
                transactionService.readAllByDateAndSender(dateBefore, dateAfter, client);

        System.out.println(transactions.toString());
        return transactions;

    }


}
