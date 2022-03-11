package com.share.dogbreeds.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.share.dogbreeds.setup.RxImmediateSchedulerRule
import com.share.dogbreeds.utils.dogBreeds
import com.share.dogbreeds.viewmodels.states.DogBreedsState
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
class DogBreedsViewModelTest {

    @get:Rule
    val testScheduleRule = RxImmediateSchedulerRule()
    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    var rule2: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var useCase: DogBreedsUseCase

    private lateinit var viewModel: DogBreedsViewModel

    @Before
    fun setup() {
        given(useCase.getBreeds()).willReturn(
            Single.just(Result.Success(dogBreeds))
        )
        viewModel = DogBreedsViewModel(useCase)
        viewModel.currentDogBreeds = dogBreeds
    }

    @Test
    fun getDogBreedsTest() {
        viewModel.observeDogBreeds()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (it as DogBreedsState.UpdateDogBreeds).apply {
                    Assert.assertEquals(this.dogBreeds, dogBreeds)
                }
            }
        viewModel.getDogBreeds()
    }

    @Test
    fun breedHasSubBreedsTest() {
        Assert.assertTrue(viewModel.breedHasSubBreeds("hound"))
    }

    @Test
    fun getSubBreedsByBreedTest() {
        viewModel.dogSubBreedsMutableLiveData.observeForever {
            Assert.assertEquals(it, listOf("blood", "english"))
        }
        viewModel.getSubBreedsByBreed("hound")
    }

    @Test
    fun getDogBreedDataByBreedNameTest() {
        Assert.assertEquals(
            viewModel.getDogBreedDataByBreedName("african"),
            dogBreeds.first()
        )
    }

    @Test
    fun getDogBreedDataBySubBreedNameTest() {
        Assert.assertEquals(
            viewModel.getDogBreedDataBySubBreedName("blood"),
            dogBreeds[1]
        )
    }
}