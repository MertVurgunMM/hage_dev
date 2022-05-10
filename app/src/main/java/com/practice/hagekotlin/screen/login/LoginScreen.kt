package com.practice.hagekotlin.screen.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.practice.hagekotlin.R
import com.practice.hagekotlin.screen.login.LoginState.Failed.Reason.*

@Composable
fun LoginScreen(
    uiState: State<LoginState>,
    submitBehavior: (String, String, String) -> Unit,
    onSuccessBehavior: () -> Unit
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var personalNumber by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.margin_large)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val localFocus = LocalFocusManager.current
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(text = stringResource(id = R.string.first_name))
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    localFocus.moveFocus(FocusDirection.Down)
                }
            ))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small)))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(text = stringResource(id = R.string.last_name))
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    localFocus.moveFocus(FocusDirection.Down)
                }
            ))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small)))

        TextField(
            value = personalNumber,
            onValueChange = { personalNumber = it },
            label = {
                Text(text = stringResource(id = R.string.personal_number))
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && personalNumber.isNotEmpty())
                        submitBehavior(firstName, lastName, personalNumber)
                }
            ))

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small)))

        Button(
            enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && personalNumber.isNotEmpty(),
            onClick = {
                submitBehavior(firstName, lastName, personalNumber)
            }) {
            Text(text = stringResource(id = R.string.sing_in))
        }
    }

    when (uiState.value) {
        LoginState.Authorized -> onSuccessBehavior.invoke()
        is LoginState.Failed -> {

            Toast.makeText(
                LocalContext.current,
                when ((uiState.value as LoginState.Failed).reason) {
                    CONNECTIVITY -> R.string.error_no_connection
                    TIMEOUT -> R.string.error_timeout
                    INCORRECT_CREDENTIALS -> R.string.error_no_user
                },
                Toast.LENGTH_LONG
            ).show()
        }
        LoginState.Initial -> Unit
        LoginState.Loading -> {

        }
    }
}