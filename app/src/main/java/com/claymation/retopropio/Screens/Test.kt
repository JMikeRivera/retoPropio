package com.claymation.retopropio.Screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.claymation.retopropio.Viewmodels.ViewModel as MyViewModel

@Composable
fun Test(){
    SendEmailComposable()
}

@Composable
fun SendEmailComposable() {
    val context = LocalContext.current
    val viewModel: MyViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.initialize(context)
    }

    // Collect the user's email from the ViewModel
    val email by viewModel.email.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                // Pass the collected email to the sendEmail function
                sendEmail(context, email)
            }
        ) {
            Text(text = "Send Email")
        }
    }
}

