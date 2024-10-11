package com.claymation.retopropio.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MercantilScreen(navController: NavController?){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Selecciona una Sección",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF0277BD),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 50.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        val casos = listOf(
            Caso("Cargos no reconocidos", "Procesos para la reclamación de cargos no reconocidos en cuentas o tarjetas."),
            Caso("Reclamaciones ante CONDUSEF", "Casos de reclamaciones y quejas ante la CONDUSEF."),
            Caso("Reclamaciones ante PROFECO", "Casos de reclamaciones y quejas ante la PROFECO."),
            Caso("Títulos de crédito", "Procesos legales relacionados con el manejo y reclamación de títulos de crédito.")
        )

        MostrarCasos(casos = casos, navController)

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMercantilScreen() {
    MercantilScreen(null)
}
