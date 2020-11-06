package basics;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try {
            sample();
        } catch (Exception e) {
            System.out.println("Error occurred.");
            e.printStackTrace();
        }
    }

    public static void firstTest() throws Exception {
        Flowable<String> flowable = Flowable.just("hello", "world");
        flowable.subscribe(System.out::println);
    }

    public static void sample() throws Exception {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                // String[] array = {"Hello, world", "Good morning, world!"};
                // List<String> data = Arrays.asList(array); // 固定長の配列を変換しているため add できない. List<String> list = new ArrayList<>(); list.add("test");
                // data.stream() ...
                Stream<String> initialStream = Stream.of("Hello, world", "Good morning, world!");
                BreakableForEach.forEach(initialStream, (elem, breaker) -> {
                    if (emitter.isCancelled()) { // 購読が解除されたかどうか判定・create を使っている場合は実装者の責任・rx がやってくれるのは通知を止めるだけであって、loop 処理を停止してくれるわけでない.
                        breaker.stop();
                    }
                    emitter.onNext(elem);
                });

                emitter.onComplete(); // 何らかの処理を行う場合は、onComplete の前にかく。エラーが発生した場合, Subscriber にエラー通知が届かない場合があるため.
            }
        }, BackpressureStrategy.BUFFER); // 超過したデータはバッファ.


        flowable.observeOn(Schedulers.computation()) // Subscriber の処理を別スレッドで実行.
                .subscribe(new Subscriber<String>() { // 購読
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1L); // 最初に受け取るデータ数を指定・onSubscribe メソッドの最後にかく
                    }

                    @Override
                    public void onNext(String s) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(String.format("current thread is %s and data is %s", threadName, s));

                        // ここで次に受け取るデータをリクエスト.
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(String.format("thread %s is finished.", threadName));
                    }
                });
        Thread.sleep(500L);
    }

    // ref: https://www.baeldung.com/java-break-stream-foreach
    private static class BreakableForEach {
        public static class Breaker {
            public static boolean shouldBreak = false;

            public void stop() {
                shouldBreak = true;
            }

            boolean get() {
                return shouldBreak;
            }
        }

        public static <T> void forEach(Stream<T> stream, BiConsumer<T, Breaker> consumer) {
            Spliterator<T> spliterator = stream.spliterator(); // spliterator が stream を分けて iterate してくれる関数
            boolean hadNext = true;
            Breaker breaker = new Breaker();
            while (hadNext && !breaker.get()) {
                hadNext = spliterator.tryAdvance(elem -> {
                    consumer.accept(elem, breaker);
                });
            }
        }
    }
}
