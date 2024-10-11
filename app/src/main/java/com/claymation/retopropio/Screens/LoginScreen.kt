package com.claymation.retopropio.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.claymation.retopropio.R
import com.claymation.retopropio.Viewmodels.ViewModel

@Composable
fun LoginScreen(navController: NavController?) {
    val viewModel: ViewModel = viewModel() // Usando el ViewModel llamado "ViewModel"
    val context = LocalContext.current

    // Observing LiveData from the ViewModel
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val errorMessage by viewModel.errorMessage.observeAsState(null)

    // UI layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.bufetec),
            contentDescription = "Imagen Bufetec",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        // Email input
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF539EFF),
                unfocusedLabelColor = Color(0xFF539EFF),
                focusedBorderColor = Color(0xFF539EFF),
                unfocusedBorderColor = Color(0xFF539EFF)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF539EFF),
                unfocusedLabelColor = Color(0xFF539EFF),
                focusedBorderColor = Color(0xFF539EFF),
                unfocusedBorderColor = Color(0xFF539EFF)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error message (if any)
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Login Button
        Button(
            onClick = {
                viewModel.login(
                    context = context,
                    onSuccess = {
                        // Navigate to HomeScreen on successful login
                        navController?.navigate("HomeScreen")
                    },
                    onFailure = {
                        Toast.makeText(context, "Login fallido", Toast.LENGTH_LONG).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(
                text = "Log In",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sign Up button
        TextButton(
            onClick = {
                navController?.navigate("SignupScreen")
            },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF539EFF))
        ) {
            Text(text = "¿Aun no tienes cuenta? Registrate")
        }

        Button(
            onClick = {
                viewModel.loginAsGuest(
                    context = context,
                    onSuccess = {
                        navController?.navigate("HomeScreen")
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(
                text = "Ingresar como Invitado",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}
