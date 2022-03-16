package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.io.CommandResult;
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
public class MakeTransactionCommand extends BaseCommand {

    private final String commandName = "makeTransaction";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @Override
    public String getDescriptionValue() {
        String description = "makeTransaction senderName=? senderAccountNumber=?"
                + " receiverName=? receiverAccountNumber=? amountOfMoney=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client sender = clientService.findClientByName(parameters.get("senderName"));
        Integer senderAccountNumber = Integer.parseInt(parameters.get("senderAccountNumber"));
//        BankAccount senderAccount = bankAccountService.getAccountByClientAndBank(sender, senderBank);

        Client receiver = clientService.findClientByName(parameters.get("receiverName"));
        Integer receiverAccountNumber = Integer.parseInt(parameters.get("receiverAccountNumber"));
//        BankAccount receiverAccount = bankAccountService.getAccountByClientAndBank(receiver, receiverBank);

        transactionService.sendMoney(senderAccountNumber,
                receiverAccountNumber, Double.valueOf(parameters.get("amountOfMoney")));

        CommandResult commandResult = new CommandResult();
        String result = "Transaction completed successfully!";
        commandResult.setResult(result);
        return commandResult;
    }

}
