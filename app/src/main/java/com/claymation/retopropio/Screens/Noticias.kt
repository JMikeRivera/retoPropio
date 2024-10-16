package com.claymation.retopropio.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.claymation.retopropio.Viewmodels.Noticia
import com.claymation.retopropio.Viewmodels.ViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun NoticiasScreen(navController: NavController?) {
    val viewModel: ViewModel = viewModel()
    val noticias by viewModel.noticias.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fondo degradado que abarca la barra de estado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF40D2FF),
                            Color(0xFF0062FF)
                        )
                    )
                )
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            // Título "Noticias" estilizado
            Text(
                text = "Noticias",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // Lista de noticias con scroll vertical gestionado por LazyVerticalGrid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(noticias) { noticia ->
                NoticiaCard(noticia = noticia, onClick = {
                    navController?.navigate("NoticiaDetalle/${noticia.Noticia_id}")
                })
            }
        }

        // Botón de regresar al HomeScreen en la parte inferior
        Button(
            onClick = { navController?.navigate("HomeScreen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(
                text = "Regresar a Home",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
        }
    }
}

@Composable
fun NoticiaCard(noticia: Noticia, onClick: () -> Unit) {
    val modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .border(2.dp, Color(0xFF4476DC), shape = RoundedCornerShape(8.dp))
        .clickable { onClick() }

    ImageCard(noticia = noticia, modifier = modifier)
}

@Composable
fun ImageCard(noticia: Noticia, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(noticia.Link),
            contentDescription = noticia.Nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color(0x800062FF))
                .padding(4.dp)
        ) {
            Text(
                text = noticia.Nombre,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
