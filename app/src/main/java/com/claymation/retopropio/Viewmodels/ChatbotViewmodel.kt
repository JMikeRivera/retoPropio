package com.claymation.retopropio.Viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claymation.retopropio.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

data class ChatMessage(val message: String, val isUser: Boolean)

class ChatbotViewModel : ViewModel() {

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.api_key
    )

    // Mutable state for messages list
    var messages = mutableStateOf(listOf(ChatMessage("Hi! How can I help you?", false)))
        private set

    // Mutable state for the current message being typed
    var currentMessage = mutableStateOf("")

    // Function to send the user message and get a bot response
    fun sendMessage(message: String) {
        viewModelScope.launch {
            val chat = generativeModel.startChat()
            val response = chat.sendMessage(message)  // Send user input to the API or processing
            Log.i("response", response.text.toString())
        }
    }


    // Function to update the current message
    fun updateCurrentMessage(newMessage: String) {
        currentMessage.value = newMessage
    }
}