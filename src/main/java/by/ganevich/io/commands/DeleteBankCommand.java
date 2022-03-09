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
    private final CommandResult commandResult;

    @Override
    public CommandResult getDescription() {
        String description = "deleteBank bankName=?";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bankService.removeBank(bank);

        commandResult.setT(null);
        return commandResult;
    }
}
