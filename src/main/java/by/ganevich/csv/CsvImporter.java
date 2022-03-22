package by.ganevich.csv;

import by.ganevich.csv.csvMapper.CsvBankAccountMapper;
import by.ganevich.csv.csvMapper.CsvBankMapper;
import by.ganevich.csv.csvMapper.CsvClientMapper;
import by.ganevich.csv.csvMapper.CsvTransactionMapper;
import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CsvImporter {

    private final CsvClientMapper clientMapper;
    private final CsvBankMapper bankMapper;
    private final CsvBankAccountMapper bankAccountMapper;
    private final CsvTransactionMapper transactionMapper;

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    public void importCsv() throws FileNotFoundException {

        List<Client> clients = clientMapper.toEntity("importClients.csv");
        for (Client c : clients) {
            clientService.saveClient(c);
        }

        List<Bank> banks = bankMapper.toEntity("importBanks.csv");
        for (Bank b : banks) {
            bankService.saveBank(b);
        }

        List<BankAccount> bankAccounts = bankAccountMapper
                .toEntity("importBankAccounts.csv");
        for (BankAccount b : bankAccounts) {
            bankAccountService.saveBankAccount(b);
        }

        List<Transaction> transactions = transactionMapper
                .toEntity("importTransactions.csv");
        for (Transaction t : transactions) {
            transactionService.saveTransaction(t);
        }
    }
}
