package com.claymation.retopropio.Datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val EMAIL_KEY = stringPreferencesKey("email")
    val LOGGED_IN_KEY = booleanPreferencesKey("logged_in")
}