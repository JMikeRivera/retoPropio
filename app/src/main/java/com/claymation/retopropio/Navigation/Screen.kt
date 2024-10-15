package com.claymation.retopropio.Navigation

import androidx.navigation.NavType
import com.claymation.retopropio.Screens.BuenasNotScreen
import com.claymation.retopropio.Screens.CasosScreen
import com.claymation.retopropio.Screens.ChatbotScreen
import com.claymation.retopropio.Screens.CivilScreen
import com.claymation.retopropio.Screens.ClientDataScreen
import com.claymation.retopropio.Screens.FamiliarScreen
import com.claymation.retopropio.Screens.GlosarioScreen
import com.claymation.retopropio.Screens.HomeScreen
import com.claymation.retopropio.Screens.LoginScreen
import com.claymation.retopropio.Screens.MalasNotScreen
import com.claymation.retopropio.Screens.MercantilScreen
import com.claymation.retopropio.Screens.NoticiasScreen
import com.claymation.retopropio.Screens.RegistroScreen
import com.claymation.retopropio.Screens.SignupScreen

sealed class Screen (val route : String){
    object HomeScreen : Screen("HomeScreen")
    object LoginScreen : Screen("LoginScreen")
    object SignupScreen : Screen("SignupScreen")
    object ChatbotScreen : Screen("ChatbotScreen")
    object ClientDataScreen :Screen("ClientData")
    object NoticiasScreen: Screen("Noticias")
    object GlosarioScreen: Screen("Glosario")
    object CasosScreen: Screen("Casos/{topic}")
    object RegistroScreen: Screen("Registro/{topic}")
    object BuenasNotScreen: Screen("Buenas")
    object MalasNotScreen: Screen("Malas")
    object FamiliarScreen: Screen("Familiar")
    object MercantilScreen: Screen("Mercantil")
    object CivilScreen: Screen("Civil")
}
