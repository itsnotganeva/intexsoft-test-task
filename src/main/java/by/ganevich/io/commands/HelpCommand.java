package by.ganevich.io.commands;

import by.ganevich.io.CommandDescriptor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class HelpCommand implements ICommand {

    private final String commandName = "help";

    @Override
    public Object execute(CommandDescriptor commandDescriptor) {

        String helpCommand = "createBank: command to create new bank \n"
        + "readBanks: command to read all banks \n"
        + "updateBank: command to update bank \n"
        + "deleteBank: command to delete bank \n\n"
        + "createClient: command to create new client \n"
        + "readClients: command to read all clients \n"
        + "updateClient: command to update client \n"
        + "deleteClient: command to delete client \n"
        + "addClientToBank: command to add client to bank and create a new bank account \n\n"
        + "makeTransaction: command to make transaction \n"
        + "readTransactions: command to read transactions of client \n\n"
        + "readBankAccounts: command to read all bank accounts of client \n\n"
        + "exit: command to exit from application";

        return helpCommand;
    }

}
