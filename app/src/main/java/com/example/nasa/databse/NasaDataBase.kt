package com.example.nasa.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavDataEntity::class, APODEntity::class], version = 1, exportSchema = false)
abstract class NasaDataBase : RoomDatabase() {

    abstract fun nasaDao(): NasaDao

    companion object {
        private var INSTANCE: NasaDataBase? = null

        fun getInstance(context: Context): NasaDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NasaDataBase::class.java,
                    "nasa_fav_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                }).build()

                INSTANCE = instance
                instance
            }
        }
    }
}