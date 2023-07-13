package com.ewide.test.gamecatalogue.presentation.view.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ewide.test.gamecatalogue.databinding.FragmentBookmarkGamesBinding
import com.ewide.test.gamecatalogue.presentation.adapter.FavoriteAdapter
import com.ewide.test.gamecatalogue.presentation.view.BaseFragment
import com.ewide.test.gamecatalogue.presentation.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BookmarkGamesFragment :
    BaseFragment<FragmentBookmarkGamesBinding>(FragmentBookmarkGamesBinding::inflate) {

    private val gameViewModel: GameViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        favoriteAdapter = FavoriteAdapter {
            findNavController().navigate(
                BookmarkGamesFragmentDirections.actionBookmarkGamesFragmentToDetailGamesFragment(
                    it.gameID
                )
            )
        }
        binding.rvGames.adapter = favoriteAdapter
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            gameViewModel.getFavorite().collect { response ->
                binding.tvEmpty.isVisible = response.isEmpty()

                if (response.isNotEmpty()) {
                    favoriteAdapter.submitList(response)
                }
            }
        }
    }
}