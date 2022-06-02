package by.ganevich.service;

import by.ganevich.client.WebClient;
import by.ganevich.dto.ExchangeRateDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionService implements BaseService<Transaction> {

    private final TransactionRepository transactionRepository;
    private final BankAccountService bankAccountService;

    private final WebClient webClient;

    public void sendMoney(Integer senderAccountNumber, Integer receiverAccountNumber, Double sumOfMoney) {

        log.info("TransactionService: Send money is called.");

        BankAccount senderAccount = bankAccountService.findBankAccountByNumber(senderAccountNumber);
        BankAccount receiverAccount = bankAccountService.findBankAccountByNumber(receiverAccountNumber);

        ExchangeRateDto senderExchangeRate = webClient.getExchangeRate(senderAccount);
        ExchangeRateDto receiverExchangeRate = webClient.getExchangeRate(receiverAccount);

        Double senderSum = senderAccount.getAmountOfMoney();
        Double recipientSum = receiverAccount.getAmountOfMoney();

        if (sumOfMoney <= senderSum) {
            Double convertSum = sumOfMoney
                            * senderExchangeRate.getRateOut()
                            / receiverExchangeRate.getRateIn();

            Double sumWithCommission;

            if (senderAccount.getBankProducer().getName().equals(receiverAccount.getBankProducer().getName())) {
                sumWithCommission = sumOfMoney;
            } else {
                sumWithCommission = sumOfMoney
                        + sumOfMoney
                        * getCommission(senderAccount);
            }

            senderAccount.setAmountOfMoney(senderSum - sumWithCommission);
            receiverAccount.setAmountOfMoney(recipientSum + convertSum);

            bankAccountService.save(senderAccount);
            bankAccountService.save(receiverAccount);

            Transaction transaction = new Transaction();
            long millis = System.currentTimeMillis();
            transaction.setDate(new Date(millis));
            transaction.setAmountOfMoney(sumOfMoney);
            transaction.setSenderAccount(senderAccount);
            transaction.setReceiverAccount(receiverAccount);
            transaction.setSender(senderAccount.getOwner());
            transaction.setReceiver(receiverAccount.getOwner());
            transactionRepository.save(transaction);

            log.info("Transaction from "
                    + senderAccount.getOwner()
                    + " to " + receiverAccount.getOwner()
                    + "was carried successfully.");
        }
    }

    private Double getCommission(BankAccount bankAccount) {
        return bankAccount.getBankProducer()
                .getCommissions()
                .stream()
                .filter(commission -> commission.getClientType().equals(bankAccount.getOwner().getType()))
                .findFirst().get().getCommission();
    }

    public Set<Transaction> readAllByDateAndSender(Date dateBefore, Date dateAfter, Client client) {

        log.info("TransactionService: Read all by date and sender is called.");

        Set<Transaction> transactions =
                transactionRepository.findAllByDateBetweenAndSender(dateBefore, dateAfter, client);

        log.info("Transactions of sender " + client.getName() + " by date between " + dateBefore
                + " and " + dateAfter + "are successfully found.");

        return transactions;
    }

    public Set<Transaction> readAllByDateAndReceiver(Date dateBefore, Date dateAfter, Client client) {

        log.info("TransactionService: Read all by date and receiver is called.");

        Set<Transaction> transactions =
                transactionRepository.findAllByDateBetweenAndReceiver(dateBefore, dateAfter, client);
        log.info("Transactions of receiver " + client.getName() + " by date between " + dateBefore
                + " and " + dateAfter + "are successfully found.");

        return transactions;
    }

    public List<Transaction> readAllByClientId(Date dateBefore, Date dateAfter, Long id) {
        List<Transaction> transactions =
                transactionRepository.findAllByDateBetweenAndSenderIdOrReceiverId(dateBefore, dateAfter, id, id);

        log.info("Transactions of client " + id + " by date between " + dateBefore
                + " and " + dateAfter + "are successfully found.");
        return transactions;
    }

    public List<Transaction> readAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        log.info("The list of all transactions in successfully read.");
        return transactions;
    }

    public Transaction save(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transactions is saved");
        return savedTransaction;
    }
}
