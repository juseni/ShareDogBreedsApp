package com.share.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.share.data.db.dao.DogBreedsDao
import com.share.data.db.dao.DogImagesDao
import com.share.data.db.entity.DogBreedsEntity
import com.share.data.db.entity.DogBreedsImageEntity

/**
 * @author Juan Sebastian Niño
 */
@Database(entities = [DogBreedsEntity::class, DogBreedsImageEntity::class],
    version = 1)
abstract class DogBreedsDataBase : RoomDatabase() {

    abstract fun dogBreedsDao(): DogBreedsDao
    abstract fun dogImagesDao(): DogImagesDao
}