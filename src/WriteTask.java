import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class WriteTask implements Runnable {
    private final BlockingQueue<String> queue;
    private final File resultFile;

    public WriteTask(BlockingQueue<String> queue, File resultFile) {
        this.queue = queue;
        this.resultFile = resultFile;
    }

    @Override
    public void run() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(resultFile, "rw");
            while (true) {
                if (queue.isEmpty()) {
                    randomAccessFile.close();
                    return;
                } else {
                    String line = queue.take();
                    randomAccessFile.seek(resultFile.length());
                    randomAccessFile.writeBytes(line);
                    System.out.println(Thread.currentThread().getName() + " -> " + new Date() + ": written new line to file " + resultFile.getPath());
                }
            }
        } catch (InterruptedException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
