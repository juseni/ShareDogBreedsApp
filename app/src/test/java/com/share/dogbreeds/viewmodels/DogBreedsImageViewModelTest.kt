package com.share.dogbreeds.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.share.dogbreeds.setup.RxImmediateSchedulerRule
import com.share.dogbreeds.utils.dogImagesBreeds
import com.share.dogbreeds.viewmodels.states.DogBreedsImagesState
import com.share.domain.core.Result
import com.share.domain.interactor.usecases.DogBreedsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

/**
 * @author Juan Sebastian Niño
 */
@RunWith(MockitoJUnitRunner::class)
class DogBreedsImageViewModelTest {

    @get:Rule
    val testScheduleRule = RxImmediateSchedulerRule()
    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    var rule2: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var useCase: DogBreedsUseCase

    private lateinit var viewModel: DogBreedsImageViewModel

    @Before
    fun setup() {
        given(useCase.getFavoriteDogPictures()).willReturn(
            Single.just(Result.Success(dogImagesBreeds.filter { it.isFavorite }))
        )
        viewModel = DogBreedsImageViewModel(useCase)
    }

    @Test
    fun getDogImagesFavoritesTest() {
        viewModel.observeDogImages()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (it as DogBreedsImagesState.UpdateDogImages).apply {
                    Assert.assertEquals(this.dogImages, listOf(dogImagesBreeds[1]))
                }
            }
        viewModel.getDogImages(isFromFavorites = true)
    }
}