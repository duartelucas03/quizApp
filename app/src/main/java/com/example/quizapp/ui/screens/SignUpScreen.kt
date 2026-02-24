package com.example.quizapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.state.SignUpUiState

import com.example.quizapp.ui.theme.BlueBackgroundDark
import com.example.quizapp.ui.theme.BlueOnPrimary
import com.example.quizapp.ui.theme.RedError
import com.example.quizapp.ui.theme.QuizAppTheme
import com.example.quizapp.ui.theme.Typography

@Composable
fun SignUpScreen(

    uiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(modifier
        .fillMaxSize()
        .safeDrawingPadding()
        .pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        Text(
            text = "Cadastrando usuário",
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(12.dp)
                .fillMaxWidth(),
            style = Typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Column(
            Modifier
                .fillMaxWidth(0.8f)
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = uiState.onNameChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlueBackgroundDark,
                    unfocusedTextColor = BlueBackgroundDark,
                    focusedLeadingIconColor = BlueBackgroundDark),
                label = {
                    Text(text = "Nome")
                }
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = uiState.onEmailChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlueBackgroundDark,
                    unfocusedTextColor = BlueBackgroundDark,
                    focusedLeadingIconColor = BlueBackgroundDark),
                label = {
                    Text(text = "Email")
                }
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = uiState.onPasswordChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlueBackgroundDark,
                    unfocusedTextColor = BlueBackgroundDark,
                    focusedLeadingIconColor = BlueBackgroundDark),
                label = {
                    Text(text = "Senha")
                }
            )
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = uiState.onConfirmPasswordChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BlueBackgroundDark,
                    unfocusedTextColor = BlueBackgroundDark,
                    focusedLeadingIconColor = BlueBackgroundDark),
                label = {
                    Text(text = "Confirmar senha")
                }
            )
            Button(
                onClick = onSignUpClick,
                Modifier.fillMaxWidth()
            ) {
                Text(text = "Cadastrar")
            }
        }
        AnimatedVisibility(visible = uiState.error != null) {
            uiState.error?.let {
                Box(modifier = Modifier.fillMaxWidth()
                    .background(RedError)) {
                    Text(
                        text = it,
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        color = BlueOnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
fun SignUpScreenPreview() {
    QuizAppTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With error")
@Composable
fun SignUpScreen1Preview() {
    QuizAppTheme {
        SignUpScreen(
            uiState = SignUpUiState(
                error = "Error"
            ),
            onSignUpClick = {}
        )
    }
}
