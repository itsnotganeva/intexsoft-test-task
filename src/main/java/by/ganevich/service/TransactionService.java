package by.ganevich.service;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private TransactionRepository transactionRepository;
    private BankAccountService bankAccountService;
    private RateService rateService;
    private CommissionService commissionService;

    public void sendMoney(BankAccount senderAccount, BankAccount recipientAccount, Double sumOfMoney) {

        Double senderSum = senderAccount.getAmountOfMoney();
        Double recipientSum = recipientAccount.getAmountOfMoney();

        if (sumOfMoney <= senderSum) {
            Double convertSum = sumOfMoney
                            * rateService.findRateByCurrency(senderAccount.getCurrency().ordinal())
                            / rateService.findRateByCurrency(recipientAccount.getCurrency().ordinal());

            if (senderAccount.getBankProducer().equals(recipientAccount.getBankProducer())) {

                senderAccount.setAmountOfMoney(senderSum - sumOfMoney);
                recipientAccount.setAmountOfMoney(recipientSum + convertSum);
            } else {
                Double sumWithCommission = sumOfMoney
                        + sumOfMoney
                        * commissionService
                        .findCommissionByClientType(senderAccount.getAccountOwner().getType().ordinal());

                senderAccount.setAmountOfMoney(senderSum - sumWithCommission);
                recipientAccount.setAmountOfMoney(recipientSum + convertSum);
            }

            bankAccountService.saveBankAccount(senderAccount);
            bankAccountService.saveBankAccount(recipientAccount);
        }

        Transaction transaction = new Transaction();
        long millis = System.currentTimeMillis();
        transaction.setDate(new Date(millis));
        transaction.setAmountOfMoney(sumOfMoney);
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(recipientAccount);
        transaction.setSender(senderAccount.getAccountOwner());
        transactionRepository.save(transaction);

    }

    public Set<Transaction> readAllByDateAndSender(Date dateBefore, Date dateAfter, Client client) {

        Set<Transaction> transactions =
                transactionRepository.findAllByDateBetweenAndSender(dateBefore, dateAfter, client);

        return transactions;
    }
}
