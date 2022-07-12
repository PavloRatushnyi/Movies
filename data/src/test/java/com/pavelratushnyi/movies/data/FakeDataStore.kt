package com.pavelratushnyi.movies.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.mutablePreferencesOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet

class FakeDataStore : DataStore<Preferences> {

    private var preferencesFlow = MutableStateFlow(mutablePreferencesOf())

    override val data: Flow<Preferences> = preferencesFlow

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        return preferencesFlow.updateAndGet {
            transform(it).toMutablePreferences()
        }
    }
}