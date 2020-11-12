package basics;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;
import java.util.List;

public class FromList {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        Flowable<String> flowable = Flowable.fromIterable(list);

        flowable.subscribe(System.out::println);
    }
}
