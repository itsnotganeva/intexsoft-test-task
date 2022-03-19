package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Map;
import java.util.Set;

@Component
@Getter
@RequiredArgsConstructor
public class ReadTransactionsCommand extends BaseCommand {

    private final String commandName = "readTransactions";

    private final TransactionService transactionService;
    private final ClientService clientService;

    @Pattern(regexp = "^sent$|^received$")
    @NotEmpty(message = "Type must not be empty")
    private String type;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String clientName;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateBefore must not be empty")
    private String dateBefore;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotEmpty(message = "DateAfter must not be empty")
    private String dateAfter;


    @Override
    public String getDescriptionValue() {
        String description = "readTransactions type=sent/received clientName=? "
                + "dateBefore=YYYY-MM-DD dateAfter=YYYY-MM-DD";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
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

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(transactions);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.type = commandDescriptor.getParameters().get("type");
        this.clientName = commandDescriptor.getParameters().get("clientName");
        this.dateBefore = commandDescriptor.getParameters().get("dateBefore");
        this.dateAfter = commandDescriptor.getParameters().get("dateAfter");
        return this;
    }
}
