package data.service;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DropboxService {
    private static final String ACCESS_TOKEN = "sl.u.AFc7IsQQrYmShXdswaPHg0r8kaFijhPdplINjFUMFGch6g0EIfohlbwpBUghCwjgGvjFPhyivoJsujAvAyx025CeWd-yDi3p-IhIPVg6AC_UYrOFtlD6iohrZGD0T5Umd4NF9N9Vvz6WNfD32BABUiHSDJodkglKhkzW_5dgBz2gqjlKBw5cvD6TZySeSvFY6kRlQ7bb-ArmI78Rp8BqjfpE0ryKKXWayPMu0LaMGOsADs_bhlysihIWLjovyYAvFcEPZRYSzUdW1WZxqiXaM0pPLmND5ByyKSfcISVLQgcTpbuOOihAl9LOJCqbxQc02uwhhBmXdddJ4iHmsx6lN6GzOc2CMzDFm6YdBxSpRE2BcB6MkVX_QEUExIPgiCDC180AOOcqy_z9sLv0uTLZj0OXadXee2-d41ZB-79NLTSQpTGaZF1nStgAkqb1tqpAVDHPiJYVEunVVt3VGveHS6CmFB5bGdPbwyfEj-bAqh9LMKuuoPpqpM_GKo-04d5mfoKJeglUHTDJs_rd6EpV8R-1s8fLTnGasXWH6C-U1IxofhE6YfR_llJHDgXb1oZnb-ceyeVyBx3sQgTXzbO2L_70euVJ4KPQoGshGpwNzKi5TLFt-rKTdHvfYYSLq22d1ourIwnQaY_QBUBk5qNSO9-1l5sGL6j8CTbq_xKRjUdovCxxR3xJhKG1CbJeA67jf6nDYrCAH1_TkcxYnJUcpPv2C8Ip2adqyEvttbOGIe6OyQboQ5djtYRCBXbkD0LghoHmdFBKOlSRYd2-e_hT2JDJI05W8SI4ucqJu8n8ySvPTRTD0A3L9PrwXRzpBbYt3VEvHhEufnoDyp3rJcB-t7U0fjl8Fstt2YFiK1oKNMsAHc8EsQgLyZWQq-jPB8Elc-iIg1adPetQnAcwvoErWiJEUXZgKTVqFRCt1rkD2uoPQpBJuznkRFqHnlw9xWGgkWHp558g-5gDrjzWcvcWenZCpAFKWP0HgBUO_8WBOYH0piOHJhsqcolx7MHSo2EXKCpFl7TYoA31e-yRkTiNXexZ1x65zENDPB4ftEQ-YNiMkXwhGo4UzXS_fE8uusZnk5ahXoyd_OMhEYG6S4XXKomjuzyHGcMYym9fREItLNFcWe_JOvsod0uREg-_dUj_Eyr5-On4c9m1nU26VO3oD4_24Yxk8zom3Tw95iwReCmGGRCL0Aa4avftldzPYCg3ywEuqtLTe3xbkd459tNkwH__5wsXdZrPzY7sj2eiQgmmuXwsA2NHcHBxKXTmA4Xt4Fh3nA0l3sFnYxq3kVp4nI1ILvpUNkr8GUHeLXa40AvnA8QJVvNnqRc74GNGsL7MSREmMf7a7eMY1BiVGUoz9YujoY3Obnh-UNFi01WfO75h4j-Y9mnncOFpeM3pmKPyluPaBAYrYm5PlArzOA6yhtuvVx6Zkq3Ur4QSCAv8MML1o0OwyyT7rjnfwoNogA6dT8Y";
    private final DbxClientV2 client;

    public DropboxService() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        this.client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    // Metodo per caricare un file
    public void uploadFile(String localFilePath, String dropboxFilePath) throws IOException {
        try (InputStream in = new FileInputStream(localFilePath)) {
            FileMetadata metadata = client.files().uploadBuilder(dropboxFilePath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
            System.out.println("File caricato con successo: " + metadata.getPathDisplay());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore durante il caricamento del file: " + e.getMessage());
        }
    }

    public void downloadFile(String dropboxFilePath, String localFilePath) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Scarica il file da Dropbox
            client.files().download(dropboxFilePath).download(outputStream);

            // Salva il file scaricato in una posizione locale
            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                fos.write(outputStream.toByteArray());
            }

            System.out.println("File scaricato con successo da Dropbox: " + dropboxFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore durante il download del file: " + e.getMessage());
        }
    }
}
