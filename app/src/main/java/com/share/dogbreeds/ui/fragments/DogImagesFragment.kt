package com.share.dogbreeds.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.share.dogbreeds.R
import com.share.dogbreeds.databinding.FragmentDogImagesBinding
import com.share.dogbreeds.ui.adapters.DogBreedsImagesAdapter
import com.share.dogbreeds.viewmodels.DogBreedsImageViewModel
import com.share.dogbreeds.viewmodels.states.DogBreedsImagesState
import com.share.domain.model.DogBreedImage
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class DogImagesFragment : DaggerFragment() {

    companion object {
        private const val CONCAT = " / "
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DogBreedsImageViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[DogBreedsImageViewModel::class.java]
    }

    private var _binding: FragmentDogImagesBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: DogBreedsImagesAdapter
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundleArgs = arguments?.let { DogImagesFragmentArgs.fromBundle(it) }
        adapter = DogBreedsImagesAdapter(
            onSelected = {
                binding.loadingDogBreeds.isVisible = true
                viewModel.setImageFavorite(it.dogBreedImageId, it.isFavorite.not())
            }
        )
        binding.recyclerViewDogBreedsImages.apply {
            setHasFixedSize(true)
            adapter = this@DogImagesFragment.adapter
        }
        setupObservers()
        bundleArgs?.let {
            if (it.showOnlyFavoritesParam) {
                setScreenForFavoritesPictures()
            } else {
                setScreenForBreedPictures(it)
            }

        }
    }

    private fun setScreenForFavoritesPictures() {
        binding.textViewInfo.text =  getString(R.string.favorites_screen)
        viewModel.getDogImages(isFromFavorites = true)
    }

    private fun setScreenForBreedPictures(data: DogImagesFragmentArgs) {
        binding.textViewInfo.text =
            if (data.dogSubBreedParam != null) {
                data.dogBreedParam.plus(CONCAT).plus(data.dogSubBreedParam)
            } else {
                data.dogBreedParam
            }
         data.dogBreedParam
        viewModel.getDogImages(
            data.dogBreedIdParam,
            data.dogBreedParam,
            data.dogSubBreedParam,
            data.showOnlyFavoritesParam
        )
    }

    private fun setupObservers() {
        viewModel.observeDogBreeds()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is DogBreedsImagesState.UpdateDogBreeds -> setupImages(it.dogBreeds)
                    is DogBreedsImagesState.NoImagesToShow -> setupNoImagesContainer()
                    is DogBreedsImagesState.OnError -> setupError()
                }
            }, {
                setupError()
            }).addTo(compositeDisposable)

        viewModel.setFavoriteMutableLiveData.observe(viewLifecycleOwner) { isSuccess ->
            binding.loadingDogBreeds.isVisible = false
            Snackbar.make(
                binding.root, getString(
                    if (isSuccess) { R.string.set_favorite_success }
                    else { R.string.set_favorite_unsuccess }
                ),
                Snackbar.LENGTH_SHORT
            ).apply {
                if (isSuccess.not()) {
                    setAction(R.string.try_again) { viewModel.setImageFavorite(1, true) }
                }
            }.show()
        }
    }

    private fun setupImages(dogImages: List<DogBreedImage>) {
        binding.loadingDogBreeds.isVisible = false
        adapter.updateList(dogImages)
        binding.recyclerViewDogBreedsImages.isVisible = true
    }

    private fun setupNoImagesContainer() {
        binding.loadingDogBreeds.isVisible = false
        binding.noImagesContainer.isVisible = true
    }

    private fun setupError() {
        binding.loadingDogBreeds.isVisible = false
        binding.recyclerViewDogBreedsImages.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }
}