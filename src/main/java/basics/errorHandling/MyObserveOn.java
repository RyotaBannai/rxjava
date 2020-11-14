package basics.errorHandling;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.IntStream;

public class MyObserveOn {
    public static void main(String[] args) {
        testObserveOn();
    }

    private static void testObserveOn() {
        Flowable<Integer> flowable = Flowable.<Integer>create(emitter -> {
            IntStream.rangeClosed(1, 100).forEach(number -> emitter.onNext(number));
            emitter.onComplete();

        }, BackpressureStrategy.BUFFER)
                .doOnSubscribe(subscription -> System.out.println("Before Subscribe started."))
                .observeOn(Schedulers.computation()); // 別のスレッドでデータを受け取る.
        // default の bufferSize は 128 (observeOn が 128 データを生産者に request)
        // buffer にデータが溜まりすぎていると、初めの onNext が実行された時に MissingBackpressureException になる.

        flowable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("subscribe started.");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("the value is: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                System.out.println(String.format("The error is: %s", sw.toString()));
            }

            @Override
            public void onComplete() {
                System.out.println("Subscribed completed.");
            }
        });

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
