package com.claymation.retopropio.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Abogado(val nombre: String, val tipoCasos: String, val contacto: String)

@Composable
fun GlosarioScreen(navController: NavController?){
    val abogados = listOf(
        Abogado("María López", "Derecho Civil", "mlopez@example.com"),
        Abogado("Juan Pérez", "Derecho Penal", "jperez@example.com"),
        Abogado("Ana García", "Derecho Familiar", "agarcia@example.com")
    )

    var abogadoSeleccionado by remember { mutableStateOf<Abogado?>(null) }
    var searchText by remember { mutableStateOf("") }

    val abogadosFiltrados = abogados.filter { abogado ->
        abogado.nombre.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar abogado") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)

        )

        Text(
            text = "Lista de Abogados",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF0277BD),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (abogadosFiltrados.isEmpty()) {
            Text(
                text = "No se encontró ningún abogado",
                style = MaterialTheme.typography.bodyLarge, // bodyLarge en vez de body1
                color = Color(0xFF0277BD), // Azul oscuro para el mensaje de no resultados
                modifier = Modifier.padding(8.dp)
            )
        } else {
            abogadosFiltrados.forEach { abogado ->
                AbogadoItem(abogado) { seleccionado ->
                    abogadoSeleccionado = seleccionado
                }
            }
        }

        abogadoSeleccionado?.let { abogado ->
            AbogadoInfoDialog(abogado = abogado, onDismiss = { abogadoSeleccionado = null })
        }
    }
}


@Composable
fun AbogadoItem(abogado: Abogado, onAbogadoClick: (Abogado) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onAbogadoClick(abogado) },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE1F5FE) // Azul claro para las tarjetas
        )
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = abogado.nombre,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF0288D1)
            )
            Text(
                text = "Especialista en: ${abogado.tipoCasos}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF0277BD)
            )
        }
    }
}

@Composable
fun AbogadoInfoDialog(abogado: Abogado, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Información de Contacto",
                color = Color(0xFF0288D1)
            )
        },
        text = {
            Column {
                Text("Nombre: ${abogado.nombre}", color = Color(0xFF0277BD))
                Text("Tipo de Casos: ${abogado.tipoCasos}", color = Color(0xFF0277BD))
                Text("Contacto: ${abogado.contacto}", color = Color(0xFF0277BD))
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0288D1),
                    contentColor = Color.White
                )
            ) {
                Text("Cerrar")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewAbogadosScreen() {
    GlosarioScreen(null)
}