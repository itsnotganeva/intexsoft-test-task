package by.ganevich.service;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.repo.BankAccountRepo;
import by.ganevich.repo.BankRepo;
import by.ganevich.repo.ClientRepo;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    List<Client> clients = new ArrayList<>();

    private final ClientRepo clientRepo = ClientRepo.getInstance();
    private final BankAccountRepo bankAccountRepo = BankAccountRepo.getInstance();
    private BankRepo bankRepo = BankRepo.getInstance();

    private BankAccountService bankAccountService = new BankAccountService();
    private BankService bankService = new BankService();

    public void create(Client client) {
        clientRepo.save(client);
    }

    public List<Client> read() {
        return clientRepo.findAll();
    }

    public void addToBank(Client client, BankAccount bankAccount) {
        bankAccountRepo.save(bankAccount);

        Long bankId = bankAccount.getBankId();
        bankRepo.addClient(bankId, client.getId());

    }

    public Client readById(Long id) {
        return clientRepo.getById(id);
    }

    public void sendMoney(BankAccount senderAccount, BankAccount recipientAccount, Double sumOfMoney) {
        Double senderSum = senderAccount.getAmountOfMoney();
        Double recipientSum = recipientAccount.getAmountOfMoney();
        if (sumOfMoney <= senderSum)
        {
            if (senderAccount.getBankId() == recipientAccount.getBankId()) {
                senderAccount.setAmountOfMoney(senderSum - sumOfMoney);
                recipientAccount.setAmountOfMoney(recipientSum + sumOfMoney);
            }
            else {

                if (clientRepo.getById(senderAccount.getClientId()).getType() == ClientType.INDUSTRIAL){
                    Double sumToSend = sumOfMoney +
                            (sumOfMoney * bankService.readById(senderAccount.getBankId()).getCommission() * 2)/100;
                    senderAccount.setAmountOfMoney(senderSum - sumToSend);
                    recipientAccount.setAmountOfMoney(recipientSum + sumOfMoney);
                }
                else{
                    Double sumToSend = sumOfMoney +
                            (sumOfMoney * bankService.readById(senderAccount.getBankId()).getCommission())/100;
                    senderAccount.setAmountOfMoney(senderSum - sumToSend);
                    recipientAccount.setAmountOfMoney(recipientSum + sumOfMoney);
                }
            }

            bankAccountRepo.save(senderAccount);
            bankAccountRepo.save(recipientAccount);
        }

    }

    public void update(Client client) {
        clientRepo.save(client);
    }

    public Client findByName(String name) {
        clients = read();
        return clients.stream().filter(c -> c.getName().equals(name)).findFirst().get();
    }

    public void remove(Long id) {
        clientRepo.deleteById(id);
    }


}
