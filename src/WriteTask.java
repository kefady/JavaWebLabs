import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class WriteTask implements Runnable {
    private final BlockingQueue<String> queue;
    private final File resultFile;
    private final Future<Integer> searchTask;

    public WriteTask(BlockingQueue<String> queue, File resultFile, Future<Integer> searchTask) {
        this.queue = queue;
        this.resultFile = resultFile;
        this.searchTask = searchTask;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(resultFile, "rw");
            while (true) {
                if (searchTask.isDone() && queue.isEmpty()) {
                    break;
                } else {
                    String line = queue.take();
                    randomAccessFile.seek(resultFile.length());
                    randomAccessFile.writeBytes(line);
                    System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": written new line to file " + resultFile.getPath());
                    Thread.sleep(1);
                }
            }
            randomAccessFile.close();
        } catch (InterruptedException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
