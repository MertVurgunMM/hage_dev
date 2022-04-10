package com.practice.hagekotlin

import com.practice.hagekotlin.login.AccountRepository
import com.practice.hagekotlin.login.CredentialsMissingException
import com.practice.hagekotlin.login.UserNotFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@ExperimentalCoroutinesApi // runTest function
class LoginTest : KoinTest {

    private val accountRepository by inject<AccountRepository>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testNetworkModule)
    }

    @Test
    fun test_loginWithCredentials_emptyFirstname_failed() = runTest {
        try {
            accountRepository.login(
                firstName = null,
                lastName = TEST_USER_LAST_NAME,
                personalNo = TEST_USER_PERSONAL_NO
            )
        } catch (e: Exception) {
            assert(e is CredentialsMissingException)
            assert((e as CredentialsMissingException).type == CredentialsMissingException.Type.FIRST_NAME)
        }
    }

    @Test
    fun test_loginWithCredentials_emptyLastname_failed() = runTest {
        try {
            accountRepository.login(
                firstName = FAULTY_CREDENTIAL,
                lastName = null,
                personalNo = TEST_USER_PERSONAL_NO
            )
        } catch (e: Exception) {
            assert(e is CredentialsMissingException)
            assert((e as CredentialsMissingException).type == CredentialsMissingException.Type.LAST_NAME)
        }
    }

    @Test
    fun test_loginWithCredentials_emptyPersonalNumber_failed() = runTest {
        try {
            accountRepository.login(
                firstName = TEST_USER_FIRST_NAME,
                lastName = TEST_USER_LAST_NAME,
                personalNo = null
            )
        } catch (e: Exception) {
            assert(e is CredentialsMissingException)
            assert((e as CredentialsMissingException).type == CredentialsMissingException.Type.PERSONAL_NO)
        }
    }

    @Test
    fun test_loginWithCredentials_incorrectFirstname_failed() = runTest {
        try {
            accountRepository.login(
                firstName = FAULTY_CREDENTIAL,
                lastName = TEST_USER_LAST_NAME,
                personalNo = TEST_USER_PERSONAL_NO
            )
        } catch (e: Exception) {
            assert(e is UserNotFoundException)
            e.printStackTrace()
        }
    }

    @Test
    fun test_loginWithCredentials_incorrectLastname_failed() = runTest {
        try {
            accountRepository.login(
                firstName = TEST_USER_FIRST_NAME,
                lastName = FAULTY_CREDENTIAL,
                personalNo = TEST_USER_PERSONAL_NO
            )
        } catch (e: Exception) {
            assert(e is UserNotFoundException)
            e.printStackTrace()
        }
    }

    @Test
    fun test_loginWithCredentials_incorrectPersonalNumber_failed() = runTest {
        try {
            accountRepository.login(
                firstName = TEST_USER_FIRST_NAME,
                lastName = TEST_USER_LAST_NAME,
                personalNo = FAULTY_CREDENTIAL
            )
        } catch (e: Exception) {
            assert(e is UserNotFoundException)
            e.printStackTrace()
        }
    }

    @Test
    fun test_loginWithCredentials_correctCredentials_success() = runTest {
        try {
            val success = accountRepository.login(
                firstName = TEST_USER_FIRST_NAME,
                lastName = TEST_USER_LAST_NAME,
                personalNo = TEST_USER_PERSONAL_NO
            )
            assert(success)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun test_loginWithStoredCredentials_expired_failed() {
        assert(false)

    }

    @Test
    fun test_loginWithStoredCredentials_NotExpired_success() {
        assert(false)
    }

    private companion object {
        const val TEST_USER_FIRST_NAME = "Google"
        const val TEST_USER_LAST_NAME = "Play Store"
        const val TEST_USER_PERSONAL_NO = "009999"
        const val FAULTY_CREDENTIAL = "faultyInformation"
    }
}