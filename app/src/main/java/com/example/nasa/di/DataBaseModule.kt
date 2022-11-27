package com.example.nasa.di

import android.content.Context
import com.example.nasa.databse.NasaDao
import com.example.nasa.databse.NasaDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesNasaDataBase(@ApplicationContext mContext: Context): NasaDataBase {
        return NasaDataBase.getInstance(mContext)
    }


    @Singleton
    @Provides
    fun providesNasaDao(db: NasaDataBase): NasaDao {
        return db.nasaDao()
    }
}