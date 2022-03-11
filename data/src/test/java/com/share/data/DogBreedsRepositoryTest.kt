package com.share.data

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.given
import com.share.data.db.DogBreedsDataBase
import com.share.data.platform.NetworkHandler
import com.share.data.repositories.DogBreedsRepositoryImpl
import com.share.data.setup.RxImmediateSchedulerRule
import com.share.data.source.DogBreedsApi
import com.share.data.utils.dogBreedEntity
import com.share.data.utils.dogBreedImagesEntity
import com.share.data.utils.responseDogBreed
import com.share.data.utils.responseDogImagesByBreed
import com.share.data.utils.responseDogImagesBySubBreed
import com.share.domain.core.Result
import com.share.domain.exceptions.ConnectionException
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import retrofit2.Response

/**
 * @author Juan Sebastian Niño
 */
@RunWith(MockitoJUnitRunner::class)
class DogBreedsRepositoryTest {

    @get:Rule
    val testScheduleRule = RxImmediateSchedulerRule()

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private var gson = Gson()

    @Mock
    lateinit var api: DogBreedsApi

    @Mock
    lateinit var networkChecker: NetworkHandler

    @Mock
    lateinit var database: DogBreedsDataBase

    private lateinit var repositoryImpl: DogBreedsRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        given(networkChecker.isConnected()).willReturn(true)
        given(api.getBreeds()).willReturn(Single.just(Response.success(responseDogBreed)))
        given(api.getImagesByBreed("african")).willReturn(
            Single.just(
                Response.success(
                    responseDogImagesByBreed
                )
            )
        )
        given(api.getImagesBySubBreed("hound", "blood")).willReturn(
            Single.just(Response.success(responseDogImagesBySubBreed))
        )
        repositoryImpl = DogBreedsRepositoryImpl(gson, api, database, networkChecker)
    }

    @Test
    fun getDogBreedsTest() {
        repositoryImpl.getDogBreeds().map {
            Assert.assertEquals(Result.Success(dogBreedEntity), it)
        }
    }

    @Test
    fun getDogBreedsTestErrorTest() {
        given(api.getBreeds()).willReturn(
            Single.just(
                Response.error(
                    400,
                    "{\"key\":[\"somestuff\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
        )
        repositoryImpl.getDogBreeds().map {
            Assert.assertEquals(Result.Error(ConnectionException()), it)
        }
    }

    @Test
    fun getImagesByBreedTest() {
        repositoryImpl.getImagesByBreed(1, "african").map {
            Assert.assertEquals(Result.Success(dogBreedImagesEntity.first()), it)
        }
    }

    @Test
    fun getImagesBySubBreedTest() {
        repositoryImpl.getImagesBySubBreed(2, "hound", "blood").map {
            Assert.assertEquals(
                Result.Success(dogBreedImagesEntity.filter { item -> item.dogBreedId == 2 }),
                it
            )
        }
    }
}