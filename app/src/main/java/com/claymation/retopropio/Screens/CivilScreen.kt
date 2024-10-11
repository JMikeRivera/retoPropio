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
fun CivilScreen(navController: NavController?){

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
            Caso("Consignaciones de rentas", "Procesos relacionados con el pago de rentas en consignación."),
            Caso("Facturas Judiciales", "Casos sobre la emisión y pago de facturas judiciales."),
            Caso("Incumplimiento de contratos de arrendamiento", "Casos relacionados con el incumplimiento de contratos de arrendamiento."),
            Caso("Carta de terminación de contrato de arrendamiento", "Procesos de redacción y gestión de cartas de terminación de contratos de arrendamiento."),
            Caso("Revisiones de contratos", "Revisión de contratos de arrendamiento, comodato y compraventa de inmuebles."),
            Caso("Redacción de contratos de comodato", "Procesos de redacción de contratos de comodato."),
            Caso("Incumplimiento de contratos de servicios funerarios", "Casos relacionados con el incumplimiento de contratos de servicios funerarios.")
        )


        MostrarCasos(casos = casos)

        Spacer(modifier = Modifier.height(16.dp))

        }
    }

@Preview(showBackground = true)
@Composable
fun PreviewCivilScreenn() {
    CivilScreen(null)
}
