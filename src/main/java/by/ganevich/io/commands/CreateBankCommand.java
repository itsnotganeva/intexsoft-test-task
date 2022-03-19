package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.Commission;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankService;
import by.ganevich.service.CommissionService;
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
public class CreateBankCommand extends BaseCommand {

    private final BankService bankService;
    private final CommissionService commissionService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String bankName;

    @Pattern(regexp = "\\(?\\d+\\.\\d+\\)?")
    @NotEmpty(message = "Individual commission must not be empty")
    private String individualCommission;

    @Pattern(regexp = "\\(?\\d+\\.\\d+\\)?")
    @NotEmpty(message = "Industrial commission must not be empty")
    private String industrialCommission;

    private final String commandName = "createBank";

    @Override
    public String getDescriptionValue() {
        String description = "createBank bankName=? individualCommission=? industrialCommission=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {

        Bank bank = new Bank();
        bank.setName(parameters.get("bankName"));

        bankService.saveBank(bank);

        Commission individualCommission = new Commission();
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());

        Commission industrialCommission = new Commission();
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());

        individualCommission.setCommission(Double.valueOf(parameters.get("individualCommission")));
        individualCommission.setBank(bank);
        industrialCommission.setCommission(Double.valueOf(parameters.get("industrialCommission")));
        industrialCommission.setBank(bank);

        bank.getCommissions().add(individualCommission);
        bank.getCommissions().add(industrialCommission);

        commissionService.saveCommission(industrialCommission);
        commissionService.saveCommission(individualCommission);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(bank);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.bankName = commandDescriptor.getParameters().get("bankName");
        this.individualCommission = commandDescriptor.getParameters().get("individualCommission");
        this.industrialCommission = commandDescriptor.getParameters().get("industrialCommission");
        return this;
    }
}
