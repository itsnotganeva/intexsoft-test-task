package by.ganevich.csv;

import au.com.bytecode.opencsv.CSVReader;
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
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvExporter {
    private final CsvClientMapper clientMapper;
    private final CsvBankMapper bankMapper;
    private final CsvBankAccountMapper bankAccountMapper;
    private final CsvTransactionMapper transactionMapper;

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    public void exportCsv() throws IOException {

        List<Client> clients = clientService.readClients();
        for (Client c : clients) {
            clientMapper.toCsv("exportClients.csv", c);
        }

        List<Bank> banks = bankService.readBanks();
        for (Bank b : banks) {
            bankMapper.toCsv("exportBanks.csv", b);
        }

        List<BankAccount> bankAccounts = bankAccountService.readAll();
        for (BankAccount b : bankAccounts) {
            bankAccountMapper.toCsv("exportBankAccounts.csv", b);
        }

        List<Transaction> transactions = transactionService.readAll();
        for (Transaction t : transactions) {
            transactionMapper.toCsv("exportTransactions.csv", t);
        }

    }
}
