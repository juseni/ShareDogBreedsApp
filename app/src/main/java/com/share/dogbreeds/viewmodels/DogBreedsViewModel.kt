package com.share.dogbreeds.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.share.dogbreeds.viewmodels.states.DogBreedsState
import com.share.domain.core.Result
import com.share.domain.interactor.usecases.DogBreedsUseCase
import com.share.domain.model.DogBreed
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class DogBreedsViewModel @Inject constructor(
    private val useCase: DogBreedsUseCase
) : ViewModel(), LifecycleObserver {

    var currentDogBreeds: List<DogBreed> = emptyList()

    val dogSubBreedsMutableLiveData = MutableLiveData<List<String>>()

    private val publishSubject = PublishSubject.create<DogBreedsState>()
    fun observeDogBreeds(): Observable<DogBreedsState> = publishSubject.hide()

    fun getDogBreeds() {
        useCase.getBreeds()
            .subscribe({
                when (it) {
                    is Result.Success -> {
                        currentDogBreeds = it.data
                        if (it.data.isEmpty()) {
                            publishSubject.onNext(DogBreedsState.NoDataFound)
                        } else {
                            publishSubject.onNext(DogBreedsState.UpdateDogBreeds(it.data))
                        }
                    }
                    is Result.Error -> publishSubject.onNext(DogBreedsState.OnError(it.exception))
                }
            }, {
                publishSubject.onNext(DogBreedsState.OnError(Exception(it.message)))
            })
    }

    fun breedHasSubBreeds(breed: String) =
        currentDogBreeds.filter { it.dogBreed == breed }[0].dogSubBreed != null

    fun getSubBreedsByBreed(breed: String) =
        currentDogBreeds.filter { it.dogBreed == breed }.map { it.dogSubBreed ?: "" }.also {
            dogSubBreedsMutableLiveData.postValue(it)
        }

    fun getDogBreedDataByBreedName(breed: String) = currentDogBreeds.first { it.dogBreed == breed }

    fun getDogBreedDataBySubBreedName(subBreed: String) =
        currentDogBreeds.first { it.dogSubBreed == subBreed}
}