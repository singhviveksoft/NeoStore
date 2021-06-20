package com.example.neosoftassignmentproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.neosoftassignmentproject.model.AddAddress

@Database(entities = [AddAddress::class],version = 1)
abstract class DataBase:RoomDatabase() {
abstract val addresDao:AddressDao


    companion object {

        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "user_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}