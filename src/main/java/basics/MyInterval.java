package basics;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

import java.util.concurrent.TimeUnit;

public class MyInterval {
    public static void main(String[] args) {
        runOnDifferentThread();
        try {
            Thread.sleep(1000L); // sleep して処理を待ってあげないと、メインスレッドが先に終了して interval の処理が実行されない.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void runOnDifferentThread() {
        System.out.println("started.");
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .subscribe(new ResourceSubscriber<Long>() {
                    private String threadName;

                    @Override
                    public void onNext(Long aLong) {
                        threadName = Thread.currentThread().getName();
                        System.out.println(String.format("current thread is %s", threadName));
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(String.format("current thread is %s", threadName));
                        System.out.println("onCompleted.");
                    }
                });
        System.out.println("end.");
    }
}
