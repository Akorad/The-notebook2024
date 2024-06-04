import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
    public void execute() {
        String fileName = "notebook.txt";
        String decryptedData = null;
        File file = new File(fileName);
        try {
            if (file.exists()){
                decryptedData = Read.decryptFromFile(fileName);
            }
            else {
                System.out.println("Нет данных для отображения статистики");
                return;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        StringBuilder data = new StringBuilder();
        String[] lines = decryptedData.split("\n");
        Map<String,Integer> dateCountMap = new HashMap<>();
        for (String line:lines){
//            поиск по дате
            String date = line.substring(0,10);
            dateCountMap.put(date, dateCountMap.getOrDefault(date,0)+1);
//            подсчет символов
            String [] parts = line.split(" ",2);
            if (parts.length == 2){
                data.append(parts[1]);
            }
        }
        String mostActiveDate = null;
        int maxCount = 0;
        for (Map.Entry<String,Integer> entry : dateCountMap.entrySet()){
            if (entry.getValue() > maxCount){
                mostActiveDate = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        int numberOfEntries = lines.length;
        int numberOfCharactersInTheContent = data.length();

        System.out.printf("Количество записей:" + String.valueOf(numberOfEntries) + "\n");
        System.out.printf("Количество Символов:" + String.valueOf(numberOfCharactersInTheContent) + "\n");
        System.out.printf("Самый активный день:" + mostActiveDate  + " Колличество записей в этот день: " + maxCount  + "\n");
    }
}
