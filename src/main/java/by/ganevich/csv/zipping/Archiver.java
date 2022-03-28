package by.ganevich.csv.zipping;

import by.ganevich.csv.exportCsv.BankAccountExporter;
import by.ganevich.csv.exportCsv.BankExporter;
import by.ganevich.csv.exportCsv.ClientExporter;
import by.ganevich.csv.exportCsv.TransactionExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
public class Archiver {

    private final BankExporter bankExporter;
    private final ClientExporter clientExporter;
    private final BankAccountExporter bankAccountExporter;
    private final TransactionExporter transactionExporter;

    public void pack() throws IOException {

        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        File bankFile = bankExporter.doExport("exportBanks.csv");
        File bankAccountFile = bankAccountExporter.doExport("exportBankAccounts.csv");
        File clientFile = clientExporter.doExport("exportClients.csv");
        File transactionFile = transactionExporter.doExport("exportTransactions.csv");


        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("export(" + date.toString() + ").zip")))
        {
            putFileToZip(bankFile, zout);
            putFileToZip(bankAccountFile, zout);
            putFileToZip(clientFile, zout);
            putFileToZip(transactionFile, zout);

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }

    }


    public void putFileToZip(File file, ZipOutputStream zout) throws IOException {
        FileInputStream fis = new FileInputStream(file.getName());
        ZipEntry zipEntry = new ZipEntry(file.getName());

        zout.putNextEntry(zipEntry);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        zout.write(buffer);
        zout.closeEntry();
        fis.close();

        file.delete();
    }

}
