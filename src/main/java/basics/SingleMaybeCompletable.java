package basics;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class SingleMaybeCompletable {
    public static void main(String[] args) {
        runSingle();
    }

    private static void runSingle() {
        Single<DayOfWeek> single = Single.create(emitter -> {
            emitter.onSuccess(LocalDate.now().getDayOfWeek());
        });

        single.subscribe(new SingleObserver<DayOfWeek>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // Do nothing
            }

            @Override
            public void onSuccess(@NonNull DayOfWeek dayOfWeek) {
                System.out.println(dayOfWeek);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error=" + e);
            }
        });
    }
}
