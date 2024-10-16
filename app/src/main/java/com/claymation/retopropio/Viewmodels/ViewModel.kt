package com.claymation.retopropio.Viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import org.json.JSONArray
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

    // Añadido: StateFlow para mantener la lista de noticias
    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> get() = _noticias


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

    data class Noticia(
        val Noticia_id: Int,
        val Nombre: String,
        val Descripcion: String,
        val Link: String,
        val IsVideo: Boolean
    )




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


    fun createCaso(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentEmail = _email.value


        // Verificar que el email no esté vacío antes de hacer la solicitud
        if (currentEmail.isEmpty()) {
            _errorMessage.value = "El correo electrónico es necesario para crear un caso."
            return
        }
        println("Email: $currentEmail")
        val url = "https://bufetecapi.onrender.com/casos/$currentEmail"
        val jsonObject = JSONObject().apply {
            put("Status", 0)  // Establecer el valor de Status a 0
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                viewModelScope.launch(Dispatchers.Main) {
                    _errorMessage.value = "Error de red: ${e.message}"
                    onFailure() // Callback en caso de fallo
                }
            }

            override fun onResponse(call: Call, response: Response) {
                viewModelScope.launch(Dispatchers.Main) {
                    response.use {  // Asegura que el cuerpo de la respuesta se cierre adecuadamente
                        if (response.isSuccessful) {
                            _errorMessage.value = null
                            onSuccess() // Callback en caso de éxito
                        } else {
                            _errorMessage.value = "Error al crear el caso: ${response.message}"
                            onFailure() // Callback en caso de fallo
                        }
                    }
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

    // Añadido: Función para obtener las noticias desde la API
    fun fetchNoticias() {
        val url = "https://ndba.onrender.com/noticias"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejo de errores
                viewModelScope.launch(Dispatchers.Main) {
                    _errorMessage.value = "Error al obtener noticias: ${e.message}"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { res ->
                    if (!res.isSuccessful) {
                        viewModelScope.launch(Dispatchers.Main) {
                            _errorMessage.value = "Error al obtener noticias: ${res.message}"
                        }
                        return
                    }

                    val responseBody = res.body?.string()
                    if (responseBody != null) {
                        try {
                            val jsonArray = JSONArray(responseBody)
                            val noticiasList = mutableListOf<Noticia>()
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val noticia = Noticia(
                                    Noticia_id = jsonObject.getInt("Noticia_id"),
                                    Nombre = jsonObject.getString("Nombre"),
                                    Descripcion = jsonObject.getString("Descripcion"),
                                    Link = jsonObject.getString("Link"),
                                    IsVideo = jsonObject.getBoolean("IsVideo")
                                )
                                noticiasList.add(noticia)
                            }
                            // Actualizar el StateFlow con la lista de noticias
                            viewModelScope.launch(Dispatchers.Main) {
                                _noticias.value = noticiasList
                            }
                        } catch (e: Exception) {
                            viewModelScope.launch(Dispatchers.Main) {
                                _errorMessage.value = "Error al procesar las noticias."
                            }
                        }
                    }
                }
            }
        })
    }

    fun sendEmail(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            putExtra(Intent.EXTRA_TEXT, "Body of the email here.")
        }

        if (emailIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(emailIntent)
        } else {
            Toast.makeText(context, "No email client installed", Toast.LENGTH_SHORT).show()
        }
    }

    init {
        fetchNoticias()
    }




    // Nueva función para enviar preguntas a la API
    fun sendQuestionToAPI(question: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        if (question.isEmpty()) {
            _errorMessage.value = "La pregunta no puede estar vacía."
            return
        }

        val url = "https://coco-zjyd.onrender.com/api/ask"
        val jsonObject = JSONObject().apply {
            put("question", question)  // Envía la pregunta en formato JSON
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                viewModelScope.launch(Dispatchers.Main) {
                    _errorMessage.value = "Error de red: ${e.message}"
                    onFailure("Error de red: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {  // Asegura que el cuerpo de la respuesta se cierre adecuadamente
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val jsonResponse = JSONObject(responseBody)
                            val botResponse = jsonResponse.getString("response")

                            viewModelScope.launch(Dispatchers.Main) {
                                _errorMessage.value = null
                                onSuccess(botResponse)  // Envía la respuesta del bot
                            }
                        } else {
                            viewModelScope.launch(Dispatchers.Main) {
                                _errorMessage.value = "Respuesta vacía del servidor."
                                onFailure("Respuesta vacía del servidor.")
                            }
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.Main) {
                            _errorMessage.value = "Error en la solicitud: ${response.message}"
                            onFailure("Error en la solicitud: ${response.message}")
                        }
                    }
                }
            }
        })
    }
}


