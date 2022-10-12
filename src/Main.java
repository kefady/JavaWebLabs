import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
            resultFile.delete();
            resultFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExecutorService pool = Executors.newCachedThreadPool();
        SearchTask counterMath = new SearchTask(new File(directory), keyword);
        Future<ArrayList<String>> task = pool.submit(counterMath);

        try {
            RandomAccessFile resultFileWriter = new RandomAccessFile(resultFile, "rw");
            ArrayList<String> result = task.get();
            if (result.isEmpty()) {
                resultFileWriter.writeBytes("Not found a match.");
            } else {
                for (String line : result) {
                    resultFileWriter.seek(resultFileWriter.length());
                    resultFileWriter.writeBytes(line + "\n");
                }
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        pool.shutdown();

        try {
            System.out.println("\nRead file " + resultFile.getPath() + ":");
            System.out.println(new String(Files.readAllBytes(resultFile.toPath())));
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
