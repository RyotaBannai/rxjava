package basics;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MyAtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    private void increment() {
        count.incrementAndGet();
    }

    int get() {
        return count.get();
    }

    public static void main(String[] args) {
        final MyAtomicCounter counter = new MyAtomicCounter();

        Runnable task = () -> {
            IntStream.rangeClosed(1, 10000)
                    .forEach(i -> counter.increment());
        };

        ExecutorService executorService = Executors.newCachedThreadPool(); // newCachedThreadPool: 必要に応じ、新規スレッドを作成するスレッド・プールを作成しますが、利用可能な場合には以前に構築されたスレッドを再利用

        Future<Boolean> future1 = executorService.submit(task, true); // 正常に完了した時点で指定された結果を返す = 以上終了なら true を返さない.
        Future<Boolean> future2 = executorService.submit(task, true);

        try {
            if (future1.get() && future2.get()) {
                System.out.println(counter.get());
            } else {
                System.out.println("Failed");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
