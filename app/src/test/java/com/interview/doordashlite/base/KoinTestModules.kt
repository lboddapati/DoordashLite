package com.interview.doordashlite.base

import android.net.Uri
import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.base.SubscriptionConfig
import com.interview.doordashlite.datalayer.DataRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.koin.dsl.module
import org.mockito.ArgumentMatchers

val testApplicationModule = module {
    single<DataRepository> { mock() }
    single { SubscriptionConfig.createTestConfig() }
    single {
        mock<Picasso>().apply {
            val requestCreator = mock<RequestCreator>()
            whenever(load(ArgumentMatchers.anyString())).thenReturn(requestCreator)
            whenever(load(ArgumentMatchers.any<Uri>())).thenReturn(requestCreator)
            whenever(load(ArgumentMatchers.anyInt())).thenReturn(requestCreator)
        }
    }
    factory { LifecycleAwareSubscriptionManager(mock()) }
}