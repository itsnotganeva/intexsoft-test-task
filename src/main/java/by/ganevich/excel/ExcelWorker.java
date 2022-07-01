package by.ganevich.excel;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;
import by.ganevich.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Transactional
@RequiredArgsConstructor
public class ExcelWorker {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    public void createClientTransactionsFile(Date dateBefore, Date dateAfter, Long id) {
        Optional<Client> client = clientService.findClientById(id);
        HSSFWorkbook workbook = createExcelFile("In of " + client.get().getName(), "Out of " + client.get().getName());
        final List<BankAccount> bankAccounts = bankAccountService.findBankAccountByClientId(id);
        List<Transaction> sentTransactionsOfClient = new ArrayList<>();
        List<Transaction> receiveTransactionsOfClient = new ArrayList<>();

        bankAccounts
                .forEach(a -> transactionService.readAllByDateAndSenderAccount(dateBefore, dateAfter, a)
                        .stream()
                        .forEach(t -> addTransactionToList(t, sentTransactionsOfClient)));

        bankAccounts
                .forEach(a -> transactionService.readAllByDateAndReceiverAccount(dateBefore, dateAfter, a)
                        .stream()
                        .forEach(t -> addTransactionToList(t, receiveTransactionsOfClient)));

        AtomicInteger rowNum = new AtomicInteger();
        createRows(workbook.getSheetAt(0), rowNum, sentTransactionsOfClient);
        rowNum.set(0);
        createRows(workbook.getSheetAt(1), rowNum, receiveTransactionsOfClient);

        writeWorkbook(workbook, "src/main/resources/excel/ReportOfClient.xls");
    }

    public void createAccountTransactionsFile(Date dateBefore, Date dateAfter, BankAccount bankAccount) {
        HSSFWorkbook workbook = createExcelFile("In of " + bankAccount.getNumber(), "Out of " + bankAccount.getNumber());
        final List<Transaction> sentTransactions = transactionService
                .readAllByDateAndSenderAccount(dateBefore, dateAfter, bankAccount);
        final List<Transaction> receiveTransactions = transactionService
                .readAllByDateAndReceiverAccount(dateBefore, dateAfter, bankAccount);

        AtomicInteger rowNum = new AtomicInteger();
        createRows(workbook.getSheetAt(0), rowNum, sentTransactions);
        rowNum.set(0);
        createRows(workbook.getSheetAt(1), rowNum, receiveTransactions);

        writeWorkbook(workbook, "src/main/resources/excel/ReportOfAccount.xls");
    }

    private static HSSFWorkbook createExcelFile(String sheetName1, String sheetName2) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet1 = workbook.createSheet(sheetName1);
        HSSFSheet sheet2 = workbook.createSheet(sheetName2);
        return workbook;
    }

    private static void createSheetOfBankAccount(HSSFSheet sheet, int rowNum, Transaction transaction) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(transaction.getSenderAccount().getNumber());
        row.createCell(1).setCellValue(transaction.getSenderAccount().getOwner().getName());
        row.createCell(2).setCellValue(transaction.getDate().toString());
        row.createCell(3).setCellValue(transaction.getAmountOfMoney());
        row.createCell(4).setCellValue(transaction.getSenderAccount().getCurrency().toString());
        row.createCell(5).setCellValue(transaction.getReceiverAccount().getNumber());
        row.createCell(6).setCellValue(transaction.getReceiverAccount().getOwner().getName());
    }

    private static void createRows(HSSFSheet sheet, AtomicInteger rowNum, List<Transaction> transactions) {
        Row row = sheet.createRow(rowNum.get());
        row.createCell(0).setCellValue("Account Number of sender");
        row.createCell(1).setCellValue("Sender's name");
        row.createCell(2).setCellValue("Date");
        row.createCell(3).setCellValue("Sum of transaction");
        row.createCell(4).setCellValue("Currency");
        row.createCell(5).setCellValue("Account number of receiver");
        row.createCell(6).setCellValue("Receiver's name");
        transactions.forEach(t -> createSheetOfBankAccount(sheet, rowNum.incrementAndGet(), t));
    }

    private static List<Transaction> addTransactionToList(Transaction transaction, List<Transaction> transactions) {
        transactions.add(transaction);
        return transactions;
    }

    private static void writeWorkbook(HSSFWorkbook workbook, String path) {
        try (FileOutputStream out = new FileOutputStream(new File(path))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
