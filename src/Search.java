import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Search {
    public void execute() {
        String fileName = "notebook.txt";
        File file = new File(fileName);
        if (!file.exists()){
            System.out.println("Нет записей для чтения");
            return;
        }
        System.out.println("Введите дату в формате 'yyyy-MM-dd'");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.replace(" ","");
        List<String> out = List.of();
        try {
                String decryptedData = Read.decryptFromFile(fileName);
                out = findData(decryptedData,input);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!out.isEmpty()){
            System.out.printf("Записи по дате: \n");
            for (String line: out) {
                System.out.printf(line+"\n");
            }
        } else {
            System.out.printf("Записи не найдены \n");
        }

    }
    public static List<String> findData(String text,String data){
        String[] lines = text.split("\n");
        List<String> outList = new ArrayList<>();
        for (String line : lines){
            if (line.startsWith(data + " ")){
                outList.add(line);
            }
        }
        return outList;
    }
}
