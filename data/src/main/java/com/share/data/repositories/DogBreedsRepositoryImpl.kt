package com.share.data.repositories

import com.google.gson.Gson
import com.share.data.db.DogBreedsDataBase
import com.share.data.db.entity.DogBreedsEntity
import com.share.data.db.entity.DogBreedsImageEntity
import com.share.data.source.response.ResponseDogImagesData
import com.share.data.platform.NetworkHandler
import com.share.data.source.DogBreedsApi
import com.share.data.utils.isUpdateSuccessful
import com.share.data.utils.mapToList
import com.share.domain.core.Result
import com.share.domain.exceptions.ConnectionException
import com.share.domain.model.DogBreed
import com.share.domain.model.DogBreedImage
import com.share.domain.repositories.DogBreedsRepository
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class DogBreedsRepositoryImpl @Inject constructor(
    val gson: Gson,
    val api: DogBreedsApi,
    val dataBase: DogBreedsDataBase,
    private val networkHandler: NetworkHandler
) : DogBreedsRepository {

    override fun getDogBreeds(): Single<Result<List<DogBreed>>> {
        return if (networkHandler.isConnected()) {
            api.getBreeds().flatMap {
                if (it.isSuccessful && it.body() != null) {
                    val dogBreedsEntityList = DogBreedsEntity.transformFromFromResponse(
                        it.body()!!.dogBreedsResponse
                    )
                    dataBase.dogBreedsDao().insertAllDogBreeds(dogBreedsEntityList)
                    dataBase.dogBreedsDao().getAllDogBreeds().flatMap { breeds ->
                        Single.just(Result.Success(gson.mapToList(breeds)))
                    }
                } else {
                    Single.just(Result.Error(ConnectionException()))
                }
            }
        } else {
            //TRY TO GET THE DATA FROM DATABASE
                dataBase.dogBreedsDao().getAllDogBreeds().flatMap {
                    Single.just(Result.Success(gson.mapToList(it)))
                }
        }
    }

    override fun getImagesByBreed(
        dogBreedId: Int,
        dogBreedName: String
    ): Single<Result<List<DogBreedImage>>> {
        return if(networkHandler.isConnected()) {
            api.getImagesByBreed(dogBreedName).flatMap {
                treatmentImageDataFromResponse(dogBreedId, it)
            }
        } else {
            //TRY TO GET THE DATA FROM DATABASE
            dataBase.dogImagesDao().getDogImagesByBreedId(dogBreedId).flatMap {
                Single.just(Result.Success(gson.mapToList(it)))
            }
        }
    }

    override fun getImagesBySubBreed(
        dogBreedId: Int,
        dogBreedName: String,
        dogSubBreedName: String
    ): Single<Result<List<DogBreedImage>>> {
        return if(networkHandler.isConnected()) {
            api.getImagesBySubBreed(dogBreedName, dogSubBreedName).flatMap {
                treatmentImageDataFromResponse(dogBreedId, it)
            }
        } else {
            //TRY TO GET THE DATA FROM DATABASE
            dataBase.dogImagesDao().getDogImagesByBreedId(dogBreedId).flatMap {
                Single.just(Result.Success(gson.mapToList(it)))
            }
        }
    }

    override fun getImagesFavorites(): Single<Result<List<DogBreedImage>>> {
        return dataBase.dogImagesDao().getDogFavoriteImages().flatMap {
            Single.just(Result.Success(gson.mapToList(it)))
        }
    }

    override fun setImagesBySubBreed(imageId: Int, isFavorite: Boolean): Single<Result<Boolean>> {
        return dataBase.dogImagesDao().updateFavoriteByImageId(imageId, isFavorite).flatMap {
             if (it.isUpdateSuccessful()) {
                Single.just(Result.Success(true))
            } else {
                Single.just(Result.Success(false))
            }
        }
    }

    private fun treatmentImageDataFromResponse(
        dogBreedId: Int,
        response: Response<ResponseDogImagesData>
    ): Single<Result<List<DogBreedImage>>> {
        return if (response.isSuccessful && response.body() != null) {
            val dogImages =
                DogBreedsImageEntity.transformFromResponse(dogBreedId, response.body()!!.dogImages.orEmpty())
            dataBase.dogImagesDao().insertAllDogImages(dogImages)
            dataBase.dogImagesDao().getDogImagesByBreedId(dogBreedId).flatMap {
                Single.just(Result.Success(gson.mapToList(it)))
            }
        } else {
            Single.just(Result.Error(ConnectionException()))
        }
    }
}