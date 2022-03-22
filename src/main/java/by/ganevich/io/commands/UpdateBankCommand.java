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
public class UpdateBankCommand extends BaseCommand {

    private final String commandName = "updateBank";

    private final BankService bankService;
    private final CommissionService commissionService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String bankName;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String newBankName;

    @Pattern(regexp = "\"[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?\"")
    @NotEmpty(message = "Individual commission must not be empty")
    private String newIndividualCommission;

    @Pattern(regexp = "\"[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?\"")
    @NotEmpty(message = "Industrial commission must not be empty")
    private String newIndustrialCommission;


    @Override
    public String getDescriptionValue() {
        String description = "updateBank bankName=? newBankName=? "
                + "newIndividualCommission=? newIndustrialCommission=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bank.setName(parameters.get("newBankName"));

        bankService.saveBank(bank);

        Commission individualCommission = commissionService.findByBankAndClientType(bank, ClientType.INDIVIDUAL);
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(Double.valueOf(parameters.get("newIndividualCommission")));
        individualCommission.setBank(bank);

        Commission industrialCommission = commissionService.findByBankAndClientType(bank, ClientType.INDUSTRIAL);
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(Double.valueOf(parameters.get("newIndustrialCommission")));
        industrialCommission.setBank(bank);

        commissionService.saveCommission(individualCommission);
        commissionService.saveCommission(industrialCommission);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(bank);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.bankName = commandDescriptor.getParameters().get("bankName");
        this.newBankName = commandDescriptor.getParameters().get("newBankName");
        this.newIndividualCommission = commandDescriptor.getParameters().get("newIndividualCommission");
        this.newIndustrialCommission = commandDescriptor.getParameters().get("newIndustrialCommission");
        return this;
    }
}
