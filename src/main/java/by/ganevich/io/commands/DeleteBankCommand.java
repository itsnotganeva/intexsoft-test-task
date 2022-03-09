package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
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
    public String getDescription() {
        String description = "deleteBank bankName=?";
        return description;
    }

    @Override
    public Object doExecute(Map<String, String> parameters) {
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bankService.removeBank(bank);

        return null;
    }
}
