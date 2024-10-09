package com.claymation.retopropio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.claymation.retopropio.Screens.ChatbotScreen
import com.claymation.retopropio.Screens.ClientDataScreen
import com.claymation.retopropio.Screens.GlosarioScreen
import com.claymation.retopropio.Screens.HomeScreen
import com.claymation.retopropio.Screens.LoginScreen
import com.claymation.retopropio.Screens.NoticiasScreen
import com.claymation.retopropio.Screens.SignupScreen
import com.claymation.retopropio.ui.theme.RetoPropioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetoPropioTheme {
                val navController = rememberNavController()

                // Incluye el AppNavGraph directamente aquí
                AppNavGraph(navController)
            }
        }
    }
}

// AppNavGraph: Define la navegación para la aplicación
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "LoginScreen" // Pantalla de inicio
    ) {
        // Pantalla de login
        composable("LoginScreen") {
            LoginScreen(navController)
        }

        // Pantalla de registro
        composable("SignupScreen") {
            SignupScreen(navController)
        }

        // Pantalla principal
        composable("HomeScreen") {
            HomeScreen(navController)
        }

        // Pantalla del ChatBot
        composable("ChatBotScreen") {
            ChatbotScreen( navController)  // Puedes pasar el ViewModel si es necesario
        }

        // Pantalla del Perfil de Usuario
        composable("ClientData") {
            ClientDataScreen( navController)  // Puedes pasar el ViewModel si es necesario
        }

        // Pantalla de Noticias
        composable("Noticias") {
            NoticiasScreen( navController)  // Puedes pasar el ViewModel si es necesario
        }

        // Pantalla de Glosario
        composable("Glosario") {
            GlosarioScreen( navController)  // Puedes pasar el ViewModel si es necesario
        }


    }
}

// Preview para diseño
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    AppNavGraph(navController)
}
