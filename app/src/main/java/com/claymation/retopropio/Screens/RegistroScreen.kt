// RegistroScreen.kt
package com.claymation.retopropio.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController

@Composable
fun RegistroScreen(navController: NavController, topic: String) {
    var esDeNuevoLeon by remember { mutableStateOf(false) }
    var edad by remember { mutableStateOf(0f) }
    var ingresoMensual by remember { mutableStateOf(0f) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var mensajeValidacion by remember { mutableStateOf("") }
    var esValido by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TituloFormulario(topic)

        SeccionFormulario(
            titulo = "¿Eres de Nuevo León?",
            contenido = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (esDeNuevoLeon) "Sí" else "No",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0288D1),
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = esDeNuevoLeon,
                        onCheckedChange = { esDeNuevoLeon = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF0288D1),
                            uncheckedThumbColor = Color(0xFFE1F5FE)
                        )
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SeccionFormulario(
            titulo = "¿Cuántos años tienes?",
            contenido = {
                Column {
                    Slider(
                        value = edad,
                        onValueChange = { edad = it },
                        valueRange = 0f..100f,
                        steps = 82,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF0288D1),
                            activeTrackColor = Color(0xFFE1F5FE)
                        )
                    )
                    Text(
                        text = "Edad: ${edad.toInt()} años",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0288D1),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SeccionFormulario(
            titulo = "¿Cuál es tu ingreso mensual?",
            contenido = {
                Column {
                    Slider(
                        value = ingresoMensual,
                        onValueChange = { ingresoMensual = it },
                        valueRange = 0f..45000f,
                        steps = 40,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF0288D1),
                            activeTrackColor = Color(0xFFE1F5FE)
                        )
                    )
                    Text(
                        text = "Ingreso mensual: $${ingresoMensual.toInt()} MXN",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0288D1),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Continuar
        Button(
            onClick = {
                esValido = validarDatos(esDeNuevoLeon, edad.toInt(), ingresoMensual.toInt())
                mensajeValidacion = if (esValido) "Los datos son válidos" else "Los datos no son válidos"
                mostrarDialogo = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0288D1),
                contentColor = Color.White
            )
        ) {
            Text(text = "Continuar")
        }

        if (mostrarDialogo) {
            MostrarDialogoValidacion(
                mensajeValidacion = mensajeValidacion,
                esValido = esValido,
                topic = topic,
                onDismiss = { mostrarDialogo = false }
            )
        }
    }
}

@Composable
fun TituloFormulario(topic: String) {
    Text(
        text = "Formulario de Datos - $topic",
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFF0277BD),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun MostrarDialogoValidacion(
    mensajeValidacion: String,
    esValido: Boolean,
    topic: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mensajeValidacion,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (esValido) Color(0xFF388E3C) else Color(0xFFD32F2F)
                )
                Text(
                    text = "Topic: $topic",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text(text = "Aceptar")
                }
            }
        }
    }
}

fun validarDatos(esDeNuevoLeon: Boolean, edad: Int, ingresoMensual: Int): Boolean {
    return esDeNuevoLeon && edad >= 18 && ingresoMensual <= 30000
}

@Composable
fun SeccionFormulario(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE1F5FE)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF0288D1),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            contenido()
        }
    }
}
