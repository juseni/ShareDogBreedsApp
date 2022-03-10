package com.share.domain.interactor.usecases

import com.share.domain.core.Result
import com.share.domain.executor.PostExecutionThread
import com.share.domain.executor.ThreadExecutor
import com.share.domain.interactor.SingleUseCase
import com.share.domain.model.DogBreed
import com.share.domain.model.DogBreedImage
import com.share.domain.repositories.DogBreedsRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class DogBreedsUseCase @Inject constructor(
    private val repository: DogBreedsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Any>(threadExecutor, postExecutionThread) {

    override fun buildObservableForDogBreedImages(
        dogBreedId: Int,
        dogBreedName: String,
        dogSubBreedName: String?
    ): Single<Result<List<DogBreedImage>>> =
        if (dogSubBreedName != null) {
            repository.getImagesBySubBreed(dogBreedId, dogBreedName, dogSubBreedName)
        } else {
            repository.getImagesByBreed(dogBreedId, dogBreedName)
        }

    override fun buildObservableForBreeds(): Single<Result<List<DogBreed>>> =
        repository.getDogBreeds()

    override fun buildObservableForSetFavoriteImage(
        imageId: Int,
        isFavorite: Boolean
    ): Single<Result<Boolean>> =
        repository.setImagesBySubBreed(imageId, isFavorite)

    override fun buildObservableForFavoritePictures(): Single<Result<List<DogBreedImage>>> =
        repository.getImagesFavorites()
}