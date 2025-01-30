package data.service;

import com.dropbox.core.DbxException;
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
    private String accessToken="sl.u.AFj98SSwLDugHw5m5n-tTOlD0VJ62GQJ8TDEs5D0fh71xOH4CJKNg_B3Ks5tq-HSQULZMH_JzWTG00eCrvo5nvGEFqLEFJZVoogQvEuBw8HNkVfJ1meE9ac4GMhx1K-r1-Ym0CeieS_IQhHOZZFPmZtQmNpt9QWJ_cZysPeHXwpkv6V9IX1X6pAVZzKDqbQPyyLODMfSmtiJphY4W9z16G4HNNk_bB_FR5ukoBKoUA6-LAKBKVRwgzlx_YOzcyKl8hdvPra2PtSn_JgOXl_NDIRispD1L0gqqBFzJpsiJNBCuqGUDv3RifmRaZREw0OKMmVN7n_bmtCdS-7I5LKh_ZrzESJXpooOwR1mhdeiioei3W7a-ZY92hXbvdSpTlAL52EylqdOpa7GHXwDCKVn6PkyYWvq6m1ETX7dL4jxF8zeCXPmZ4pvWi7qD0XQcAhq7me1QLlSJInEAEi3Mli6rCRVmG7nxcY20Cqf63PTz9hy0iHcO9ncDrWrUTUBle0bF6aGhAJPakViYZQp3AaVmPkIt3096lzjjBUpogV2o2PfZ3w5jrq4vG1RjEBN3Pg_6BGxUkwb1999KaAsOqIk6BHhEhcvcMxpIZSMQkO57a1A7bv7GvBYLTwuQOym3c6U87UvbTBoK1KZ747N0ExGuouZZ7exXsYsFLmA4n4Z447rfbuM1kpwL_J9zLl4uW91nswDh1J3cmhduoyXnB1tG5wCIm0vmNnkrlBdU6-MXbeGrPrQJZqzMLS9N8MNdTQnlilBOzmRdgoD1NG7ix6FbsFUGiXjYKFqx3bNmExOKS1jDYlbhrH7A7_PUKKlYXxzwTmIPfA9YIxsQ2aedyDOMlHUgJc9LnNCYxnbYDMAoUgm_xElCUB9us_MCi5aGmzqRgMa1BMt2dcgq5fcqZDyKlENMjAYIMDnJhEkKtss1GS74zLU__6ZokcnS-HOHfJ92tjZRKNjFEObTWjD9zJW1DXggwDxsWQm9ZVG0BWYqYkasfvVmEguB5r4orqS1FciDSR9ClCksNoeJEwT1_uxb9aSubCHjh5LsfBZW68D-mTUMxgVAEbgIXWxK9zFJH0uppTJAzTNMbTzUeWXTq_CTkK0_fsUaqZxvYb8hfplXCQuztRuWIiBW9V-4nm8N4sUkrm-4sw8rdGDt-3dtzAqmfWcT9XK9_DaaS-p7D8GTWsunH-A2a6edxTy3NXfcV4bp4UvAuA9M7-gVjFByH-5-3AjTdO2qPgh-DxjOXv71RWQp4RgLvvbxq44WT9fIUIK3pLixpwowPefYqn29LX4uuCdnd-zp-VgteyRhXv2clRSB9U9Vu7UZgy9Wa7h_WFXqYp1-NXm1vEYx_iCGZW-GYnvxyCyx0LVQMOOKe08hfDfXjPsLbSbNZ1o8e2BG0ZNY7_BqFQAYuicQcKT_DeDYSf6Z-tyWaT8gXA-7FfPH7xcZfn3Sgz_zOE6qbH_vAOJ3wk";

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
    public boolean downloadFile(String dropboxFilePath, String localFilePath){
        boolean result = false;
        try {
            Metadata metadat = client.files().getMetadata(dropboxFilePath);
            if (metadat != null) {
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
                result= true;
            }
        }catch(DbxException e){
            result= false;
        }
        return result;
    }
}