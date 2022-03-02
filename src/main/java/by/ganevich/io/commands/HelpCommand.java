package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class HelpCommand implements ICommand{

    private final String commandName = "help command";

    @Override
    public Object execute(CommandDescriptor commandDescriptor) {

        String helpCommand = "create bank bank_name individual_commission industrial_commission \n"
        + "read banks \n"
        + "update bank bank_name new_bank_name new_individual_commission new_industrial_commission \n"
        + "delete bank bank_name \n\n"
        + "create client client_name individual/industrial \n"
        + "read clients \n"
        + "update client client_name new_client_name individual/industrial \n"
        + "delete client client_name \n"
        + "add client client_name bank_name currency(usd/eur/byn) amount_of_money \n\n"
        + "make transaction sender_name "
                + "sender_bank_name receiver_name receiver_bank_name amount_of_money \n"
        + "read transactions received/sent client_name date_before date_after \n\n"
        + "read bankAccounts client_name \n";

        return helpCommand;
    }

}
