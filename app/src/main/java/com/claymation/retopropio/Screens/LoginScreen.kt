import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.claymation.retopropio.R
import com.claymation.retopropio.Viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController?) {
    val loginViewModel: LoginViewModel = viewModel()

    // A Column layout to arrange the UI elements vertically
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

        // Email TextField
        OutlinedTextField(
            value = loginViewModel.email.observeAsState("").value,
            onValueChange = { loginViewModel.updateEmail(it) },
            label = {
                Text(
                    text = "Email",
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold // Custom font weight for the label
                    )
                )
            },
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

        // Password TextField with visual transformation for hiding text
        OutlinedTextField(
            value = loginViewModel.password.observeAsState("").value,
            onValueChange = { loginViewModel.updatePassword(it) },
            label = {
                Text(
                    text = "Password",
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold // Custom font weight for the label
                    )
                )
            },
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
        loginViewModel.errorMessage.observeAsState().value?.let { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Login Button
        Button(
            onClick = {
                loginViewModel.login() // Call the login function
                if (loginViewModel.errorMessage.value == null) {
                    navController?.navigate("HomeScreen") // Navigate if no error
                }
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

        // Sign Up button (optional)
        TextButton(
            onClick = {
                // Navigate to sign up screen
                navController?.navigate("SignupScreen")
            },
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF539EFF))
        ) {
            Text(text = "Don't have an account? Sign Up")
        }
    }
}