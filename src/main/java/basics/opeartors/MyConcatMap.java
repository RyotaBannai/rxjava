package basics.opeartors;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class MyConcatMap {
    public static void main(String[] args) {
        test();
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }
    }

    private static void test() {
        Flowable<String> flowable = Flowable.range(10, 3)
                .concatMap(sourceData ->
                        //.concatMapEager() を使うと前の処理の 500L をまつことなく次々に実行することができる
                        Flowable.interval(500L, TimeUnit.MILLISECONDS)
                                .take(2)
                                .map(data -> System.currentTimeMillis() + "ms: [" + sourceData + "] " + data));
        flowable.subscribe(new DebugSubscriber<>("concatMap test. data in order."));
    }
}
