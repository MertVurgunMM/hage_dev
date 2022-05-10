package com.practice.hagekotlin

import com.practice.hagekotlin.screen.launcher.LauncherViewModel
import com.practice.hagekotlin.screen.login.LoginActivity
import com.practice.hagekotlin.storage.AccessState
import com.practice.hagekotlin.storage.Credentials
import org.junit.Test

class MockedCredentials(override val state: AccessState) : Credentials {

    override fun put(firstName: String, lastName: String, personalNo: String) {
    }

    override fun get(block: (firstName: String?, lastName: String?, personalNo: String?) -> Unit) {
    }
}

class LauncherViewModelTest {

    @Test
    fun test_checkCredentials_userUnauthorized_screenStateToLogin() {
        val credentials = MockedCredentials(AccessState.UNAUTHORIZED)
        val viewModel = LauncherViewModel(credentials)

        viewModel.checkCredentials()

        assert(viewModel._screenState.value == LoginActivity::class.java)
    }

}