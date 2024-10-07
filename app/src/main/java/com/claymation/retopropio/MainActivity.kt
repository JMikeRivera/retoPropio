package com.claymation.retopropio

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.claymation.retopropio.Screens.HomeScreen
import com.claymation.retopropio.Screens.SignupScreen
import com.claymation.retopropio.ui.theme.RetoPropioTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetoPropioTheme {

                val navController = rememberNavController()

                AppNavigation(navController)
            }
        }
    }
}


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "LoginScreen" // Start with the login screen
    ) {
        // Login Screen
        composable("LoginScreen") {
            LoginScreen(navController)
        }

        // Signup Screen
        composable("SignupScreen") {
            SignupScreen(navController)
        }

        // Home Screen
        composable("HomeScreen") {
            HomeScreen(navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    AppNavigation(navController)
}