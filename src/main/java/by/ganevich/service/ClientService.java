package by.ganevich.service;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.repository.BankAccountRepository;
import by.ganevich.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class ClientService {

    private ClientRepository clientRepository;
    private BankAccountRepository bankAccountRepository;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> readClients() {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    public Client findClientByName(String name) {
        return clientRepository.findByName(name);
    }

    public void sendMoney(BankAccount senderAccount, BankAccount recipientAccount, Double sumOfMoney) {
        Double senderSum = senderAccount.getAmountOfMoney();
        Double recipientSum = recipientAccount.getAmountOfMoney();
        if (sumOfMoney <= senderSum)
        {
            if (senderAccount.getBankProducer() == recipientAccount.getBankProducer()) {
                senderAccount.setAmountOfMoney(senderSum - sumOfMoney);
                recipientAccount.setAmountOfMoney(recipientSum + sumOfMoney);
            }
            else {

                if (senderAccount.getAccountOwner().getType() == ClientType.INDUSTRIAL){
                    Double sumToSend = sumOfMoney +
                            (sumOfMoney * senderAccount.getBankProducer().getCommission() * 2)/100;
                    senderAccount.setAmountOfMoney(senderSum - sumToSend);
                    recipientAccount.setAmountOfMoney(recipientSum + sumOfMoney);
                }
                else{
                    Double sumToSend = sumOfMoney +
                            (sumOfMoney * senderAccount.getBankProducer().getCommission())/100;
                    senderAccount.setAmountOfMoney(senderSum - sumToSend);
                    recipientAccount.setAmountOfMoney(recipientSum + sumOfMoney);
                }
            }

            bankAccountRepository.save(senderAccount);
            bankAccountRepository.save(recipientAccount);
        }

    }

    public void removeClient(Client client) {
        clientRepository.delete(client);
    }

    public Client addClientToBank(Client client, Bank bank) {
        if (Objects.isNull(client.getBanks()))
            client.setBanks(new HashSet<Bank>(){{ add(bank); }});
        else
            client.getBanks().add(bank);
        return client;
    }

}
