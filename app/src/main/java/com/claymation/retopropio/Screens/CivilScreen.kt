package com.claymation.retopropio.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.claymation.retopropio.Viewmodels.ViewModel as MyViewModel

@Composable
fun CivilScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: MyViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.initialize(context)
    }
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    var selectedSection by remember { mutableStateOf<TipoDerecho?>(null) }

    Scaffold(
        topBar = { AppBarTop() },
        content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Selecciona un Caso",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF0277BD),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            val casos = listOf(
                Caso(
                    "Consignaciones de rentas",
                    "Procesos relacionados con el pago de rentas en consignación."
                ),
                Caso(
                    "Facturas Judiciales",
                    "Casos sobre la emisión y pago de facturas judiciales."
                ),
                Caso(
                    "Incumplimiento de contratos de arrendamiento",
                    "Casos relacionados con el incumplimiento de contratos de arrendamiento."
                ),
                Caso(
                    "Carta de terminación de contrato de arrendamiento",
                    "Procesos de redacción y gestión de cartas de terminación de contratos de arrendamiento."
                ),
                Caso(
                    "Revisiones de contratos",
                    "Revisión de contratos de arrendamiento, comodato y compraventa de inmuebles."
                ),
                Caso(
                    "Redacción de contratos de comodato",
                    "Procesos de redacción de contratos de comodato."
                ),
                Caso(
                    "Incumplimiento de contratos de servicios funerarios",
                    "Casos relacionados con el incumplimiento de contratos de servicios funerarios."
                )
            )

            MostrarCasos(casos = casos, navController = navController, isLoggedIn = isLoggedIn)

            Spacer(modifier = Modifier.height(24.dp))
            }
        },
        bottomBar = { AppBarBottom(modifier = Modifier, navController) }
    )
}

// Añade la función MostrarCasos dentro de MercantilScreen.kt
@Composable
private fun MostrarCasos(casos: List<Caso>, navController: NavController, isLoggedIn: Boolean) {
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