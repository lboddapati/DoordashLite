package com.interview.doordashlite.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.DisposableSubscriber
import org.koin.core.KoinComponent
import org.koin.core.inject

class LifecycleAwareSubscriptionManager(lifecycle: Lifecycle): LifecycleObserver, KoinComponent {

    private val subscriptionConfig: SubscriptionConfig by inject()
    private var disposables = CompositeDisposable()

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner) {
        if (disposables.isDisposed) disposables = CompositeDisposable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(owner: LifecycleOwner) {
        disposables.dispose()
    }

    fun <T> subscribe(
        single: Single<T>,
        observer: DisposableSingleObserver<T>
    ) = single
        .subscribeOn(subscriptionConfig.subscribeOnScheduler)
        .observeOn(subscriptionConfig.observeOnScheduler)
        .subscribeWith(observer)
        .also { disposables.add(it) }

    fun <T> subscribe(
        maybe: Maybe<T>,
        observer: DisposableMaybeObserver<T>
    ) = maybe
        .subscribeOn(subscriptionConfig.subscribeOnScheduler)
        .observeOn(subscriptionConfig.observeOnScheduler)
        .subscribeWith(observer)
        .also { disposables.add(it) }

    fun <T> subscribe(
        flowable: Flowable<T>,
        observer: DisposableSubscriber<T>
    ) = flowable
        .subscribeOn(subscriptionConfig.subscribeOnScheduler)
        .observeOn(subscriptionConfig.observeOnScheduler)
        .subscribeWith(observer)
        .also { disposables.add(it) }

    fun <T> subscribe(
        completable: Completable,
        observer: DisposableCompletableObserver
    ) = completable
        .subscribeOn(subscriptionConfig.subscribeOnScheduler)
        .observeOn(subscriptionConfig.observeOnScheduler)
        .subscribeWith(observer)
        .also { disposables.add(it) }
}