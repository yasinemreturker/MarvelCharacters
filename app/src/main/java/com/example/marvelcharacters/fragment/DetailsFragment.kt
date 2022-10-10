package com.example.marvelcharacters.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.data.model.CharacterSpotlight
import com.example.marvelcharacters.databinding.FragmentDetailsBinding
import com.example.marvelcharacters.databinding.UiResultStateBinding
import com.example.marvelcharacters.presentation.adapter.SpotlightsAdapter
import com.example.marvelcharacters.presentation.viewmodel.DetailsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.example.marvelcharacters.util.Result
import androidx.navigation.fragment.navArgs


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels()

    /**
     * The current scroll range in toolbar to scroll.
     */
    private var scrollRange = -1

    /**
     * The current visibility of toolbar title.
     */
    private var isShown = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.character = args.marvelCharacter

        binding.appBarLayout.addOnOffsetChangedListener(this)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        setupSpotlightRecyclerViews(
            binding.comicsRecyclerView,
            binding.eventsRecyclerView,
            binding.seriesRecyclerView,
            binding.storiesRecyclerView
        )

        observeSpotlightStates(
            Pair(viewModel.comics, binding.comicsResultState),
            Pair(viewModel.events, binding.eventsResultState),
            Pair(viewModel.series, binding.seriesResultState),
            Pair(viewModel.stories, binding.storiesResultState)
        )
    }

    override fun onDestroyView() {
        binding.appBarLayout.removeOnOffsetChangedListener(this)
        super.onDestroyView()
    }

    private fun setupSpotlightRecyclerViews(vararg recyclerViews: RecyclerView) {
        recyclerViews.forEach {
            with(it) {
                adapter = SpotlightsAdapter()
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
            }
        }
    }

    private fun observeSpotlightStates(
        vararg spotlights: Pair<LiveData<Result<List<CharacterSpotlight>>>, UiResultStateBinding>
    ) {
        spotlights.forEach { entry ->
            entry.first.observe(viewLifecycleOwner) {
                val resultStateBinding = entry.second

                resultStateBinding.loadingState.isVisible = it is Result.Loading
                resultStateBinding.successEmptyState.isVisible =
                    it is Result.Success && it.data.isEmpty()
                resultStateBinding.errorState.isVisible = it is Result.Error
            }
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout.totalScrollRange
        }

        if (scrollRange + verticalOffset == 0) {
            binding.toolbar.title = args.marvelCharacter.name
            binding.toolbar.setNavigationIcon(R.drawable.ic_back)
            isShown = true
        } else if (isShown) {
            binding.toolbar.title = ""
            binding.toolbar.setNavigationIcon(R.drawable.ic_back_bg)
            isShown = false
        }
    }
}