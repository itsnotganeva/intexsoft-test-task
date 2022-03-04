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

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class MakeTransactionCommand implements ICommand {

    private final String commandName = "makeTransaction";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @Override
    public Transaction execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "makeTransaction senderName=? senderBankName=?" +
                    " receiverName=? receiverBankName=? amountOfMoney=?";

            System.out.println(help);
            return null;
        }

        Client sender = clientService.findClientByName(parameters.get("senderName"));
        Bank senderBank = bankService.findBankByName(parameters.get("senderBankName"));
        BankAccount senderAccount = bankAccountService.getAccountByClientAndBank(sender, senderBank);

        Client receiver = clientService.findClientByName(parameters.get("receiverName"));
        Bank receiverBank = bankService.findBankByName(parameters.get("receiverBankName"));
        BankAccount receiverAccount = bankAccountService.getAccountByClientAndBank(receiver, receiverBank);

        transactionService.sendMoney(senderAccount,
                receiverAccount, Double.valueOf(parameters.get("amountOfMoney")));

        return null;

    }
}
