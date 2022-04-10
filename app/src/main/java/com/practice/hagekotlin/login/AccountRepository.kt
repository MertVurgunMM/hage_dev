package com.practice.hagekotlin.login

import com.practice.hagekotlin.network.AccountService

interface AccountRepository

class AccountRepositoryImpl(private val service: AccountService) {

    fun login(firstName: String?, lastName: String?, personalNo: String?) {

        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || personalNo.isNullOrEmpty())
            error("Credentials missing!")

        //service.login()
    }
}