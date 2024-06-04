import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Для отображения команд введите '#help'\nВведите команду");
        label:
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().replace(" ","").toLowerCase();
            switch (input) {
                case "#help":
                    System.out.println("'#read' - для чтения; '#write' - для записи; '#search' - для поиска записей по дате; '#statistics' - для отображении статистики; '#delete' - для удоления ключа и всех записей 'q' - для выхода");
                    System.out.println("Введите команду");
                    break;
                case "#read":
                    Read readJava = new Read();
                    readJava.execute();
                    System.out.println("Введите команду");
                    break;
                case "#write":
                    Write writeJava = new Write();
                    writeJava.execute();
                    System.out.println("Введите команду");
                    break;
                case "#search":
                    Search searchJava = new Search();
                    searchJava.execute();
                    System.out.println("Введите команду");
                    break;
                case "#statistics":
                    Statistics statisticsJava = new Statistics();
                    statisticsJava.execute();
                    System.out.println("Введите команду");
                    break;
                case "#delete":
                    String fileName = "notebook.txt";
                    String keyName = "key.txt";
                    File data = new File(fileName);
                    data.delete();
                    File key = new File(keyName);
                    key.delete();
                    System.out.println("Записная книжка отчищена \n Введите команду");
                    break;
                case "q":
                    System.out.println("До свидания");
                    break label;
                default:
                    System.out.println("Неизвестная команда, введите '#help' для отображения команд");
                    break;
            }
        }
    }
}
