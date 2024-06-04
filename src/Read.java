import java.io.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Read {
    public void execute() {
        String fileName = "notebook.txt";
        String data = null;
        File file = new File(fileName);
        try {
            if (file.exists()){
                data = decryptFromFile(fileName);
            } else {
                data = "Нет записей для чтения";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);

    }
    public static String decryptFromFile(String fileName) throws Exception {
        String aesKeyName = "key.txt";
        File key = new File(aesKeyName);
        //читаем ключ
        byte[] keyBytes;
        try (FileInputStream fis = new FileInputStream(aesKeyName)) {
            keyBytes = fis.readAllBytes();
        }
        String encodedKey = new String(keyBytes);
        //используем ключ
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] fileContent;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            fileContent = fis.readAllBytes();
        }

        byte[] decryptedBytes = cipher.doFinal(fileContent);
        return new String(decryptedBytes);
    }
}

