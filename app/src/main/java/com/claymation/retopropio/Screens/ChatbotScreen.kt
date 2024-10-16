package com.claymation.retopropio.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.claymation.retopropio.Viewmodels.ViewModel

data class ChatMessage(val message: String, val isUser: Boolean)

@Composable
fun ChatbotScreen(navController: NavController?, chatbotViewModel: ViewModel = viewModel()) {
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }  // Lista de mensajes correctamente tipada
    var currentMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Chatbot", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(messages) { message ->
                MessageBubble(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = currentMessage,
                onValueChange = { currentMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray, RoundedCornerShape(10.dp))
                    .padding(8.dp)
            )

            Button(
                onClick = {
                    if (currentMessage.isNotBlank()) {
                        // Agregar el mensaje del usuario al historial
                        messages = messages + ChatMessage(currentMessage, true)

                        chatbotViewModel.sendQuestionToAPI(
                            question = currentMessage,
                            onSuccess = { botResponse ->
                                // Agregar la respuesta del bot al historial
                                messages = messages + ChatMessage(botResponse, false)
                            },
                            onFailure = { error ->
                                errorMessage = error
                            }
                        )
                        currentMessage = ""  // Limpiar el campo de entrada después de enviar
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun MessageBubble(chatMessage: ChatMessage) {
    val backgroundColor = if (chatMessage.isUser) Color(0xFFDCF8C6) else Color(0xFFEDEDED)
    val alignment = if (chatMessage.isUser) Alignment.End else Alignment.Start

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(alignment)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(12.dp)
    ) {
        Text(text = chatMessage.message, fontSize = 16.sp)
    }
}
