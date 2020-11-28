package basics.opeartors;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class MyFlatMap {
    public static void main(String[] args) {
//        testFlatMap();
//        combiner();
        converter();
    }

    private static void converter() {
        Flowable<Integer> originalFlowable = Flowable.just(1, 3, 0, 2, 5).map(number -> 10 / number);
        Flowable<Integer> flowable = originalFlowable.flatMap(
                Flowable::just,
                error -> Flowable.just(-1), // error 時には exception を返すのではなく -1 を返して全体の処理を終了する
                () -> Flowable.just(100));
        flowable.subscribe(new DebugSubscriber<>("Converter test."));
    }

    private static void combiner() {
        Flowable<String> flowable = Flowable.range(2, 2) // 2,3 が一気に入って、それぞれに対して、一回の combiner が適用される.
//                .doOnNext(System.out::println)
                .flatMap(
                        data -> Flowable.interval(100L, TimeUnit.MILLISECONDS).take(3),
                        (sourceData, newData) -> "[" + sourceData + "] " + newData);

        flowable.subscribe(new DebugSubscriber<>("test"));
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
