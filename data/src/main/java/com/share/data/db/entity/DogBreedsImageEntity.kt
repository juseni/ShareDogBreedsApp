package com.share.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author Juan Sebastian Niño
 */
@Entity(
    tableName = "dog_breeds_image_table",
    foreignKeys = [ForeignKey(
        entity = DogBreedsEntity::class,
        parentColumns = ["dogBreedId"],
        childColumns = ["dogBreedId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["dogBreedId", "imageUrl"], unique = true)]
)
data class DogBreedsImageEntity(
    @PrimaryKey(autoGenerate = true)
    val dogBreedImageId: Int? = null,
    val dogBreedId: Int,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false
) {
    companion object {
        fun transformFromResponse(dogBreedId: Int, dogImages: List<String>): List<DogBreedsImageEntity> =
            dogImages.map { DogBreedsImageEntity(dogBreedId = dogBreedId, imageUrl = it) }
    }
}