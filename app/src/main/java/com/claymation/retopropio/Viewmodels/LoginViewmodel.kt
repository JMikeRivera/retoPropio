package com.claymation.retopropio.Viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // MutableLiveData to hold email and password
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> get() = _errorMessage

    // Function to validate login
    fun login() {
        val currentEmail = _email.value
        val currentPassword = _password.value

        if (currentEmail.isNullOrEmpty() || currentPassword.isNullOrEmpty()) {
            _errorMessage.value = "Please enter both email and password."
        } else {
            _errorMessage.value = null // por el momento
        }
    }

    // Function to update email and password
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}