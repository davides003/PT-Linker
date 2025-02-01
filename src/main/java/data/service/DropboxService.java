package data.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.*;

public class DropboxService {

    // Attributo per il token di accesso
    private String accessToken="sl.u.AFiGZcPCJJF2qeyfehxHbkm0M8NZCyD-m5cvd_rF1J2wXbjyi8L8IcdrwrQrRQVp706qfKNzAF4PP816-Xe4Kv7nU3BHFVL0tQAcBnhGFgmT8QqRHAUrvVzq3H04povOnexg88i5DbGN91KJ4VpfpfBjkHRreBShtZ6Hlu0hYTNj7lSXavixKCFhs9fHtDGO-mxBPH4VjTGkBmUhwBizrHjmWjHPwfxE4S_aJ7knW6ZO0Ii4Iiu1tP8o9Nlz_rJ4UnZlY6sv1TBnc_OrMx8mV_ECFSsffJSlyQwSquoLubEMusRY1d4YkeeflUEd0pC2FrG1HOS6BJEZfrKk0zj8WS5eO2WULTXU8EJC8rVc1L4c3qTV1LwLLFsoYVvymyDf2kcn3iM3wh0TTKrWFvDtjzNJVIXdVJgqfXEtsor7zTQ7fp0bWcv9iUvZ4a00YsXGFmXhzB_tQ2JQN1YLZMfYqCEC1hZaMODsPvZUon-7q9h6JX2VdP0rBLxcdSiGBLniZl4NQo-rAi3KeutSt3T9OfBTiv-wlxTVAUCtTrYUIFFMGtg5SVJ9GbmztUk9mZtd9N2_QwDrlzGStheik893AJ0RvwUVHAOi42zBw3PdX1lyfONb14OVD1SsDGTwMZEYezq257uHXnE2l5_gtu4Rtmjte3PnlDqL8YeTXVBsU5087yZjSFZxhk3w8e6zgD78QCF40Zd1As65S392YUG8SGkk3bLQwrZhmBnGa78o-BqOIxnZQ3LPmE0D568-A3JH0DrDVygq9JvpNmivdlp_2HRS8V5LfJN-dKBVxnWKvS3auI5umNT3Xb46k6eaZ7iXuoWZAZyxg5IJPitXIvBELZLPmkP7srWVPx_n8AWbU0O597xE8T2vdunImiAFuIvNkMvSSK6ZgxAqFswdFIwYJNFPYEOkemPSzdWsf6cIiXDZl2ZOFzqsOTa8DGORpMYQ2A9lhDVl86qnLJT3mzcsKVNa4tHMjQWED0Vi3LjNA1vERdgz3NbD5zTtEKrILt7XfQQJInDVWezk9uwjmgi5-xcysRiOrkx0JLZoCMT7pSnaIuDjPWyDpWhDxzXivbuYG73ODWAqVKkRnTMvljEqd-bSznNk_c75TEf87Jg-QUX3ZMz_pvZ8kepWRrgUCmO8wbWxzVNodvbzwhYsCVjk6p1jFJXwl1UoapXm_MyHMWNsPeovvQWRBH9opBAedXorFGz0Lxzd7-KhSbHUbRINC0_V7JX6tttODC4XwQcQymTy9tb0VebdVaCOMzqwtWR4DcQbWFqqu6KXvOubjYBsn9tEN4IX7fkSkct0wr2n119kUSWa-2h2bJ1T3PBZC-EXiokVvUz34LSwjIKyfGma73iHo2dhdC-5s3sqnacXe6mGBdru6ckbI8i-alR4SCd35XDF0NejsR_Hlm2wI4Q3zQmag8pv3cC3XAIyJSHCY5nkiRESemItIy3OBie-dQwsUfU";

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