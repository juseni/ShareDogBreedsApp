package com.share.dogbreeds.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.share.dogbreeds.databinding.FragmentDogSubBreedsBinding
import com.share.dogbreeds.ui.adapters.DogBreedsAdapter
import com.share.dogbreeds.viewmodels.DogBreedsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño
 */
class DogSubBreedsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DogBreedsViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[DogBreedsViewModel::class.java]
    }

    private var _binding: FragmentDogSubBreedsBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: DogBreedsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogSubBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        adapter = DogBreedsAdapter(
            onSelected = {
                viewModel.getDogBreedDataBySubBreedName(it).also { subBreedItem ->
                    val directions = DogSubBreedsFragmentDirections.actionDogSubBreedFragmentToImageFragment(
                        subBreedItem.dogBreed,
                        subBreedItem.dogBreedId ?: 0,
                        subBreedItem.dogSubBreed,
                        false
                    )
                    findNavController().navigate(directions)
                }
            }
        )
        binding.recyclerViewDogBreeds.apply {
            setHasFixedSize(true)
            adapter = this@DogSubBreedsFragment.adapter
        }
        val bundleArgs = arguments?.let { DogSubBreedsFragmentArgs.fromBundle(it) }
        bundleArgs?.let {
            viewModel.getSubBreedsByBreed(it.dogBreedParam)
            binding.textViewBreed.text = it.dogBreedParam
        }
    }

    private fun setupObserver() {
        viewModel.dogSubBreedsMutableLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                setupViews(it)
            } else {
                setupError()
            }
        }
    }

    private fun setupViews(dogSubBreeds: List<String>) {
        binding.loadingDogBreeds.isVisible = false
        adapter.updateList(dogSubBreeds)
        binding.recyclerViewDogBreeds.isVisible = true
    }

    private fun setupError() {
        binding.loadingDogBreeds.isVisible = false
        binding.noDataContainer.isVisible = true
    }
}