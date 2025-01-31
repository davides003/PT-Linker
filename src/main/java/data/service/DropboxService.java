package data.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.*;

public class DropboxService {

    // Attributo per il token di accesso
    private String accessToken="sl.CFnwhb-8Qe2hONftlrMRKCJ6rWhU8eAiF7MuujmLF7Jj0-fdhIqp0qwtxGnFG_zVQLzWzaJUOyzLI7Qmu-Bdk8_y-aCxPEP5IU8xcjHFMe-mV79j1uE6SpwoHOsuJBh6NruPexCEPKOs";

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