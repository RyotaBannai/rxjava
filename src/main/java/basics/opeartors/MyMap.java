package basics.opeartors;

import io.reactivex.rxjava3.core.Flowable;

public class MyMap {
    public static void main(String[] args) {
        myFlatMap();
    }

    private static void myMap() {
        Flowable<String> flowable = Flowable.just("a", "b", "c");
        flowable
                .map(String::toUpperCase)
                .subscribe(new DebugSubscriber<>("test"));
    }

    // null を取り除きたいならシンプルに filter.
    private static void myFlatMap() {
        Flowable<String> flowable = Flowable.just("a", "b", null);
        flowable
                .flatMap(Flowable::just)
                .subscribe(new DebugSubscriber<>("test"));
    }
}
