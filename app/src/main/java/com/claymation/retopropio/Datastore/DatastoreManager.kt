package com.claymation.retopropio.Datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.claymation.retopropio.Datastore.PreferenceKeys.EMAIL_KEY
import com.claymation.retopropio.Datastore.PreferenceKeys.LOGGED_IN_KEY
import kotlinx.coroutines.flow.first


const val DATASTORE = "datastore"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE)


suspend fun Context.SaveEmail(email: String) {
    dataStore.edit { preferences ->
        preferences[EMAIL_KEY] = email
    }
}


suspend fun Context.GetEmail(): String? {
    val preferences = dataStore.data.first()
    return preferences[PreferenceKeys.EMAIL_KEY]
}


suspend fun Context.SaveLoggedInStatus(logged_in: Boolean) {
    dataStore.edit { preferences ->
        preferences[LOGGED_IN_KEY] = logged_in
    }
}


suspend fun Context.isLoggedIn(): Boolean {
    val preferences = dataStore.data.first()
    return preferences[PreferenceKeys.LOGGED_IN_KEY] ?: false
}