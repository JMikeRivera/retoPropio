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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.claymation.retopropio.R
import com.claymation.retopropio.Viewmodels.ViewModel

@Composable
fun SignupScreen(navController: NavController?) {
    val viewModel: ViewModel = viewModel() // Usando el ViewModel llamado "ViewModel"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Inicializando el ViewModel para cargar los datos del DataStore
    LaunchedEffect(Unit) {
        viewModel.initialize(context)
    }

    // Observing StateFlow from ViewModel
    val name by viewModel.name.collectAsState()
    val secname by viewModel.secname.collectAsState()
    val email by viewModel.email.collectAsState()
    val age by viewModel.age.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()


    // UI layout for signup screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.bufetec),
            contentDescription = "Imagen Bufetec",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        // Name TextField
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF539EFF),
                unfocusedLabelColor = Color(0xFF539EFF),
                focusedBorderColor = Color(0xFF539EFF),
                unfocusedBorderColor = Color(0xFF539EFF)
            )

        )

        Spacer(modifier = Modifier.height(16.dp))

        // secName TextField
        OutlinedTextField(
            value = secname,
            onValueChange = { viewModel.updateSecName(it) },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF539EFF),
                unfocusedLabelColor = Color(0xFF539EFF),
                focusedBorderColor = Color(0xFF539EFF),
                unfocusedBorderColor = Color(0xFF539EFF)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Correo Electrónico") },
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

        // Age TextField
        OutlinedTextField(
            value = age,
            onValueChange = { viewModel.updateAge(it) },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF539EFF),
                unfocusedLabelColor = Color(0xFF539EFF),
                focusedBorderColor = Color(0xFF539EFF),
                unfocusedBorderColor = Color(0xFF539EFF)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Phone TextField
        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.updatePhone(it) },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color(0xFF539EFF),
                unfocusedLabelColor = Color(0xFF539EFF),
                focusedBorderColor = Color(0xFF539EFF),
                unfocusedBorderColor = Color(0xFF539EFF)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Contraseña") },
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

        // Confirm Password TextField
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.updateConfirmPassword(it) },
            label = { Text("Confirmar Contraseña") },
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

        // Error messages
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Sign Up Button
        Button(
            onClick = {
                viewModel.register(
                    context = context,
                    onSuccess = {
                        // Show success message and navigate to login screen
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
                        navController?.navigate("LoginScreen")
                    },
                    onFailure = {
                        Toast.makeText(context, "Registro fallido", Toast.LENGTH_LONG).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(text = "Registrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Already have an account? Go to login
        TextButton(onClick = {
            navController?.navigate("LoginScreen")
        },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF539EFF))
            ) {
            Text(text = "¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreen(null)
}

