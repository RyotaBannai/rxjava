package basics.errorHandling;

import io.reactivex.rxjava3.core.Flowable;

import java.util.stream.IntStream;

public class Handle {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.<Integer> create(emitter -> {
            System.out.println("Flowable started.");
            IntStream.rangeClosed(1,3).forEach();
        })
    }
}
