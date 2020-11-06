package basics;

import io.reactivex.rxjava3.core.*;

public class Main {
    public static void main(String[] args) {
        Flowable<String> flowable = Flowable.just("hello", "world");
        flowable.subscribe(System.out::println);
    }
}
