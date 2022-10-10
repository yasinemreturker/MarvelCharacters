package com.example.marvelcharacters.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.example.marvelcharacters.R
import com.example.marvelcharacters.presentation.adapter.CharactersAdapter
import com.example.marvelcharacters.presentation.adapter.PagingLoadStateAdapter
import com.example.marvelcharacters.databinding.FragmentCharactersBinding
import com.example.marvelcharacters.presentation.viewmodel.CharactersViewModel


@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding

    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        setupCharactersRecyclerView()

    }

    private fun setupCharactersRecyclerView() {
        val charactersAdapter = CharactersAdapter(R.layout.item_character) {
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToDetailsFragment(it)
            )
        }

        with(binding.charactersRecyclerView) {
            adapter = charactersAdapter.withLoadStateHeaderAndFooter(
                PagingLoadStateAdapter { charactersAdapter.retry() },
                PagingLoadStateAdapter { charactersAdapter.retry() }
            )
        }

        viewModel.marvelCharacters.observe(viewLifecycleOwner) {
            charactersAdapter.submitData(lifecycle, it)
        }
    }

}