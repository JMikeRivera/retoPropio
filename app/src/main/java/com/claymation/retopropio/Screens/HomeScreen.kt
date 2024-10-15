package com.claymation.retopropio.Screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.claymation.retopropio.R
import com.claymation.retopropio.Viewmodels.ViewModel


@Composable
fun HomeScreen(navController: NavController?) {
    val viewModel: ViewModel = viewModel() // Usando el ViewModel llamado "ViewModel"
    val context = LocalContext.current
    // Initialize the ViewModel
    viewModel.initialize(context)
    Scaffold(
        topBar = { AppBarTop() },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState()),  // Agrega el scroll vertical
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Asesoría legal gratuita y confiable",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6889B3)
                    ),
                    textAlign = TextAlign.Center
                )

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

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ayudanos a encontrar al abogado adecuado para ti",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4476DC)
                    ),
                    textAlign = TextAlign.Center
                )

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

                ButtonSection( navController)
                TestimonialSection()

                Button(
                    onClick = { /*TODO: navegar a asesoria marital*/
                        navController?.navigate("Glosario")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF539EFF))
                ) {
                    Text(
                        text = "Conoce Nuestros Abogados",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    )
                }            }
        },
        bottomBar = { AppBarBottom(modifier = Modifier, navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "BufeTec",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
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
            containerColor = Color.Transparent, // Transparent to see gradient
            titleContentColor = Color.White // White text color
        )
    )

}


@Composable
fun AppBarBottom(modifier: Modifier = Modifier, navController: NavController?) {
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
            Icon(painterResource(id = R.drawable.ic_process), contentDescription = "CasosScreen")
        }
        IconButton(
            onClick = { /* TODO: Handle click for Bufete */
                navController?.navigate("Noticias")

            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.ic_search), contentDescription = "Buscar")
        }
        IconButton(
            onClick = {
            //navegar a la chabotscreen
                navController?.navigate("ChatBotScreen")
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.ic_buffet), contentDescription = "Bufete")
        }
        IconButton(
            onClick = {
                //navegar a home
                navController?.navigate("HomeScreen")
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Bufete")
        }
    }
}

@Composable
fun ButtonSection(navController: NavController?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { /*TODO: navegar a asesoria marital*/
                navController?.navigate("Familiar")

            },
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
            onClick = { /*TODO: navegar a asesoria laboral*/
                navController?.navigate("Mercantil")
            },
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
            onClick = { /*TODO: navegar a asesoria civil*/
                navController?.navigate("Civil")
            },
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
            onClick = { /*TODO: navegar a ayuda con la seleccion*/

                navController?.navigate("Casos/H")
            },
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