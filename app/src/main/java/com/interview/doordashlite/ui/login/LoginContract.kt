package com.interview.doordashlite.ui.login

interface LoginContract {

    interface View {

    }

    interface Presenter {
        fun onLoginClicked(email: String, password: String)
    }

    interface Router {
        fun startRestaurantListActivity()
    }
}