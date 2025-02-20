package simba.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final File file;

    /**
     * Initializes a new Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    Storage(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    /**
     * Prints the contents of the file to the console.
     *
     * @throws FileNotFoundException If the file does not exist.
     */
    String fileToString() throws IOException {
        try {
            Scanner sc = new Scanner(this.file);
            String result = "";
            if (!sc.hasNext()) {
                result = "Task list is empty";
            }
            while (sc.hasNext()) {
                result += sc.nextLine() + "\n";
            }
            sc.close();
            return result;
        } catch (FileNotFoundException e) {
            this.writeToFile(this.readFile());
            return this.fileToString();
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

    ArrayList<Task> readFile() {
        try {
            Scanner sc = new Scanner(this.file);
            ArrayList<Task> list = new ArrayList<Task>();
            while (sc.hasNext()) {
                String currentLine = sc.nextLine();
                if (currentLine.charAt(4) == 'T') {
                    list.add(this.todoToAdd(currentLine));
                } else if (currentLine.charAt(4) == 'D') {
                    list.add(this.deadlineToAdd(currentLine));
                } else {
                    list.add(this.eventToAdd(currentLine));
                }
            }
            return list;
        } catch (FileNotFoundException e) {
            return new ArrayList<Task>();
        }
    }

    private ToDo todoToAdd(String line) {
        boolean isDone = false;
        if (line.charAt(8) == 'X') {
            isDone = true;
        }
        ToDo todo = new ToDo(line.substring(11));
        if (isDone) {
            todo.makeDone();
        }
        return todo;
    }

    private Deadline deadlineToAdd(String line) {
        boolean isDone = false;
        if (line.charAt(8) == 'X') {
            isDone = true;
        }
        int idx = 0;
        for (int i = 5; i < line.length(); i++) {
            if (line.substring(i - 5, i).equals("(by: ")) {
                idx = i;
                break;
            }
        }
        String deadlineName = line.substring(11, idx - 6);
        LocalDateTime startDate = readDateInFile(line.substring(idx, line.length() - 1));
        Deadline deadline = new Deadline(deadlineName, startDate);
        if (isDone) {
            deadline.makeDone();
        }
        return deadline;
    }

    private Event eventToAdd(String line) {
        boolean isDone = false;
        if (line.charAt(8) == 'X') {
            isDone = true;
        }
        int startIdx = 0;
        int endIdx = 0;
        for (int i = 7; i < line.length(); i++) {
            if (line.substring(i - 7, i).equals("(from: ")) {
                startIdx = i;
                break;
            }
        }
        for (int i = line.length() - 1; i >= 4; i--) {
            if (line.substring(i - 4, i).equals("to: ")) {
                endIdx = i;
                break;
            }
        }
        String eventName = line.substring(11, startIdx - 8);
        LocalDateTime startDate = readDateInFile(line.substring(startIdx, endIdx - 5));
        LocalDateTime endDate = readDateInFile(line.substring(endIdx, line.length() - 1));
        Event event = new Event(eventName, startDate, endDate);
        if (isDone) {
            event.makeDone();
        }
        return event;
    }

    private LocalDateTime readDateInFile(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }

}
