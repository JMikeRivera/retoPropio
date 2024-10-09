package com.claymation.retopropio.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ClientDataScreen(navController: NavController?) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        //Chat
        Text("Client Data Screen")
        /*
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
        */

    }
}