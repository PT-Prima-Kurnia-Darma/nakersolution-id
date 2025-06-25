package com.nakersolutionid.nakersolutionid.features.signup


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakersolutionid.nakersolutionid.R
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .systemBarsPadding(),
    ) {
        Image(
            modifier = modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.logo),
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp, end = 24.dp),
            text = stringResource(R.string.sign_up),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        val customColors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                modifier = modifier.weight(1f),
                value = firstName,
                onValueChange = { firstName = it },
                colors = customColors,
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                placeholder = { Text(stringResource(R.string.first_name)) },
            )

            OutlinedTextField(
                modifier = modifier.weight(1f),
                value = lastName,
                onValueChange = { lastName = it },
                colors = customColors,
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                placeholder = { Text(stringResource(R.string.last_name)) },
            )
        }

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
            value = username,
            colors = customColors,
            onValueChange = { username = it },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = { Text(stringResource(R.string.username)) },
            leadingIcon = { Icon(Icons.Outlined.PersonOutline, contentDescription = stringResource(R.string.username)) }
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
            value = password,
            colors = customColors,
            onValueChange = { password = it },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = { Text(stringResource(R.string.password)) },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = stringResource(R.string.password)) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = if (isPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                    )
                }
            }
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
            value = confirmPassword,
            colors = customColors,
            onValueChange = { confirmPassword = it },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = { Text(stringResource(R.string.confirm_password)) },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = stringResource(R.string.password)) },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                        contentDescription = if (isConfirmPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                    )
                }
            }
        )

        Button(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(horizontal = 64.dp),
            onClick = {}
        ) {
            Text(stringResource(R.string.login))
        }

        TextButton(
            modifier = modifier
                .align(Alignment.CenterHorizontally),
            onClick = { },
        ) {
            Text(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onLoginClick() }
                    ),
                text = stringResource(R.string.need_login),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    NakersolutionidTheme {
        SignUpScreen(onLoginClick = {})
    }
}
