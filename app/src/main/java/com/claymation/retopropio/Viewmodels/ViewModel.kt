package com.claymation.retopropio.Viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claymation.retopropio.Datastore.SaveEmail
import com.claymation.retopropio.Datastore.SaveLoggedInStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    // MutableLiveData to hold email, password, and other fields for registration
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> get() = _confirmPassword

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _secname = MutableLiveData<String>()
    val secname: LiveData<String> get() = _secname

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> get() = _age

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> get() = _phone

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    private val client = OkHttpClient()

    // Functions to update LiveData fields
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

    // Function to login via API and store data in DataStore
    fun login(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentEmail = _email.value
        val currentPassword = _password.value

        // Validate inputs before making API call
        if (currentEmail.isNullOrEmpty() || currentPassword.isNullOrEmpty()) {
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
                Log.e("AuthError", "Error en la red: ${e.message}")
                viewModelScope.launch(Dispatchers.Main) {
                    _errorMessage.value = "Error de red: ${e.message}"
                    onFailure() // Callback on failure
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody)
                    val success = jsonResponse.getBoolean("success")

                    if (success) {
                        // Login successful, store email and logged in status in DataStore
                        viewModelScope.launch(Dispatchers.IO) {
                            context.SaveEmail(currentEmail!!)
                            context.SaveLoggedInStatus(true)

                            viewModelScope.launch(Dispatchers.Main) {
                                _isLoggedIn.value = true
                                _errorMessage.value = null
                                onSuccess() // Callback on success
                            }
                        }
                    } else {
                        val message = jsonResponse.getString("message")
                        Log.e("AuthError", "Error de autenticación: $message")
                        viewModelScope.launch(Dispatchers.Main) {
                            _errorMessage.value = message
                            onFailure() // Callback on failure
                        }
                    }
                } else {
                    Log.e("AuthError", "Error en la respuesta del servidor.")
                    viewModelScope.launch(Dispatchers.Main) {
                        _errorMessage.value = "Error en la autenticación"
                        onFailure() // Callback on failure
                    }
                }
            }
        })
    }

    // Function para iniciar sesión como invitado
    fun loginAsGuest(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            context.SaveLoggedInStatus(true)
            viewModelScope.launch(Dispatchers.Main) {
                _isLoggedIn.value = true
                _errorMessage.value = null
                onSuccess()
            }
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
        if (currentName.isNullOrEmpty() || currentEmail.isNullOrEmpty() || currentPassword.isNullOrEmpty() ||
            currentConfirmPassword.isNullOrEmpty() || currentAge.isNullOrEmpty() || currentPhone.isNullOrEmpty()) {
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
                Log.e("AuthError", "Error al registrar: ${e.message}")
                viewModelScope.launch(Dispatchers.Main) {
                    _errorMessage.value = "Error de red: ${e.message}"
                    onFailure() // Callback on failure
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("AuthSuccess", "Usuario registrado con éxito")
                    viewModelScope.launch(Dispatchers.IO) {
                        context.SaveEmail(currentEmail!!)
                        context.SaveLoggedInStatus(true)

                        viewModelScope.launch(Dispatchers.Main) {
                            _errorMessage.value = null
                            onSuccess() // Callback on success
                        }
                    }
                } else {
                    Log.e("AuthError", "Error en la respuesta: ${response.body?.string()}")
                    viewModelScope.launch(Dispatchers.Main) {
                        _errorMessage.value = "Error en el registro."
                        onFailure() // Callback on failure
                    }
                }
            }
        })
    }
}
