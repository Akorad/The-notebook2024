import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Scanner;

public class Write{
    public void execute() {
        System.out.println("Введите данные для записи");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String fileName = "notebook.txt";
        File file = new File(fileName);
        LocalDateTime Data = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String newData = Data.format(formatter);
        input =  newData+" "+input ;
        String encodedKey = null;
        try {
            encodedKey = generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        шифрование
        if (file.exists()) {
            // Если файл существует, добавляем запись
            try (FileWriter writer = new FileWriter(file, true)) {
//                    writer.write(input);
//                    writer.write(System.lineSeparator());

//                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//                keyGenerator.init(128);
//                SecretKey secretKey = keyGenerator.generateKey();
//                String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//                System.out.println("ключ " + encodedKey);
                String decryptedData = Read.decryptFromFile(fileName);
                String newInput = decryptedData + input +"\n";
                encryptToFile (newInput,encodedKey,fileName);
                System.out.println("Добавлена запись: \n" + input);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } else {
            // Если файл не существует, создаем его и записываем данные
            try (FileWriter writer = new FileWriter(file)) {
                encryptToFile (input,encodedKey,fileName);
                System.out.println("Файл " + fileName + " создан и добавлена запись: \n " + input);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void encryptToFile (String data, String encodedKey, String fileName) throws Exception{
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(encryptedBytes);
            }
    }
    //создание ключа
    public static String generateKey()  throws  Exception{
        String aesKeyName = "key.txt";
        File key = new File(aesKeyName);
        //Если ключ уже создан
        if (key.exists()){
            byte[] keyBytes;
            try (FileInputStream fis = new FileInputStream(aesKeyName)) {
                keyBytes = fis.readAllBytes();
            }
            String encodedKey = new String(keyBytes);
            return encodedKey;
        } else {
            //если ключа еще нет
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            try (FileOutputStream fos = new FileOutputStream(aesKeyName)) {
                fos.write(encodedKey.getBytes());
            }
            System.out.println("Был создан ключ \n" + encodedKey);
            return encodedKey;
        }

    }
}
