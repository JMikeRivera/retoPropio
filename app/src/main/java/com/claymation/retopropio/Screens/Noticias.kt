package com.claymation.retopropio.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun NoticiasScreen(navController: NavController?) {
    Column {
        Text(text = "Noticias")
    }
}

@Preview(showBackground = true)
@Composable
fun NoticiasPreview() {
    NoticiasScreen(null)
}