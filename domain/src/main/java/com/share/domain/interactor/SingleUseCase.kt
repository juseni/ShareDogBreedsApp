package com.share.domain.interactor

import com.share.domain.core.Result
import com.share.domain.executor.PostExecutionThread
import com.share.domain.executor.ThreadExecutor
import com.share.domain.model.DogBreed
import com.share.domain.model.DogBreedImage
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Juan Sebastian Niño
 */
abstract class SingleUseCase<M>(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) {

    private val threadExecutorScheduler: Scheduler = Schedulers.from(threadExecutor)

    private val postExecutionThreadScheduler: Scheduler = postExecutionThread.scheduler

    abstract fun buildObservableForDogBreedImages(
        dogBreedId: Int,
        dogBreedName: String,
        dogSubBreedName: String?
    ): Single<Result<List<DogBreedImage>>>

    abstract fun buildObservableForBreeds(): Single<Result<List<DogBreed>>>

    abstract fun buildObservableForFavoritePictures(): Single<Result<List<DogBreedImage>>>

    abstract fun buildObservableForSetFavoriteImage(imageId: Int, isFavorite: Boolean): Single<Result<Boolean>>

    fun getDogImagesByBreed(
        dogBreedId: Int,
        dogBreedName: String,
        dogSubBreedName: String?
    ): Single<Result<List<DogBreedImage>>> {
        return buildObservableForDogBreedImages(
            dogBreedId,
            dogBreedName,
            dogSubBreedName
        ).subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }

    fun getFavoriteDogPictures(): Single<Result<List<DogBreedImage>>> {
        return buildObservableForFavoritePictures()
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }

    fun setFavoriteImage(imageId: Int, isFavorite: Boolean): Single<Result<Boolean>> {
        return buildObservableForSetFavoriteImage(imageId, isFavorite)
            .subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }

    fun getBreeds(): Single<Result<List<DogBreed>>> {
            return buildObservableForBreeds()
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler)
    }
}