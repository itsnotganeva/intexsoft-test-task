package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
@Getter
public class MakeTransactionCommand implements ICommand {

    private final String commandName = "make transaction";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    //make transaction Matvey Alfa Max Belarus 200
    @Override
    public Transaction execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client sender = clientService.findClientByName(parameters.get(0));
        Bank senderBank = bankService.findBankByName(parameters.get(1));
        BankAccount senderAccount = bankAccountService.getAccountByClientAndBank(sender, senderBank);

        Client receiver = clientService.findClientByName(parameters.get(2));
        Bank receiverBank = bankService.findBankByName(parameters.get(3));
        BankAccount receiverAccount = bankAccountService.getAccountByClientAndBank(receiver, receiverBank);

        transactionService.sendMoney(senderAccount, receiverAccount, Double.valueOf(parameters.get(4)));

        return null;

    }
}
