package basics;

import io.reactivex.rxjava3.core.*;

public class Main {
    public static void main(String[] args) {
        Flowable.just("hello world").subscribe(System.out::println);
    }
}
