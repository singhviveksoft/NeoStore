package com.example.neosoftassignmentproject.constants

import android.content.Context

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")

class UserPreferences(context: Context) {
  private  val _appicationContext=context.applicationContext

    private val mDataStore: DataStore<Preferences> = _appicationContext.dataStore

    companion object{
        val USER_ID_KEY= stringPreferencesKey("access_token")
        val ADDRESS= stringPreferencesKey("address")
    }

    suspend fun setAccesToken(access_token:String){
        mDataStore.edit {
            it[USER_ID_KEY]=access_token
        }
    }

    val getAccessToken: Flow<String> = mDataStore.data.map {
        it[USER_ID_KEY] ?: "0"
    }

    suspend fun setAddress(address:String){
        mDataStore.edit {
            it[ADDRESS]=address
        }
    }

    val getAddress:Flow<String> = mDataStore.data.map {
        it[ADDRESS] ?:"0"
    }


 suspend fun clearData(){
    mDataStore.edit {
        it.clear()
    }
}



}