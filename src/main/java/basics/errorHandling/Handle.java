package basics.errorHandling;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.IntStream;

public class Handle {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.<Integer>create(emitter -> {
            System.out.println("Flowable started.");
            IntStream.rangeClosed(1, 3).forEach(number -> {
                try {
                    if (number == 2) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Intentional no serious reason Exception."); // rethrow
                }
                emitter.onNext(number);
            });

            emitter.onComplete();
            System.out.println("Flowable completed.");

        }, BackpressureStrategy.BUFFER)
                .doOnSubscribe(subscription -> System.out.println("Before Subscribe started."))
                .retry(2); // retry two times when error has occurred.

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
                // https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                System.out.println(String.format("The error is: %s", sw.toString()));
            }

            @Override
            public void onComplete() {
                System.out.println("Subscribed completed.");
            }
        });
    }
}
