import java.util.Scanner;
import java.util.ArrayList;

public class Simba {
    public static void main(String[] args) {
        System.out.println("Hello I am Simba.");
        System.out.println("How can I help you?");
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();
        while (true) {
            String command = sc.nextLine();
            if (command.equals("bye")) {
                System.out.println("Bye-bye!");
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + ": " + list.get(i));
                }
            } else {
                System.out.println("added: " + command);
                list.add(command);
            }
        }
    }
}
