package com.share.dogbreeds.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.share.dogbreeds.viewmodels.states.DogBreedsImagesState
import com.share.domain.core.Result
import com.share.domain.interactor.usecases.DogBreedsUseCase
import com.share.domain.model.DogBreedImage
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class DogBreedsImageViewModel @Inject constructor(
    private val useCase: DogBreedsUseCase
) : ViewModel(), LifecycleObserver {

    private val publishSubject = PublishSubject.create<DogBreedsImagesState>()
    fun observeDogImages(): Observable<DogBreedsImagesState> = publishSubject.hide()
    val setFavoriteMutableLiveData = MutableLiveData<Boolean>()

    fun getDogImages(
        dogBreedId: Int = 0,
        dogBreedName: String = "",
        dogSubBreedName: String? = null,
        isFromFavorites: Boolean = false
    ) {
        if (isFromFavorites) {
            getFavoritePictures()
        } else {
            getDogImagesByBreed(dogBreedId, dogBreedName, dogSubBreedName)
        }
    }

    private fun getFavoritePictures() {
        useCase.getFavoriteDogPictures()
            .subscribe({
                treatmentImageData(it)
            }, {
                publishSubject.onNext(DogBreedsImagesState.OnError(Exception(it.message)))
            })
    }

    private fun getDogImagesByBreed(dogBreedId: Int, dogBreedName: String, dogSubBreedName: String?) {
        useCase.getDogImagesByBreed(dogBreedId, dogBreedName, dogSubBreedName)
            .subscribe({
                treatmentImageData(it)
            }, {
                publishSubject.onNext(DogBreedsImagesState.OnError(Exception(it.message)))
            })
    }

    private fun treatmentImageData(result: Result<List<DogBreedImage>>) {
        when (result) {
            is Result.Success -> {
                if (result.data.isEmpty()) {
                    publishSubject.onNext(DogBreedsImagesState.NoImagesToShow)
                } else {
                    publishSubject.onNext(DogBreedsImagesState.UpdateDogImages(result.data))
                }
            }
            is Result.Error -> publishSubject.onNext(DogBreedsImagesState.OnError(result.exception))
        }
    }

    fun setImageFavorite(imageId: Int, isFavorite: Boolean) {
        useCase.setFavoriteImage(imageId, isFavorite)
            .subscribe({
                (it as Result.Success).apply {
                    setFavoriteMutableLiveData.postValue(data)
                }
            }, {
                setFavoriteMutableLiveData.postValue(false)
            })
    }
}