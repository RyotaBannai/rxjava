package basics.opeartors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class MyBuffer {
    public static void main(String[] args) {
        testOpeningAndClosingIndicators();
    }

    // reference so far https://juejin.cn/post/6844903984189145102
    // implementation in progress...
    private static void testOpeningAndClosingIndicators() {
        ObservableSource<Long> openingIndicator = Observable.interval(3, TimeUnit.SECONDS);
        ObservableSource<Long> closingIndicator = Observable.timer(1, TimeUnit.SECONDS);
        Function<Long, ObservableSource<Long>> closingFunction = (Long number) -> closingIndicator;
        // go figure.
//        Observable.intervalRange(1, 12, 0, 1, TimeUnit.SECONDS)
//                .buffer(openingIndicator, closingFunction);
    }
}
