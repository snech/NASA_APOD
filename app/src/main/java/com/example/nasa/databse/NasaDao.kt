package com.example.nasa.databse

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NasaDao {
    @Query("SELECT * FROM favourite_table LIMIT :size OFFSET :offset")
    suspend fun getFavList(size: Int, offset: Int): List<FavDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(favDataEntity: FavDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAPOD(apodEntity: APODEntity)

    @Query("SELECT * FROM nasa_apod_table LIMIT 1")
    suspend fun getLatestAPOD(): APODEntity

    @Query("DELETE FROM nasa_apod_table")
    suspend fun deleteAllAOPD()
}