package com.share.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author Juan Sebastian Niño
 */
@Entity(
    tableName = "dog_breeds_table",
    indices = [Index(value = ["dogBreed", "dogSubBreed"], unique = true)]
)
data class DogBreedsEntity(
    @PrimaryKey(autoGenerate = true)
    val dogBreedId: Int? = null,
    val dogBreed: String,
    val dogSubBreed: String? = null
) {
    companion object {
        fun transformFromFromResponse(responseMessageData: Map<String, List<String>>): List<DogBreedsEntity> {
            val dogBreedsEntity = mutableListOf<DogBreedsEntity>()
            responseMessageData.forEach { (breed, subBreeds) ->
                if (subBreeds.isNotEmpty()) {
                    subBreeds.forEach {
                        dogBreedsEntity.add(getItem(breed, it))
                    }
                } else {
                    dogBreedsEntity.add(getItem(dogBreed = breed))
                }
            }
            return dogBreedsEntity
        }

        private fun getItem(dogBreed: String, dogSubBreed: String? = null) =
            DogBreedsEntity(
                dogBreed = dogBreed,
                dogSubBreed = dogSubBreed
            )
    }
}