package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class DeleteBankCommand extends BaseCommand {

    private final String commandName = "deleteBank";

    private final BankService bankService;

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
}
