package com.geekbrains.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "MainActivity";

    private Observable<String> observable;
    private Observer<String> observer;
    private Observable<String> observableJust;
    private Observable<Long> observableInterval;
    private Observer<Long> observerInterval;
    private Observable<String> observableTake;
    private Observable<String> observableSkip;
    private Observable<Integer> observableMap;
    private Observer<Integer> observerMap;
    private Observable<String> observableDistinct;
    private Observable<Integer> observableFilter;
    private Observable<Integer> observableMerge;
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> liters = Arrays.asList("a", "b", "c", "d");
        observable = Observable.from(liters);

        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.v(LOG_TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.v(LOG_TAG, "onError = " + e);
            }

            @Override
            public void onNext(String s) {
                Log.v(LOG_TAG, "onNext = " + s);
            }
        };

        observableJust = Observable.just("a", "b", "c", "d");

        observableInterval = Observable.interval(200, TimeUnit.MILLISECONDS);

        observerInterval = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.v(LOG_TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.v(LOG_TAG, "onError = " + e);
            }

            @Override
            public void onNext(Long s) {
                Log.v(LOG_TAG, "onNext = " + s);
            }
        };

        observerMap = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.v(LOG_TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.v(LOG_TAG, "onError = " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.v(LOG_TAG, "onNext = " + s);
            }
        };

        observableTake = Observable
                .from(new String[] {"1", "2", "3", "4", "5", "6", "7"} )
                .take(2);

        observableSkip = Observable
                .from(new String[] {"1", "2", "3", "4", "5", "6", "7"} )
                .skip(2);

        Func1<String, Integer> myFuncForMap = new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s) * 2;
            }
        };

        observableMap = Observable
                .from(new String[] {"1", "2", "3", "4", "5", "6", "7"} )
                .skip(2)
                .map(myFuncForMap);

        observableDistinct = Observable
                .from(new String[] {"a", "x", "d", "a", "r", "q", "d", "s", "w"} )
                .distinct();

        Func1<Integer, Boolean> myFuncForFilter = new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer s) {
                int res = s % 3;
                return res == 0;
            }
        };

        observableFilter = Observable
                .from(new Integer[] {9, 12, 33, 17, 18, 26} )
                .filter(myFuncForFilter);

        observableMerge = Observable
                .from(new Integer[] {1,2,3,4,5,6} )
                .take(5)
                .mergeWith(Observable.from(new Integer[] {4,5,6,7,8,9} ))
                .distinct();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_error_completed:
                mSubscription = observable.subscribe(observer);
            case R.id.just:
                mSubscription = observableJust.subscribe(observer);
            case R.id.interval:
                mSubscription = observableInterval.subscribe(observerInterval);
            case R.id.take:
                mSubscription = observableTake.subscribe(observer);
            case R.id.skip:
                mSubscription = observableSkip.subscribe(observer);
            case R.id.map:
                mSubscription = observableMap.subscribe(observerMap);
            case R.id.distinct:
                mSubscription = observableDistinct.subscribe(observer);
            case R.id.filter:
                mSubscription = observableFilter.subscribe(observerMap);
            case R.id.merge:
                mSubscription = observableMerge.subscribe(observerMap);
            case R.id.unsubscribe:
                mSubscription.unsubscribe();
        }
    }
}
