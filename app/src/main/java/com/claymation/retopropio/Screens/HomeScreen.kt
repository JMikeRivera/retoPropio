package com.claymation.retopropio.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.claymation.retopropio.R


@Composable
fun HomeScreen(navController: NavController?) {
    Scaffold(
        // Top bar
        topBar = { AppBarTop() },

        // Content
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "Asesoría legal gratuita y confiable",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6889B3)
                    ),
                    textAlign = TextAlign.Center
                )

                // Texto del header
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF4476DC), fontWeight =
                                FontWeight.Bold
                            )
                        ) {
                            append("BufeTec")
                        }
                        append(
                            " lo conectará con uno de nuestros abogados especializados según" +
                                    " su ubicación, preferencias y disponibilidad de horarios."
                        )
                    },
                    style = TextStyle(fontSize = 16.sp, color = Color(0xFF6889B3)),
                    textAlign = TextAlign.Center
                )

                // Spacer
                Spacer(modifier = Modifier.height(8.dp))

                // Header
                Text(
                    text = "Ayudanos a encontrar al abogado adecuado para ti",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4476DC)
                    ),
                    textAlign = TextAlign.Center
                )

                // Divider
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp, bottom = 2.dp),
                    thickness = 1.dp,
                    color = Color(0xFF4476DC)
                )

                Text(
                    text = "¿Que tipo de asesoria buscas?",
                    style = TextStyle(fontSize = 16.sp, color = Color(0xFF434343)),
                    textAlign = TextAlign.Center
                )

                // Botones de acción
                ButtonSection()

                // Testimonios
                TestimonialSection()

            }
        },

        // Bottom bar
        // bottomBar = { AppBarBottom() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "BufeTec",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        },
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF40D2FF),  // Start color of gradient
                        Color(0xFF0062FF)   // End color of gradient
                    )
                )
            ),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        )
    )
}


@Composable
fun AppBarBottom() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        containerColor = Color(0xFFF5F5F5)
    ) {
        IconButton(
            onClick = { /* TODO: Handle click for Procesos */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.ic_process), contentDescription = "Procesos")
        }
        IconButton(
            onClick = { /* TODO: Handle click for Bufete */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.ic_search), contentDescription = "Buscar")
        }
        IconButton(
            onClick = { /* TODO: Handle click for Guías */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.ic_buffet), contentDescription = "Bufete")
        }
    }
}


@Composable
fun ButtonSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { /*TODO: navegar a asesoria marital*/ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(
                text = "Familiar",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { /*TODO: navegar a asesoria laboral*/ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(
                text = "Mercantil",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { /*TODO: navegar a asesoria penal*/ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
        ) {
            Text(
                text = "Civil",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /*TODO: navegar a ayuda con la seleccion*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF40D2FF),  // Start color of gradient
                                Color(0xFF0062FF)   // End color of gradient
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "¿No sabes cual elegir?",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                )
            }

        }
    }
}


@Composable
fun TestimonialSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF434343)),
                text = "\" Gracias a BufeTec, por fin pude ver a mi hija después de 5 años \""
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = null)  // Passing null since this is just a preview
}