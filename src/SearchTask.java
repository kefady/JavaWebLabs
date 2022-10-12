import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class SearchTask implements Callable<Integer> {
    private final BlockingQueue<String> queue;
    private final ExecutorService pool;
    private final File directory;
    private final File resultFile;
    private final String keyword;

    public SearchTask(BlockingQueue<String> queue, File directory, File resultFile, String keyword, ExecutorService pool) {
        this.queue = queue;
        this.directory = directory;
        this.resultFile = resultFile;
        this.keyword = keyword;
        this.pool = pool;
    }

    public Integer search(File file) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    count++;
                    queue.put(file.getPath() + " -> " + line + "\n");
                    WriteTask writeTask = new WriteTask(queue, resultFile);
                    pool.submit(writeTask);
                    System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": found a new match in the file " + file.getPath());
                }
            }
        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();

        }
        return count;
    }

    @Override
    public Integer call() {
        int count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> tasks = new ArrayList<>();
            for (File file : Objects.requireNonNull(files, "No files in directory " + directory.getPath())) {
                if (file.isDirectory()) {
                    SearchTask searchTask = new SearchTask(queue, file, resultFile, keyword, pool);
                    Future<Integer> task = pool.submit(searchTask);
                    tasks.add(task);
                } else if (file.isFile() && !file.getName().equals("Result.txt")) {
                    count += search(file);
                    System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": put a new file in list " + file.getPath());
                }
            }
            for (Future<Integer> task : tasks)
                count += task.get();
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return count;
    }
}
