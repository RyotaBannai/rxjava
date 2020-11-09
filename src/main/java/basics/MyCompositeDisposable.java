package basics;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyCompositeDisposable {
    public static void main(String[] args) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(Flowable.range(1, 3)
                .doOnCancel(() -> System.out.println("No.1 canceled!"))
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    Thread.sleep(100);
                    System.out.println("No.1:" + data);
                }));

        compositeDisposable.add(Flowable.range(1, 3)
                .doOnCancel(() -> System.out.println("No.2 canceled!"))
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    Thread.sleep(100);
                    System.out.println("No.2:" + data);
                }));

        try {
            Thread.sleep(150L);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        compositeDisposable.dispose(); // まとめて購読解除
    }
}
