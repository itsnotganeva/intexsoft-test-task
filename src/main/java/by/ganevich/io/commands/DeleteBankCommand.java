package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankService;
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
public class DeleteBankCommand extends BaseCommand {

    private final String commandName = "deleteBank";
    private final BankService bankService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String bankName;


    @Override
    public String getDescriptionValue() {
        String description = "deleteBank bankName=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bankService.removeBank(bank);

        CommandResult commandResult = new CommandResult();
        String result = "Bank removed!";
        commandResult.setResult(result);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.bankName = commandDescriptor.getParameters().get("bankName");
        return this;
    }
}
