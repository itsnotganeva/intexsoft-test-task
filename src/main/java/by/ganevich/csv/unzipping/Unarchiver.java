package by.ganevich.csv.unzipping;

import by.ganevich.csv.importCsv.BankAccountImporter;
import by.ganevich.csv.importCsv.BankImporter;
import by.ganevich.csv.importCsv.ClientImporter;
import by.ganevich.csv.importCsv.TransactionImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@RequiredArgsConstructor
public class Unarchiver {

    private final BankImporter bankImporter;
    private final ClientImporter clientImporter;
    private final BankAccountImporter bankAccountImporter;
    private final TransactionImporter transactionImporter;


    public void unpack() {

        try(ZipInputStream zin = new ZipInputStream(new FileInputStream("import.zip")))
        {
            ZipEntry entry;
            String name;

            while((entry=zin.getNextEntry())!=null){
                File file = new File(entry.getName());
                FileOutputStream  os = new FileOutputStream(file);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    os.write(c);
                }
                os.flush();
                zin.closeEntry();
                os.close();
            }

            bankImporter.doImport("importBanks.csv");
            clientImporter.doImport("importClients.csv");
            bankAccountImporter.doImport("importBankAccounts.csv");
            transactionImporter.doImport("importTransactions.csv");
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }
}

