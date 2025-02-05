package data.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.*;

public class DropboxService {

    // Attributo per il token di accesso
    private String accessToken="sl.u.AFhZSf3I8iqVGK2K4SskwF9XHoLPVrzTsIr_AXD-7EjFdpNRbZdzYTKGhBP0-zMBMDAMQsNRgXr32FKpWObVzW1FKxeIEPWVgyq_6apI9U0-iq1_NJRDEvkohoMKdqLGv6gGHiVMLw_fzqIrJSvPWph1D0HECUGg9X5tSSFMAgu2ZgH15QMTavUHlnpjToEcjv443bv6ufZ1QqpEACQ5ISPQweCsJBxAHoInmapubCxTjw92Ngl-jU9WoFclPED3XFnJ3zJx6JdpSWRoMTV2tnTO2S9A2cqlFGZYR5WlwcsxkUUTTdG9gE0XaidD6Cfls6W8BeKhKe0iZWmwTlbTu-bXh1IzJAt4AnvpXV7kygnpd0Isg9Qak3qjUXTxUD338Zz7UYX1JSW6FNrmkCGZ8gu2vU2XZWZ_x5snxqKbs5VZziYnLQUS8ZmBuJtzznTawxJeNCLfcH2MON2KNNVggx3A3857JhO8_quNMnVPhMHH0Hen766Z8_Mv83C5GW6rc8B56Xen-h7Wl67YBRLbwA31VvkFlgWqJSV5mta28COkRqVzAUrRCJ1teYh5SuoQWA_El85gFBWw9WaWobGak9QJNWoFDRNIzuEdnoY7Dxrc2lxjkLbO4XLOD7Oa059tFfUNVx0XKHoK_ZZgLXdrfVpNzLF611twfIGsJlLZAVK0dxiT1IkjnxxSkOHcP6Z5tkgO2UCOwzcwU01B216ER8qyM13c8APCeI7bE3DGTyh4nGnWtlhJX81Ikbc_bQx5s_osec_Uj2_m4XZ2Nswg37veP8ikwjjwD2NeVW5EFxSGLIyOgqREYzCDSfpxoXvfaMRdyoL7r3ntZcyF0DNHNkerd32EcfPalH-EBolJk-RWz5fHfL4pIKNzCMpZ8wvy-Foxt_LAnOSVuy7wVwZk5Uk6GRDJTZusDWnxwgz-8kf-euAQhFn8wnqWAx5xv-YvcG0A4a-x9LyR7_aAZ0Q5q2XgU0FgbqjEx9qUDdDfW9YCZR9zNaWKQBueJKB9E6MSTHTI1xBY7mM1AaVKuM_2q_LyKT-XnPGNqJkwca_EgiTqGoB08D4o7UezAPS2NCPofxnV3qDR4euiLsFqRpXdu-ch_YpPjgJhcGl0sraE7dOaZjWAZQVGlZqAneQNyah5nN7dTKy4D-b94IrAqWKgQD5NcIGUYICn6Tav8ZbwE848TlUmcD2PtIHUhm2jdV1GusUqcBk0k_FftpoVIrU-HK_LZpRQwjmY5cyTgc1q23AqbHlXpdcqFzUcyim06KJst0pPiQY_49MLS6sMGlALW44SnhPbwUj0CJRKjt44SCePOgSuZHEnbNA4TuFFc8QzwZGAWrWrSVOrIfrKEYGnt30MvnMkUo2teFKHC622lTYrWXv5jILyZYmP0bi7iPOPCTFmNsUi2-JBRHhhfhmUd89F_-aCPrsZ8ZusGaB1M9Bj1G-kvvhWBG9E-9uoX9r73X8";

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