package com.claymation.retopropio.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class InstitucionLegal(
    val nombre: String,
    val direccion: String
)

val institucionesLegales = listOf(
    InstitucionLegal(
        nombre = "Suprema Corte de Justicia de la Nación (SCJN)",
        direccion = "Pino Suárez No. 2, Col. Centro, Alcaldía Cuauhtémoc, C.P. 06065, Ciudad de México"
    ),
    InstitucionLegal(
        nombre = "Procuraduría General de Justicia del Estado de Nuevo León (Fiscalía General del Estado)",
        direccion = "Avenida Gonzalitos 1100 Norte, Col. Urdiales, Monterrey, Nuevo León, C.P. 64430"
    ),
    InstitucionLegal(
        nombre = "Comisión Nacional de los Derechos Humanos (CNDH)",
        direccion = "Periférico Sur 3469, Col. San Jerónimo Lídice, C.P. 10200, Ciudad de México"
    ),
    InstitucionLegal(
        nombre = "Tribunal Superior de Justicia de Nuevo León",
        direccion = "Ocampo 163, Col. Centro, Monterrey, Nuevo León, C.P. 64000"
    ),
    InstitucionLegal(
        nombre = "Instituto Federal de Defensoría Pública (IFDP)",
        direccion = "Calle Dr. José María Vértiz 211, Col. Doctores, Alcaldía Cuauhtémoc, C.P. 06720, Ciudad de México"
    )
)

@Composable
fun MalasNotScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE1F5FE)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Malas Noticias!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA390),
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "No cumples con los requisitos para recibir tu caso.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF0277BD),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Te recomendamos ir con estas instituciones dependiendo del caso:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF0277BD),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        // Mostrar la lista de instituciones legales
        institucionesLegales.forEach { institucion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = institucion.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0288D1)
                    )
                    Text(
                        text = institucion.direccion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF0277BD)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        // Botón para regresar a la pantalla principal
        Button(
            onClick = { navController.navigate("HomeScreen") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
        ) {
            Text(
                text = "Regresar a Inicio",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
