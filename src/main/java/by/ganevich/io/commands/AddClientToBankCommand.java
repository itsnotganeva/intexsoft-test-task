package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Currency;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
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
public class AddClientToBankCommand extends BaseCommand {

    private final String commandName = "addClientToBank";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String clientName;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String bankName;

    @Pattern(regexp = "^usd$|^eur$|^byn$")
    @NotEmpty(message = "Type must not be empty")
    private String currency;

    @Size(min = 5, max = 5)
    @NotEmpty(message = "Account number commission must not be empty")
    private String accountNumber;

    @Pattern(regexp = "\"[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?\"")
    @NotEmpty(message = "Amount of money must not be empty")
    private String amountOfMoney;

    @Override
    public String getDescriptionValue() {
        String description = "addClientToBank clientName=? bankName=? accountNumber=? currency=? amountOfMoney=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        BankAccount newBankAccount = new BankAccount();

        if (parameters.get("currency").equals("usd")) {
            newBankAccount.setCurrency(Currency.USD);
        } else if (parameters.get("currency").equals("eur")) {
            newBankAccount.setCurrency(Currency.EUR);
        } else if (parameters.get("currency").equals("byn")) {
            newBankAccount.setCurrency(Currency.BYN);
        }

        newBankAccount.setNumber(Integer.valueOf(parameters.get("accountNumber")));
        newBankAccount.setAmountOfMoney(Double.valueOf(parameters.get("amountOfMoney")));

        newBankAccount.setOwner(client);
        newBankAccount.setBankProducer(bank);

        clientService.saveClient(client);
        bankService.saveBank(bank);

        bankAccountService.saveBankAccount(newBankAccount);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(newBankAccount);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.bankName = commandDescriptor.getParameters().get("bankName");
        this.clientName = commandDescriptor.getParameters().get("clientName");
        this.currency = commandDescriptor.getParameters().get("currency");
        this.amountOfMoney = commandDescriptor.getParameters().get("amountOfMoney");
        this.accountNumber = commandDescriptor.getParameters().get("accountNumber");
        return this;
    }
}
