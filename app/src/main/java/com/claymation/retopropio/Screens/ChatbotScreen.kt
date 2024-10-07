package com.claymation.retopropio.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.claymation.retopropio.Viewmodels.ChatbotViewModel
import com.claymation.retopropio.Viewmodels.ChatMessage

@Composable
fun ChatbotScreen(chatbotViewModel: ChatbotViewModel = viewModel()) {
    val messages by chatbotViewModel.messages
    val currentMessage by chatbotViewModel.currentMessage

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Message List
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages) { message ->
                MessageBubble(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Text input and send button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = currentMessage,
                onValueChange = { chatbotViewModel.updateCurrentMessage(it) }, // Updates the current message as the user types
                placeholder = { Text("Type your message...") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    if (currentMessage.isNotBlank()) {
                        chatbotViewModel.sendMessage(currentMessage)  // Pass the current message to sendMessage()
                        chatbotViewModel.updateCurrentMessage("")    // Clear the input field after sending the message
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }

    }
}

@Composable
fun MessageBubble(chatMessage: ChatMessage) {
    val backgroundColor = if (chatMessage.isUser) Color(0xFFD1E8FF) else Color(0xFFF1F1F1)
    val alignment = if (chatMessage.isUser) Alignment.End else Alignment.Start

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .wrapContentWidth(alignment)
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Text(text = chatMessage.message)
    }
}


@Preview
@Composable
fun ChatbotScreenPreview() {
    ChatbotScreen()
}