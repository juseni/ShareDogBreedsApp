package com.share.data.di

import android.content.Context
import androidx.room.Room
import com.share.data.db.DogBreedsDataBase
import dagger.Module
import dagger.Provides

/**
 * @author Juan Sebastian Niño
 */
@Module
class DataBaseModule {

    @Provides
    fun provideRoom(context: Context): DogBreedsDataBase {
        return synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                DogBreedsDataBase::class.java,
                "dog_breeds_database"
            )
                .fallbackToDestructiveMigration()
                .build()
            instance
        }
    }
}
