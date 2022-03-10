package com.share.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.share.data.db.entity.DogBreedsEntity
import io.reactivex.rxjava3.core.Single

/**
 * @author Juan Sebastian Niño
 */
@Dao
interface DogBreedsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllDogBreeds(dogBreeds: List<DogBreedsEntity>)

    @Query("SELECT * FROM DOG_BREEDS_TABLE")
    fun getAllDogBreeds(): Single<List<DogBreedsEntity>>
}