
package com.claymation.retopropio.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.claymation.retopropio.Viewmodels.ViewModel

@Composable
fun NoticiaDetalleScreen(noticiaId: Int, navController: NavController) {
    val viewModel: ViewModel = viewModel()
    val noticia = viewModel.noticias.collectAsState().value.find { it.Noticia_id == noticiaId }

    if (noticia != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Habilitar scroll
        ) {
            // Encabezado con el título de la noticia
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp)
            ) {
                Text(
                    text = noticia.Nombre,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4476DC)
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            // Imagen de la noticia con ajuste sin recorte y con borde
            Image(
                painter = rememberAsyncImagePainter(noticia.Link),
                contentDescription = noticia.Nombre,
                contentScale = ContentScale.Fit,  // Ajustar la imagen sin recorte
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .border(2.dp, Color(0xFF4476DC)) // Bordes ajustados automáticamente a la imagen
                    .padding(bottom = 16.dp) // Añadir espacio debajo de la imagen
            )

            // Descripción de la noticia justificada
            Text(
                text = noticia.Descripcion,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF434343),
                    textAlign = TextAlign.Justify  // Justificar texto
                ),
                modifier = Modifier.padding(bottom = 16.dp) // Añadir espacio debajo del texto
            )

            // Botón de regreso a la pantalla de noticias
            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al final
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
            ) {
                Text(
                    text = "Regresar a Noticias",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
            }
        }
    } else {
        Text(
            text = "Noticia no encontrada.",
            style = TextStyle(fontSize = 20.sp, color = Color.Red),
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}