package com.interview.doordashlite.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class SubscriptionConfig(
    val subscribeOnScheduler: Scheduler,
    val observeOnScheduler: Scheduler
) {
    companion object {
        fun createDefaultConfig() = SubscriptionConfig(
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )

        fun createTestConfig() = SubscriptionConfig(
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }
}