package basics.opeartors;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class MyThrottle {
    public static void main(String[] args) {
//        myThrottleFirst();
        myThrottleWithTimeout();
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void myThrottleFirst() {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(10)
                .throttleFirst(1000L, TimeUnit.MILLISECONDS);
        flowable.subscribe(new DebugSubscriber<>());
        // -> 0, 4, 8
        // because allow to receive one data per 1000L milliseconds
    }

    private static void myThrottleWithTimeout() {
        Flowable<String> flowable = Flowable.create(emitter -> {
            emitter.onNext("A");
            Thread.sleep(1000L);
            emitter.onNext("B");
            Thread.sleep(200L);
            emitter.onNext("C");
            Thread.sleep(200L);
            emitter.onNext("D");
            Thread.sleep(200L);
            emitter.onNext("E");
            Thread.sleep(200L);
            emitter.onNext("F");
            Thread.sleep(1000L);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
        flowable.throttleWithTimeout(500L, TimeUnit.MILLISECONDS)
                .subscribe(new DebugSubscriber<>());

        // send only A and F because only those two has buffer after them more than 500L milliseconds
    }
}
