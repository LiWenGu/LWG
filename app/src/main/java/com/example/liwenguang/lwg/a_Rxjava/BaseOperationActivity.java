package com.example.liwenguang.lwg.a_Rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liwenguang.lwg.R;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static rx.Observable.timer;

/**
 * Created by liwenguang on 2016/11/1.
 */

public class BaseOperationActivity extends AppCompatActivity {

    private CompositeSubscription compositeSubscription;

    private Subscription longOpeSubscription;

    private ProgressBar progressBar_one;
    private ProgressBar progressBar_two;
    private Subscription baseSubscription;
    private Subscription twoMapSubscription;
    String[] data = {"1","2","3"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_rxjava_baseoperation);
        textChangeEvents();
    }

    public void a_rxjava_one_button(View view){
        progressBar_one = (ProgressBar) findViewById(R.id.a_rxjava_one_progress);
        doLongOperation();
    }

    public void a_rxjava_two_button(View view){
        progressBar_two = (ProgressBar) findViewById(R.id.a_rxjava_two_progress);
        doTwomapWithLongOperation();
    }

    public void a_rxjava_three_button(View view){
        doRepeatOperation();
    }

    public void a_rxjava_four_button(View view){
        doRepeatWhenOperation();
    }

    public void a_rxjava_five_button(View view){
        doIntervalOperation();
    }

    public void a_rxjava_six_button(View view){
        doTimerOperation();
    }

    public void a_rxjava_seven_button(View view){
        doFlatMapOperation();
    }

    public void a_rxjava_eight_button(View view){
        doConcatmapOperation();
    }

    public void a_rxjava_nine_button(View view){
        doSwitchMapOperation();
    }

    public void a_rxjava_ten_button(View view){
        doBufferOperation();
    }

    public void a_rxjava_eleven_button(View view){
        dopollingOperation();
    }

    public void a_rxjava_twelve_button(View view){
        doRetrofit();
    }

    private void doBaseOperation(){
        baseSubscription = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                for (int i=0; i<3; i++){
                    printLog("onNext" + data[i] + "in OnSubscribe");
                    subscriber.onNext(data[i]);
                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                printLog("OnCompleted in Subscriber");
            }

            @Override
            public void onError(Throwable e) {
                printLog("OnError in Subscriber");
            }

            @Override
            public void onNext(String s) {
                printLog("OnNext" + s + "in Subscriber");
            }
        });
        compositeSubscription.add(baseSubscription);
    }

    private void doLongOperation(){
        progressBar_one.setVisibility(View.VISIBLE);
        longOpeSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                int N = data.length;
                for (int i=0; i<N; i++){
                    dosomethingBlockThread();
                    printLog("onNext" + data[i] + "in OnSubcribe");
                    subscriber.onNext(data[i]);
                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }


        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                printLog("OnCompleted in Subscriber");
                progressBar_one.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                printLog("onError in Subscriber");
                progressBar_one.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNext(String s) {
                printLog("onNext " + s + " in Subscriber");
            }
        });
    }

    private void doTwomapWithLongOperation(){
        progressBar_two.setVisibility(View.VISIBLE);
        twoMapSubscription = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("OnStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i=0; i<N; i++){
                    Log.d("tag","begin for" + i);
                    printLog("OnNext" + data[i] + "in OnSubscribe");
                    dosomethingBlockThread();
                    subscriber.onNext(data[i]);
                    Log.d("tag","end for" + i);
                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String result = s + "a";
                        printLog("Map1" + result);
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String result = s + "b";
                        printLog("Map2" + result);
                        return result;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("OnCompleted in Subscriber");
                        progressBar_two.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError in Subscriber");
                        progressBar_two.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(String s) {
                        printLog("onNext " + s + " in Subscriber");
                    }
                });
    }

    private void doRepeatOperation() {
        Observable.range(1,3).repeat(2).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");
            }

            @Override
            public void onNext(Integer i) {
                printLog("Nest" + i);
            }
        });
    }

    //完成一次之后，再每隔一秒重复一次，共重复三次
    private void doRepeatWhenOperation(){
        Observable.just(1,2,3).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                //重复三次
                return observable.zipWith(Observable.range(1, 3), new Func2<Void, Integer, Integer>() {
                    @Override
                    public Integer call(Void aVoid, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        printLog("delay repeat" + integer);
                        //1秒钟重复一次
                        return timer(1, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");
            }

            @Override
            public void onNext(Integer value) {
                printLog("Next" + value);
            }
        });
    }

    //第一次是数字，第二个是单位。
    private void doIntervalOperation(){
        Observable.interval(1, TimeUnit.MILLISECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                printLog("Completed");

            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");

            }

            @Override
            public void onNext(Long aLong) {
                printLog("Next" + aLong);
            }
        });
    }

    //每隔一秒输出一个数字
    private void doTimerOperation(){
        Observable.timer(1, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");
            }

            @Override
            public void onNext(Long aLong) {
                printLog("Next" + aLong);
            }
        });
    }

    /**
     * 与map一样，都是对数据转化，map是一对一，flatmap是一个对象转化为很多类型，即实际返回
     * 的是Observable对象
     */
    private void doFlatMapOperation(){
        Bean bean = new Bean();
        Observable.just(bean)
                .flatMap(new Func1<Bean, Observable<String>>() {
                    @Override
                    public Observable<String> call(Bean bean) {
                        return Observable.from(bean.getData());
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printLog("Flat Next"+s);
                    }
                });
    }

    private void doConcatmapOperation(){
        Observable.just(10,20,30)
                .concatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //10的延迟执行时间为200毫秒，20和30的延迟执行时间为180毫秒
                        int delay = 200;
                        if (integer > 10)
                            delay = 1800;
                        return Observable.from(new Integer[]{integer, integer/2}).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printLog("Concat Next" + integer);
                    }
                });
    }

    private class Bean{
        String[] data={"1","2","3"};

        public String[] getData(){
            return data;
        }
    }

    private void doSwitchMapOperation(){
        Observable.just(10,20,30).switchMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒，20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;
                return Observable.from(new Integer[]{integer, integer/2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                printLog("Switch Next" + integer);
            }
        });
    }

    private void doBufferOperation(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i <10   ; i++) {
                    subscriber.onNext(" "+i);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })
        .buffer(3, TimeUnit.SECONDS)
        .subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {
                printLog("接受到"+strings.size()+"次");
                for (int i=0; i<strings.size(); i++){
                    printLog(strings.get(i));
                }
            }
        });
    }

    private void dosomethingBlockThread() {
        printLog("Lock...");
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void textChangeEvents() {
        EditText editText = (EditText) findViewById(R.id.a_rxjava_four_edit);
        final TextView textView = (TextView) findViewById(R.id.a_rxjava_four_text);
        RxTextView.textChangeEvents(editText)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("Error");
                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                        textView.setText(onTextChangeEvent.text().toString());
                    }
                });

    }

    private void dopollingOperation() {
        final int[] N = {0};
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                Schedulers.newThread().createWorker()
                        .schedulePeriodically(new Action0() {
                            @Override
                            public void call() {
                                subscriber.onNext(" " + (N[0]++));
                            }
                        }, 0, 1000, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                printLog("polling" +s);
            }
        });
    }

    private void doRetrofit() {

    }
    private void printLog(String s) {
        Log.i("lwg", s + getThreadMessage());
    }

    private String getThreadMessage() {
        if (Looper.myLooper() == Looper.getMainLooper())
            return " MainThread";
        else return " Not-MainThread";
    }
}
