package com.claymation.retopropio.Screens

// Importa tu ViewModel y asigna un alias para evitar conflictos
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.claymation.retopropio.Viewmodels.ViewModel as MyViewModel

enum class TipoDerecho {
    DERECHO_CIVIL, DERECHO_PENAL, DERECHO_FAMILIAR
}

data class Caso(val nombre: String, val descripcion: String)

@Composable
fun CasosScreen(navController: NavController, topic: String) {
    val context = LocalContext.current
    val viewModel: MyViewModel = viewModel()

    // Inicializar el ViewModel si aún no lo has hecho
    LaunchedEffect(Unit) {
        viewModel.initialize(context)
    }

    // Usa 'collectAsState()' para observar 'isLoggedIn' de tipo 'StateFlow<Boolean>'
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    var selectedSection by remember { mutableStateOf<TipoDerecho?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Selecciona una Sección",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF0277BD),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        SeccionDerecho("Derecho Civil") { selectedSection = TipoDerecho.DERECHO_CIVIL }
        SeccionDerecho("Derecho Penal") { selectedSection = TipoDerecho.DERECHO_PENAL }
        SeccionDerecho("Derecho Familiar") { selectedSection = TipoDerecho.DERECHO_FAMILIAR }

        Spacer(modifier = Modifier.height(16.dp))

        selectedSection?.let {
            when (it) {
                TipoDerecho.DERECHO_CIVIL -> MostrarCasosDerechoCivil(navController, isLoggedIn)
                TipoDerecho.DERECHO_PENAL -> MostrarCasosDerechoPenal(navController, isLoggedIn)
                TipoDerecho.DERECHO_FAMILIAR -> MostrarCasosDerechoFamiliar(navController, isLoggedIn)
            }
        }
    }
}

@Composable
fun SeccionDerecho(nombreSeccion: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE1F5FE)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = nombreSeccion,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF0288D1),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MostrarCasosDerechoCivil(navController: NavController, isLoggedIn: Boolean) {
    val casos = listOf(
        Caso("Contratodecompraventa", "Resolución de conflictos relacionados con compraventas."),
        Caso("Arrendamiento", "Casos de arrendamiento y derechos de inquilinos."),
        Caso("Sucesiones", "Procesos de herencias y sucesiones.")
    )

    MostrarCasos(casos, navController, isLoggedIn)
}

@Composable
fun MostrarCasosDerechoPenal(navController: NavController, isLoggedIn: Boolean) {
    val casos = listOf(
        Caso("Delitosmenores", "Defensa en casos de delitos menores."),
        Caso("Delitosgraves", "Casos que involucran delitos graves."),
        Caso("Fraude", "Procesos penales relacionados con fraudes.")
    )

    MostrarCasos(casos, navController, isLoggedIn)
}

@Composable
fun MostrarCasosDerechoFamiliar(navController: NavController, isLoggedIn: Boolean) {
    val casos = listOf(
        Caso("Divorcio", "Procesos legales de divorcio."),
        Caso("Custodiademenores", "Casos relacionados con la custodia de hijos."),
        Caso("Adopciones", "Procesos legales de adopción.")
    )

    MostrarCasos(casos, navController, isLoggedIn)
}

@Composable
private fun MostrarCasos(casos: List<Caso>, navController: NavController, isLoggedIn: Boolean) {
    val context = LocalContext.current
    var showLoginPrompt by remember { mutableStateOf(false) }

    Column {
        casos.forEach { caso ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isLoggedIn) {
                            val topicSinEspacios = caso.nombre.replace(" ", "")
                            navController.navigate("Registro/$topicSinEspacios")
                        } else {
                            showLoginPrompt = true
                        }
                    }
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE1F5FE)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = caso.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF0288D1),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = caso.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF0277BD)
                    )
                }
            }
        }
    }

    if (showLoginPrompt) {
        AlertDialog(
            onDismissRequest = { showLoginPrompt = false },
            title = { Text(text = "Iniciar Sesión") },
            text = { Text(text = "Debe iniciar sesión para acceder a esta función.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLoginPrompt = false
                        navController.navigate("LoginScreen")
                    }
                ) {
                    Text("Iniciar Sesión")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLoginPrompt = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
