package com.ewide.test.gamecatalogue.presentation.view.list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewide.test.gamecatalogue.databinding.FragmentGamesBinding
import com.ewide.test.gamecatalogue.presentation.adapter.GameAdapter
import com.ewide.test.gamecatalogue.presentation.view.BaseFragment
import com.ewide.test.gamecatalogue.presentation.viewmodel.GameViewModel
import com.ewide.test.gamecatalogue.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>(FragmentGamesBinding::inflate) {

    private val gameViewModel: GameViewModel by viewModels()
    private lateinit var gameAdapter: GameAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        gameAdapter = GameAdapter()
        binding.rvGames.apply {
            adapter = gameAdapter
        }
    }

    private fun initViewModel() {
        gameViewModel.setListGame("Batman")
        viewLifecycleOwner.lifecycleScope.launch {
            gameViewModel.getListGame.collect { response ->
                binding.apply {
                    progressBar.isVisible = response is Resource.Loading
                    rvGames.isVisible = response is Resource.Success
                    clError.isVisible = response is Resource.Error

                    if (response is Resource.Success) {
                        gameAdapter.submitList(response.data)
                        setSearch()
                    }

                    btnRefresh.setOnClickListener {
                        initViewModel()
                    }
                }
            }
        }
    }

    private fun setSearch() {
        binding.svGame.apply {
            setOnSearchClickListener {
                binding.tvTitle.isVisible = false
            }

            setOnCloseListener {
                binding.tvTitle.isVisible = true
                false
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    gameViewModel.setListGame(query.toString())
                    val imm =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(windowToken, 0)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }
}