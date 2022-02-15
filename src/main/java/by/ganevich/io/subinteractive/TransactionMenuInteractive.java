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
import java.util.Set;

@Component
@AllArgsConstructor
public class TransactionMenuInteractive {

    private final TransactionService transactionService;
    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;

    public void transactionMenuPrint() {
        System.out.println("1 - Make a transaction");
        System.out.println("2 - Show transactions of client");
        System.out.println("3 - Back");
    }

    public void sendMoneyToClient() {

        System.out.println("Enter the name of the client from whose account the money will be sent: ");
        Client sender = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the name of the bank of account from whose the money will be sent: ");
        Bank bankOfSender = bankService.findBankByName(InputManager.inputString());

        BankAccount bankAccount1 = bankAccountService.getAccountByClientAndBank(sender, bankOfSender);

        System.out.println("Enter the name of the client to whose account the money will be sent: ");
        Client recipient = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the name of the bank of account to whose the money will be sent: ");
        Bank bankOfRecipient = bankService.findBankByName(InputManager.inputString());

        BankAccount bankAccount2 = bankAccountService.getAccountByClientAndBank(recipient, bankOfRecipient);

        System.out.println("Enter the sum of money that will be sent: ");
        Double sum = Double.parseDouble(InputManager.inputString());

        transactionService.sendMoney(bankAccount1, bankAccount2, sum);

    }


    public Set<Transaction> showTransactions() {
        System.out.println("Enter the name of the client whose transactions you want to view:");
        Client client = clientService.findClientByName(InputManager.inputString());

        System.out.println("Enter the date before sending (YYYY-MM-DD):");
        String dateBeforeStr = InputManager.inputString();

        Date dateBefore = Date.valueOf(dateBeforeStr);

        System.out.println("Enter the date after sending (YYYY-MM-DD):");
        String dateAfterStr = InputManager.inputString();

        Date dateAfter = Date.valueOf(dateAfterStr);

        Set<Transaction> transactions =
                transactionService.readAllByDateAndSender(dateBefore, dateAfter, client);

        System.out.println(transactions.toString());
        return transactions;

    }


}
