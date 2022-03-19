package by.ganevich.io.commands;

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
import javax.validation.constraints.Size;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class MakeTransactionCommand extends BaseCommand {

    private final String commandName = "makeTransaction";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @Size(min = 5, max = 5)
    @NotEmpty(message = "Sender account number commission must not be empty")
    private String senderAccountNumber;

    @Size(min = 5, max = 5)
    @NotEmpty(message = "Receiver account number commission must not be empty")
    private String receiverAccountNumber;

    @Pattern(regexp = "\"[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?\"")
    @NotEmpty(message = "Amount of money must not be empty")
    private String amountOfMoney;


    @Override
    public String getDescriptionValue() {
        String description = "makeTransaction senderAccountNumber=? "
                + "receiverAccountNumber=? amountOfMoney=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {

        Integer senderAccountNumber = Integer.parseInt(parameters.get("senderAccountNumber"));

        Integer receiverAccountNumber = Integer.parseInt(parameters.get("receiverAccountNumber"));

        transactionService.sendMoney(senderAccountNumber,
                receiverAccountNumber, Double.valueOf(parameters.get("amountOfMoney")));

        CommandResult commandResult = new CommandResult();
        String result = "Transaction completed successfully!";
        commandResult.setResult(result);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.senderAccountNumber = commandDescriptor.getParameters().get("senderAccountNumber");
        this.receiverAccountNumber = commandDescriptor.getParameters().get("receiverAccountNumber");
        this.amountOfMoney = commandDescriptor.getParameters().get("amountOfMoney");
        return this;
    }
}
