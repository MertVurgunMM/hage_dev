package com.practice.hagekotlin.screen.login

import com.practice.hagekotlin.network.AccountService
import com.practice.hagekotlin.storage.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AccountRepository {
    suspend fun login(firstName: String?, lastName: String?, personalNo: String?): Boolean
}

class AccountRepositoryImpl(
    private val service: AccountService,
    private val credentials: Credentials
) : AccountRepository {

    override suspend fun login(firstName: String?, lastName: String?, personalNo: String?) =
        withContext(Dispatchers.IO) {

            when {
                firstName.isNullOrEmpty() ->
                    throw CredentialsMissingException(CredentialsMissingException.Type.FIRST_NAME)
                lastName.isNullOrEmpty() ->
                    throw CredentialsMissingException(CredentialsMissingException.Type.LAST_NAME)
                personalNo.isNullOrEmpty() ->
                    throw CredentialsMissingException(CredentialsMissingException.Type.PERSONAL_NO)
                else -> {
                    val response = service.login(firstName, lastName, personalNo)

                    if (!response.success) {
                        throw UserNotFoundException(response.error.toString())
                    }
                    credentials.put(firstName, lastName, personalNo)
                    return@withContext response.success
                }
            }
        }
}

class CredentialsMissingException(val type: Type) : Exception() {
    enum class Type {
        FIRST_NAME, LAST_NAME, PERSONAL_NO
    }
}

class UserNotFoundException(val error: String) : Exception()