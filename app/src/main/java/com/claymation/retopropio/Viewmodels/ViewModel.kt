package com.claymation.retopropio.Viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claymation.retopropio.Datastore.SaveEmail
import com.claymation.retopropio.Datastore.SaveLoggedInStatus
import com.claymation.retopropio.Datastore.getEmail
import com.claymation.retopropio.Datastore.getLoggedInStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ViewModel : ViewModel() {
    // MutableStateFlows to hold email, password, and other fields for registration
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> get() = _confirmPassword

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _secname = MutableStateFlow("")
    val secname: StateFlow<String> get() = _secname

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> get() = _age

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> get() = _phone

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // StateFlow for authentication status
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val client = OkHttpClient()

    // Initialize the ViewModel and load the authentication status
    fun initialize(context: Context) {
        // Observa el Flow del estado de autenticación y actualiza _isLoggedIn
        viewModelScope.launch {
            context.getLoggedInStatus().collect { status ->
                _isLoggedIn.value = status
            }
        }

        // Observa el Flow del email y actualiza _email
        viewModelScope.launch {
            context.getEmail().collect { emailValue ->
                _email.value = emailValue
            }
        }
    }

    // Functions to update StateFlow fields
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateSecName(newSecName: String) {
        _secname.value = newSecName
    }

    fun updateAge(newAge: String) {
        _age.value = newAge
    }

    fun updatePhone(newPhone: String) {
        _phone.value = newPhone
    }

    fun login(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentEmail = _email.value
        val currentPassword = _password.value

        // Validate inputs before making API call
        if (currentEmail.isEmpty() || currentPassword.isEmpty()) {
            _errorMessage.value = "Por favor, ingrese el email y la contraseña."
            return
        }

        val url = "https://bufetecapi.onrender.com/login"
        val jsonObject = JSONObject().apply {
            put("Correo", currentEmail)
            put("Password", currentPassword)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Cambiamos al hilo principal
                viewModelScope.launch(Dispatchers.Main) {
                    _errorMessage.value = "Error de red: ${e.message}"
                    onFailure() // Callback on failure
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val jsonResponse = JSONObject(responseBody)
                    val success = jsonResponse.getBoolean("success")

                    if (success) {
                        // Login successful, store email and logged in status in DataStore
                        viewModelScope.launch {
                            context.SaveEmail(currentEmail)
                            context.SaveLoggedInStatus(true)
                            // Cambiamos al hilo principal
                            withContext(Dispatchers.Main) {
                                _errorMessage.value = null
                                onSuccess() // Callback on success
                            }
                        }
                    } else {
                        val message = jsonResponse.getString("message")
                        // Cambiamos al hilo principal
                        viewModelScope.launch(Dispatchers.Main) {
                            _errorMessage.value = message
                            onFailure() // Callback on failure
                        }
                    }
                } else {
                    // Cambiamos al hilo principal
                    viewModelScope.launch(Dispatchers.Main) {
                        _errorMessage.value = "Error en la autenticación"
                        onFailure() // Callback on failure
                    }
                }
            }
        })
    }

    // Function to log in as guest
    fun loginAsGuest(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            context.SaveLoggedInStatus(false)
            context.SaveEmail("")
            _errorMessage.value = null
            onSuccess()
        }
    }

    // Function to register a user via API and store data in DataStore
    fun register(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentName = _name.value
        val currentSecName = _secname.value
        val currentEmail = _email.value
        val currentAge = _age.value
        val currentPhone = _phone.value
        val currentPassword = _password.value
        val currentConfirmPassword = _confirmPassword.value

        // Validate inputs before making API call
        if (currentName.isEmpty() || currentEmail.isEmpty() || currentPassword.isEmpty() ||
            currentConfirmPassword.isEmpty() || currentAge.isEmpty() || currentPhone.isEmpty()) {
            _errorMessage.value = "Todos los campos son obligatorios."
            return
        }

        if (currentPassword != currentConfirmPassword) {
            _errorMessage.value = "Las contraseñas no coinciden."
            return
        }

        val url = "https://bufetecapi.onrender.com/usuarios"
        val jsonObject = JSONObject().apply {
            put("Nombre", currentName)
            put("Apellido", currentSecName)
            put("Correo", currentEmail)
            put("Telefono", currentPhone)
            put("Edad", currentAge.toInt())
            put("Password", currentPassword)
            put("Permisos", 1)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _errorMessage.value = "Error de red: ${e.message}"
                onFailure() // Callback on failure
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        context.SaveEmail(currentEmail)
                        context.SaveLoggedInStatus(true)
                        _errorMessage.value = null
                        onSuccess() // Callback on success
                    }
                } else {
                    val errorBody = response.body?.string()
                    _errorMessage.value = "Error en el registro."
                    onFailure() // Callback on failure
                }
            }
        })
    }

    // Function to log out the user
    fun logout(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            context.SaveLoggedInStatus(false)
            context.SaveEmail("")
            onSuccess()
        }
    }
}
