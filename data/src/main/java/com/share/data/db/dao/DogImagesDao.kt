package com.share.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.share.data.db.entity.DogBreedsImageEntity
import io.reactivex.rxjava3.core.Single

/**
 * @author Juan Sebastian Niño
 */
@Dao
interface DogImagesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllDogImages(dogImages: List<DogBreedsImageEntity>)

    @Query("SELECT * FROM DOG_BREEDS_IMAGE_TABLE T WHERE T.dogBreedId == :breedId")
    fun getDogImagesByBreedId(breedId: Int): Single<List<DogBreedsImageEntity>>

    @Query("SELECT * FROM DOG_BREEDS_IMAGE_TABLE T WHERE T.isFavorite == '1'")
    fun getDogFavoriteImages(): Single<List<DogBreedsImageEntity>>

    @Query("UPDATE DOG_BREEDS_IMAGE_TABLE SET isFavorite = :isFavorite WHERE dogBreedImageId == :imageId")
    fun updateFavoriteByImageId(imageId: Int, isFavorite: Boolean): Single<Int>
}