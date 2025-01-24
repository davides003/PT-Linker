package data.service;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import data.DAO.AutenticazioneDAO;
import data.entity.Professionista;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DropboxService {

    // Attributo per il token di accesso
    private String accessToken="sl.u.AFclOo99u3FN4qUsZxr71c7Y61gXAGZ6w9uk1_8ufiFWIKHqxS-HfAREAGN53wUQSon0oU7qQ8nGsJegGp8LR96tcHomAqh8XxB37Pcjz2CBDqI47JYAy-cWwuA4i00TcaMIfO7OGslywQ1zPkkPv5HRH4UIOJFqCgk6ZvW62GXrXivFSD1IwIuhuFlq6QmvmXkHGVVovbRAg_bPcQwUU6wQKYAdehV48eKSOSYHLP-TVwDw_4ZedjinmKSQgQ-Cy5Nh3ZtJLcagPMHbWI1gGntNgBt0buJYe_aFTjV-8m4OPXzU5LvabpjIGXmriTRghI0nOaKrZi1AyniEjxHJQqh1A6AK5NMaNeJ5lWQpvn96Nt1Eog5gtxXO8dw_TJCajYHpLoYvvVUlaOWw5kU8dbwXz3am47mMyBrHOA3tAjQsct0CzzhEx9_3tgMt2c8gR66fH6_xwf0hg7WBtbayY1_j_eMVNghbVRUweEpSJy3K3ptY-Vqd-5jP-868dLGCRc2poZ7Uj4RR7OzvKMH2rl1E6VYs5j5_Z-GQEZtuXmgFaGCsuk4SD2z2LUyjfHc3YiCzCTtS6ceT8BToiaZzEqeXnpT2HaYs9I8yRPUR2hq7ivRZNwmDXsFmG3gMmKv4NbUdDQv9qkDJWq3iDdHOLdMEz8REVV7JKGvwHIwFjR7mJS6-LigQFtWJvpUAy_E44S5B2rHFXwDZRySY1l7A86oxukbtWDKW5AKaY5XsDtvkBPUv-A1apzbjWqI4gFEv9ClcVUYB2EHLcvhROYE842WMFWqMBDA1t_GGCHCjLYcbSaLbcUp0oKhrQPlQL7qlKP-zXzHemgmLPpmJCn2Unq0UmXpQe8sgDqdkdQGswwAqW7zmujMr9hHDbdXyEGzST96MlIuAd0HqkHlCrX5zmAyXF9WMY5cbcRpnLcN31Sxey809ilVr0HhxP5mQH8V42haafwUZ7zJPIfqSmPyglU4IQ63SUJXhPkLO_Clb9noUBXo29_GB-IJ_64s3Wa7z05-KOvgLl2Tfnw1kUapUHz7vlz6jteDjRxrSRf23pZzxh0pHeWBFw03gwHqaVW173ruh9Pm1RxbbpL0rqUowmeb0CqYM9SuR15zQVULdfhEZwS6fnqAASVSV3QMysIIYkK7Iflt9aIt-KFg1v4r3zTJrfEGgSCTD8uzaimL-HHT4AGicmvwd54nKAds-mU1Qvjv4phO5xXRCYZxIEBxzOu2Q-8A5BajJb5om0J45Ce8uVfMA6dgwhlaW6NW1Y0Wh5F76PSYNoua0_FlrOHJThBx-XhAghNvgBIl3LaGxMVk5k0OovqzDlaw07IqNhFnElGv1DB8hCm3la11MY_VQ0o7kOi-CSRIuo4w18B4dbcdzVO9enu2UcTa6DIvDMgFcbylXuAvbomN1Zlvi1_nAlHr_Nsu1aCS8-riMDrBeYZQeLXqda7QAnW0ieVEYuPpiFAo";
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
    public void downloadFile(String dropboxFilePath, String localFilePath) {
        try {
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
    }
}