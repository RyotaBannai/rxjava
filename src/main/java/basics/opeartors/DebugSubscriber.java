package basics.opeartors;

import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class DebugSubscriber<T> extends DisposableSubscriber<T> {
    private String label;

    public DebugSubscriber() {
        super();
    }

    public DebugSubscriber(String string) {
        super();
        this.label = string;
    }

    @Override
    public void onNext(T data) {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + ":" + data);
        } else {
            System.out.println(threadName + ":" + label + ":" + data);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + " error:" + throwable);
        } else {
            System.out.println(threadName + ":" + label + ":" + " error:" + throwable);
        }
    }

    @Override
    public void onComplete() {
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + " completed.");
        } else {
            System.out.println(threadName + ":" + label + " completed.");
        }
    }
}

