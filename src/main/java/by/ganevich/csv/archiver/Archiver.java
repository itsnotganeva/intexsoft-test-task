package by.ganevich.csv.archiver;

import by.ganevich.csv.exportCsv.CsvExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
@RequiredArgsConstructor
public class Archiver {

    private final List<CsvExporter> exporters;

    public void pack(Set<File> files) throws IOException {

        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("export(" + date.toString() + ").zip")))
        {
            for (File file : files) {
                putFileToZip(file, zout);
            }
        } catch(Exception ex){
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


    public void unpack() {

        try(ZipInputStream zin = new ZipInputStream(new FileInputStream("import.zip")))
        {
            ZipEntry entry;

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

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
