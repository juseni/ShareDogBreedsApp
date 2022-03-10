package com.share.dogbreeds.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.share.dogbreeds.databinding.FragmentMainBinding
import com.share.dogbreeds.ui.adapters.DogBreedsAdapter
import com.share.dogbreeds.viewmodels.DogBreedsViewModel
import com.share.dogbreeds.viewmodels.states.DogBreedsState
import com.share.domain.model.DogBreed
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.logging.Logger
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DogBreedsViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[DogBreedsViewModel::class.java]
    }

    private var compositeDisposable = CompositeDisposable()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: DogBreedsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDogBreeds()
        setupObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.currentDogBreeds.isNotEmpty()) {
            setupBreedViews(viewModel.currentDogBreeds)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DogBreedsAdapter(
            onSelected = {
                val directions =
                    if (viewModel.breedHasSubBreeds(it)) {
                        MainFragmentDirections.actionMainFragmentToDogSubBreedFragment(it)
                    } else {
                        val item = viewModel.getDogBreedDataByBreedName(it)
                        MainFragmentDirections.actionMainFragmentToImageFragment(
                            dogBreedIdParam = item.dogBreedId ?: 0,
                            dogBreedParam = item.dogBreed,
                            dogSubBreedParam = item.dogSubBreed,
                            showOnlyFavoritesParam = false
                        )
                    }
                findNavController().navigate(directions)
            }
        )
        binding.recyclerViewDogBreeds.apply {
            setHasFixedSize(true)
            adapter = this@MainFragment.adapter
        }
        binding.buttonGoToFavorites.setOnClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToImageFragment(
                "",
                0,
                null,
                true
            )
            findNavController().navigate(directions)
        }
    }

    private fun setupObserver() {
        viewModel.observeDogBreeds()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is DogBreedsState.UpdateDogBreeds -> setupBreedViews(it.dogBreeds)
                    is DogBreedsState.NoDataFound -> setupNoDataFound()
                    is DogBreedsState.OnError -> setupError(it.exception.toString())
                }
            }, {
                setupError(it.message.orEmpty())
            }).addTo(compositeDisposable)
    }

    private fun setupBreedViews(dogBreeds: List<DogBreed>) {
        binding.loadingDogBreeds.isVisible = false
        adapter.updateList(dogBreeds.map { it.dogBreed }.toSet().toList())
        binding.recyclerViewDogBreeds.isVisible = true
    }

    private fun setupNoDataFound() {
        binding.loadingDogBreeds.isVisible = false
        binding.noDataViewGroup.isVisible = true
        binding.textViewTryAgain.setOnClickListener {
            binding.loadingDogBreeds.isVisible = true
            binding.noDataViewGroup.isVisible = false
            viewModel.getDogBreeds()
        }
    }

    private fun setupError(error: String) {
        binding.loadingDogBreeds.isVisible = false
        Logger.getLogger("MainFragmentOnError", error)
        setupNoDataFound()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }
}