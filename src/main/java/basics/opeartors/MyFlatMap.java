package basics.opeartors;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class MyFlatMap {
    public static void main(String[] args) {
        testFlatMap();
    }

    private static void testFlatMap() {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .flatMap(data -> { // パフォーマンス重視で、順序を気にしない場合.
                    return Flowable.just(data)
                            .delay(1000L, TimeUnit.MILLISECONDS);
                });
        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": " + data);
        });
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
