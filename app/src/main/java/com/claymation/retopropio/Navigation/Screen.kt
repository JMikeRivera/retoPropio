package com.claymation.retopropio.Navigation

sealed class Screen (val route : String){
    object HomeScreen : Screen("HomeScreen")
    object LoginScreen : Screen("LoginScreen")
    object SignupScreen : Screen("SignupScreen")
    object ChatbotScreen : Screen("ChatbotScreen")
}