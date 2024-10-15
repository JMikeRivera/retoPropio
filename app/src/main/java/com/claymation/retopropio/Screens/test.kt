package com.claymation.retopropio.Screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.claymation.retopropio.Viewmodels.ViewModel as MyViewModel


@Composable
fun test(navController: NavController?) {
    Column {
        SendEmailComposable()
    }
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


fun sendEmail(context: Context, userEmail: String) {
    val senderEmail = "smikerivera@gmail.com"  // Sender's email
    val password = "vkhd zuim skkq rqlm"       // App password (use the correct app password)
    val recipientEmail = "a00836995@tec.mx"    // Recipient's email
    val subject = "Nuevo Caso Recibido"

    // Use the user's email in the body of the email
    val body = "Hola abogado, llego un nuevo caso de $userEmail"

    // Sending the email
    SendEmailTask(senderEmail, password, recipientEmail, subject, body).execute()
}

