import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String directory = inputDirectory(scanner);
        System.out.print("Enter keyword -> ");
        String keyword = scanner.next();

        File resultFile = new File(directory + "\\Result.txt");
        try {
            if (!resultFile.exists()) {
                resultFile.createNewFile();
            } else {
                resultFile.delete();
                resultFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExecutorService pool = Executors.newCachedThreadPool();
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        SearchTask searchTask = new SearchTask(queue, new File(directory), keyword, pool);
        System.out.println("\nSearch started.");
        Future<Integer> task = pool.submit(searchTask);
        WriteTask writeTask = new WriteTask(queue, resultFile, task);
        pool.submit(writeTask);

        try {
            System.out.println("\nSearch completed. Found " + task.get() + " matches.");
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        pool.shutdown();

        try {
            System.out.println("Read file " + resultFile.getPath() + ":");
            System.out.print(new String(Files.readAllBytes(resultFile.toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String inputDirectory(Scanner scanner) {
        String directoryPath;
        while (true) {
            System.out.print("Enter directory -> ");
            directoryPath = scanner.next();
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                System.out.print("Directory is not exists. ");
            } else if (!directory.isDirectory()) {
                System.out.print("It is not a directory. ");
            } else if (Objects.requireNonNull(directory.listFiles()).length < 1) {
                System.out.print("Directory is empty. ");
            } else break;
        }
        return directoryPath;
    }
}
