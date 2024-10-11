package com.claymation.retopropio.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


enum class TipoDerecho {
    DERECHO_CIVIL, DERECHO_PENAL, DERECHO_FAMILIAR
}

data class Caso(val nombre: String, val descripcion: String)

@Composable
fun CasosScreen(navController: NavController?) {
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
            modifier = Modifier.padding(start = 50.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        SeccionDerecho("Derecho Civil") { selectedSection = TipoDerecho.DERECHO_CIVIL }
        SeccionDerecho("Derecho Penal") { selectedSection = TipoDerecho.DERECHO_PENAL }
        SeccionDerecho("Derecho Familiar") { selectedSection = TipoDerecho.DERECHO_FAMILIAR }

        Spacer(modifier = Modifier.height(16.dp))

        selectedSection?.let {
            when (it) {
                TipoDerecho.DERECHO_CIVIL -> MostrarCasosDerechoCivil(navController)
                TipoDerecho.DERECHO_PENAL -> MostrarCasosDerechoPenal(navController)
                TipoDerecho.DERECHO_FAMILIAR -> MostrarCasosDerechoFamiliar(navController)
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
            containerColor = Color(0xFFE1F5FE) // Azul claro para las tarjetas
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
fun MostrarCasosDerechoCivil(navController: NavController?) {
    val casos = listOf(
        Caso("Contrato de compraventa", "Resolución de conflictos relacionados con compraventas."),
        Caso("Arrendamiento", "Casos de arrendamiento y derechos de inquilinos."),
        Caso("Sucesiones", "Procesos de herencias y sucesiones.")
    )

    MostrarCasos(casos,navController)
}

@Composable
fun MostrarCasosDerechoPenal(navController: NavController?) {
    val casos = listOf(
        Caso("Delitos menores", "Defensa en casos de delitos menores."),
        Caso("Delitos graves", "Casos que involucran delitos graves."),
        Caso("Fraude", "Procesos penales relacionados con fraudes.")
    )

    MostrarCasos(casos,navController)
}

@Composable
fun MostrarCasosDerechoFamiliar(navController: NavController?) {
    val casos = listOf(
        Caso("Divorcio", "Procesos legales de divorcio."),
        Caso("Custodia de menores", "Casos relacionados con la custodia de hijos."),
        Caso("Adopciones", "Procesos legales de adopción.")
    )

    MostrarCasos(casos,navController)
}

@Composable
fun MostrarCasos(casos: List<Caso>, navController: NavController?) {
    Column {
        casos.forEach { caso ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController?.navigate("detail")  // Navigate to detail screen on click
                    }
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = caso.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF0288D1), // Azul oscuro para el título del caso
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = caso.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF0277BD) // Azul más oscuro para la descripción
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCasosScreen() {
    CasosScreen(null)
}
