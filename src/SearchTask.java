import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class SearchTask implements Callable<ArrayList<String>> {
    private final File directory;
    private final String keyword;

    public SearchTask(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    public ArrayList<String> search(File file) {
        ArrayList<String> matchesInCurrentFile = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    matchesInCurrentFile.add(file.getName() + " -> " + line);
                    System.out.println(new Date() + ": found a new match in the file " + file.getPath());
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return matchesInCurrentFile;
    }

    @Override
    public ArrayList<String> call() {
        ArrayList<String> matchesInAllFiles = new ArrayList<>();
        try {
            File[] files = directory.listFiles();
            List<Future<ArrayList<String>>> tasks = new ArrayList<>();
            for (File file : Objects.requireNonNull(files, "No files in directory " + directory.getPath())) {
                if (file.isDirectory()) {
                    SearchTask searchTask = new SearchTask(file, keyword);
                    FutureTask<ArrayList<String>> task = new FutureTask<>(searchTask);
                    tasks.add(task);
                    new Thread(task).start();
                } else if (file.isFile()) {
                    matchesInAllFiles.addAll(search(file));
                }
            }
            for (Future<ArrayList<String>> task : tasks)
                matchesInAllFiles.addAll(task.get());
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return matchesInAllFiles;
    }
}
