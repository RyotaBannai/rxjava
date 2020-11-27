package basics.opeartors;

import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalTime;

public class MyDefer {
    public static void main(String[] args) {
        // defer によって毎回 flowable を生成.
        Flowable<LocalTime> flowable = Flowable.defer(() -> Flowable.just(LocalTime.now()));
        flowable.subscribe(new DebugSubscriber<>("No. 1"));
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flowable.subscribe(new DebugSubscriber<>("No. 2")); // 2 sec 後の時刻を表示.
    }
}
