package simba.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages task storage, including reading from and writing to a file.
 * The Storage class handles operations related to saving tasks to a file
 * and loading tasks from the file.
 *
 * <p>It supports the following functionalities:
 * <ul>
 *     <li>Reading task data from a specified file path.</li>
 *     <li>Writing task data (e.g., task list) to a specified file.</li>
 * </ul>
 * </p>
 *
 * <p>For example, a task list can be printed to the console or saved to the file
 * by using the methods in this class.</p>
 */
public class Storage {
    private final String filePath;

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
    String fileToString() throws FileNotFoundException {
        File file = new File(this.filePath);
        Scanner sc = new Scanner(file);
        String result = "";
        if (!sc.hasNext()) {
            result = "Task list is empty";
        }
        while (sc.hasNext()) {
            result += sc.nextLine() + "\n";
        }
        return result;
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
