package com.claymation.retopropio.Datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.claymation.retopropio.Datastore.PreferenceKeys.EMAIL_KEY
import com.claymation.retopropio.Datastore.PreferenceKeys.LOGGED_IN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DATASTORE = "datastore"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)


// Función para guardar el email
suspend fun Context.SaveEmail(email: String) {
    dataStore.edit { preferences ->
        preferences[EMAIL_KEY] = email
    }
}

// Función para obtener el email como Flow
fun Context.getEmail(): Flow<String> {
    return dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] ?: ""
    }
}

// Función para guardar el estado de inicio de sesión
suspend fun Context.SaveLoggedInStatus(logged_in: Boolean) {
    dataStore.edit { preferences ->
        preferences[LOGGED_IN_KEY] = logged_in
    }
}

// Función para obtener el estado de inicio de sesión como Flow
fun Context.getLoggedInStatus(): Flow<Boolean> {
    return dataStore.data.map { preferences ->
        preferences[LOGGED_IN_KEY] ?: false
    }
}
