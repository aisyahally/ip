package simba.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    /**
     * Initializes a new Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Prints the contents of the file to the console.
     *
     * @throws FileNotFoundException If the file does not exist.
     */
    void printFile() throws FileNotFoundException {
        File file = new File(this.filePath);
        Scanner sc = new Scanner(file);
        if (!sc.hasNext()) {
            System.out.println("\tTask list is empty");
        }
        while (sc.hasNext()) {
            System.out.println("\t" + sc.nextLine());
        }
    }

    /**
     * Writes the list of tasks to the file.
     *
     * @param list The list of tasks to write to the file.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    void writeToFile(ArrayList<Task> list) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        for (int i = 0; i < list.size(); i++) {
            int idx = i + 1;
            fw.write(idx + ". " + list.get(i) + "\n");
        }
        fw.close();
    }

}
