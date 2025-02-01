package data.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.*;

public class DropboxService {

    // Attributo per il token di accesso
    private String accessToken="sl.u.AFinF7f6OJ4w-1kw-5K-Icm64BJ6j1GA9YMPSEBnkf9QoVSZuuSMD6kvToPwCrFd8gY28I41aVD4gSCHBr0ikEQZJKImQFyt0yLN1a9Q9HY-8xU0U9BYbh8hXW0AN-M45PZtCNcvluLUCsSJ1dM5tshVZwJozWxet0XJSr4CtzlN-qP2EybeGB14QnC5xaSDoTV5ioiFl0PNExne_la_hbT71AKPXZatudKQmGwbbYwgTJA0oP59wwYF0poNkNrx0W1tm1zv_4DPwY0kjM8texnsGX_Z17wrG_dcVJfCh0m-OTDBMebIzJWBM6N7LDX43_V3WCKFuBpBRBf9SLMGL8dlXBStDf3-Y55pTNGxxwj7cMR3GEll0IMnYK2A4ZJ901eMfugn0ovtjj_CFss8idL9JGTUOVSMF7AtNSq6D2DwlrW3iYCZY_GrH2z8Szr5BMpIdPZH1ttWyebO7_OOPvraAmcHgyYj2nuFl04XYnBJ3SIc9wH9GJ4cKoI4Ba4Asraff6MDgaHU-oa1fyb7fHyipKIIOxEpvIwMGSlL5Ivu4De9z6KZg6NURZPL_3b9eSbXWbiwmVbm0-uIGUvsqbl8ciBPzNZyCSIN_3frnY-UmUGmRFiy-EEGC23W5MLTs1VeQJW5_izPngCfinFETbI8VZaHwMZBclQ83CmrIDyl1pmXTXLpCTZZLbhHkaV5LyH_X7hX7YkzLttK9OnynkaS3hhXaSf1Aoe71PRWLKgm1xgzFB6Q5wZQYhIYSXm-VdVOY8aawsB66mlTt7midsc9YeAs87KfgHaylL22DZ8nKK4bgJ4CERZoqww4YAyLRO301lPZHzWuW6R2OlYWTmyqj1jocmSBtu2I-VYqfKrwW1b-X7uEe7BPzvyzFVohYDsP7HCIdEOLX0NQwFZWghxzG_kMEbe0Ew-z6DkwnFb3tpf3kPT6Ec3ULXn5rFxayYEuLBuCPVM6BEv02ztnDOru4EBBibf0EAXiK7xje6sPwHrrkSoheFDmUBtXWfhXjE6i_Tr3kZ9F3z0Qi5vsJuLtx1SQ5_2D3OgkY5wj5t0vHTwW2Q7ZDX__4LsUT11p4xyoMBqOfowWzwHNNp-Sbqmh3iftIss_QR3u6XizsS_feGprB0jGvzb4yj5aGfCUYbJho-GFm6mlGSpEF56PSN3vzcBMIH4Na1-4IzHNAilX2sRIOdgShIG8vPXMkVO5437PyrmSqQL611kAb2WMwt7w7IPUcX7GgAzO895oOb1uP7PBCJoaxDqLFB1uDKM-ZtwlJQz_WuozamaZUmpWEE50qr7WTol2I5C7rdc3NnfCVNxOZ4-JEUx-LLquQTWV95SDqC2I3CGYrYIHh2uMscZkydAGEkaT0w4cCsHuuqNcB4JIzkwxn3V6j3QonCJUphec39B-9s8V1AqHr3OekFATUmblMJZhVmVUkPaqDu-tFy_EoDVB2FVPb1eHwROrvdY";

    private DbxClientV2 client;

    // Costruttore che accetta il token di accesso come parametro
    public DropboxService() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java").build();
        this.client = new DbxClientV2(config, accessToken);
    }

    public void uploadFile(String localFilePath, String dropboxFolderPath) {
        try {
            System.out.println("Uploading file: " + dropboxFolderPath);
            // Verifica se la cartella esiste già su Dropbox
            try {
                ListFolderResult result = client.files().listFolder("");
                boolean folderExists = false;
                for (Metadata metadata : result.getEntries()) {
                    if (metadata.getPathLower().equals(dropboxFolderPath.toLowerCase()) && metadata instanceof FolderMetadata) {
                        folderExists = true;
                        break;
                    }
                }

                // Se la cartella non esiste, crea una nuova cartella
                if (!folderExists) {
                    client.files().createFolderV2(dropboxFolderPath);
                    System.out.println("Cartella creata con successo: " + dropboxFolderPath);
                } else {
                    System.out.println("La cartella esiste già: " + dropboxFolderPath);
                }
            } catch (CreateFolderErrorException e) {
                System.out.println(e.getMessage());
            }

            // Aggiungi il nome del file al percorso della cartella su Dropbox
            String dropboxFilePath = dropboxFolderPath + "/" + localFilePath.substring(localFilePath.lastIndexOf("/") + 1);

            // Verifica se il file esiste già su Dropbox
            try {
                Metadata metadata = client.files().getMetadata(dropboxFilePath);
                if (metadata != null) {
                    // Se il file esiste, lo elimina prima di caricarne uno nuovo
                    client.files().deleteV2(dropboxFilePath);
                    System.out.println("File esistente rimosso: " + dropboxFilePath);
                }
            } catch (GetMetadataErrorException e) {
                if (e.errorValue.isPath()) {
                    e.errorValue.getPathValue();
                }
            }

            // Carica il file nella cartella su Dropbox
            try (InputStream in = new FileInputStream(localFilePath)) {
                System.out.println("DROPBOX FILEPATH: " + dropboxFolderPath);
                FileMetadata metadata = client.files().uploadBuilder(dropboxFilePath)
                        .uploadAndFinish(in);
                System.out.println("File caricato con successo: " + metadata.getPathLower());
            } catch (Exception e) {
                System.out.println("Errore durante il caricamento del file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per scaricare un file da Dropbox
    public boolean download(String dropboxFilePath, String localFilePath){
        System.out.println("Downloading file: " + localFilePath);
        System.out.println("Dropbox file: "+dropboxFilePath);
        boolean result = false;
        try {
            Metadata metadat = client.files().getMetadata(dropboxFilePath);
            System.out.println("metadata "+metadat);
            if (metadat != null) {
                try {
                    // Crea la cartella di destinazione se non esiste
                    File localFile = new File(localFilePath);
                    File parentDir = localFile.getParentFile();
                    if (parentDir != null && !parentDir.exists()) {
                        parentDir.mkdirs();  // Crea la cartella se non esiste
                    }
                    dropboxFilePath+=File.separator+localFile.getName();
                    System.out.println("DROPBOX "+dropboxFilePath);
                    System.out.println("LOCAL "+localFilePath);
                    // Crea un OutputStream per scrivere il file localmente
                    try (OutputStream out = new FileOutputStream(localFilePath)) {
                        // Scarica il file da Dropbox
                        FileMetadata metadata = client.files().download(dropboxFilePath)
                                .download(out);
                        System.out.println("File scaricato con successo: " + metadata.getPathLower());
                    } catch (Exception e) {
                        System.out.println("Errore durante il download del file: " + e.getMessage());
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result= true;
            }
        }catch(DbxException e){
            result= false;
        }
        return result;
    }
}