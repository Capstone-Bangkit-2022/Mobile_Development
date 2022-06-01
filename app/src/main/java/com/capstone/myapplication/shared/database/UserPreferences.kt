package com.capstone.myapplication.shared.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.GsonBuilder
import com.capstone.myapplication.shared.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val USER = stringPreferencesKey("user")

    fun getUser(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER]
        }
    }

    suspend fun resetUser() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit {
            it[USER] = GsonBuilder().create().toJson(user)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}