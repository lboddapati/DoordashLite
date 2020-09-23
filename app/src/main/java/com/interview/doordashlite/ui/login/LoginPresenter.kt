package com.interview.doordashlite.ui.login

import android.content.SharedPreferences
import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.datalayer.LOGIN_TOKEN_KEY
import com.interview.doordashlite.models.LoginResponse
import io.reactivex.observers.DisposableSingleObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginPresenter(
    private val router: LoginContract.Router,
    private val subscriptionManager: LifecycleAwareSubscriptionManager
): LoginContract.Presenter, KoinComponent {

    private val dataRepository: DataRepository by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onLoginClicked(email: String, password: String) {
        subscriptionManager.subscribe(
            dataRepository.login(email, password),
            object: DisposableSingleObserver<LoginResponse>() {
                override fun onSuccess(response: LoginResponse) {
                    saveToken(response.token)
                    router.startRestaurantListActivity()
                }

                override fun onError(e: Throwable) {
                    // TODO
//                    view.displayError(errorType)
                    // 1. onLoginClickec_Success_verifyTokenIsSaved
                }
            }
        )
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString(LOGIN_TOKEN_KEY, token).apply()
    }
}