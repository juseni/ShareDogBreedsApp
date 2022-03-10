package com.share.data.source

import com.share.data.source.response.ResponseDogImagesData
import com.share.data.source.response.ResponseMessageData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @author Juan Sebastian Niño
 */
interface DogBreedsApi {

    companion object {
        private const val BREEDS = "breeds/list/all"
        private const val BREED = "breed"
        private const val SUB_BREED = "subBreed"
        private const val BREED_PARAM = "{breed}"
        private const val SUB_BREED_PARAM = "{subBreed}"
        private const val IMAGES_BY_BREED = "breed/$BREED_PARAM/images"
        private const val IMAGES_BY_SUB_BREED = "breed/$BREED_PARAM/$SUB_BREED_PARAM/images"
    }

    @Headers("Content-Type: application/json")
    @GET(BREEDS)
    fun getBreeds(): Single<Response<ResponseMessageData>>

    @Headers("Content-Type: application/json")
    @GET(IMAGES_BY_BREED)
    fun getImagesByBreed(@Path(BREED) breed: String): Single<Response<ResponseDogImagesData>>

    @Headers("Content-Type: application/json")
    @GET(IMAGES_BY_SUB_BREED)
    fun getImagesBySubBreed(
        @Path(BREED) breed: String,
        @Path(SUB_BREED) subBreed: String
    ): Single<Response<ResponseDogImagesData>>
}
