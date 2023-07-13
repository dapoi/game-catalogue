package com.ewide.test.gamecatalogue.presentation.view.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ewide.test.gamecatalogue.data.source.remote.model.DealsItem
import com.ewide.test.gamecatalogue.data.source.remote.model.DetailResponse
import com.ewide.test.gamecatalogue.databinding.FragmentDetailGamesBinding
import com.ewide.test.gamecatalogue.presentation.adapter.DetailAdapter
import com.ewide.test.gamecatalogue.presentation.view.BaseFragment
import com.ewide.test.gamecatalogue.presentation.viewmodel.GameViewModel
import com.ewide.test.gamecatalogue.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class DetailGamesFragment :
    BaseFragment<FragmentDetailGamesBinding>(FragmentDetailGamesBinding::inflate) {

    private val gameViewModel: GameViewModel by viewModels()
    private val detailAdapter by lazy { DetailAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        initViewModel()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            gameViewModel.getDetail.collect { response ->
                binding.apply {
                    progressBar.isVisible = response is Resource.Loading
                    clContent.isVisible = response is Resource.Success
                    clError.isVisible = response is Resource.Error

                    if (response is Resource.Success) {
                        initUI(response.data)
                    }
                }
            }
        }
    }

    private fun initUI(result: DetailResponse) {
        binding.apply {
            Glide.with(requireContext())
                .load(result.info?.thumb)
                .into(ivGame)

            tvName.text = result.info?.title

            val formatter = result.cheapestPriceEver?.date?.let { epochToIso8601(it.toLong()) }
            tvDate.text = formatter

            initAdapter(result.deals)
        }
    }

    private fun initAdapter(deals: List<DealsItem?>?) {
        binding.rvPrice.apply {
            adapter = detailAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            detailAdapter.submitList(deals)
        }
    }

    private fun epochToIso8601(time: Long): String {
        val format = "dd MMM yyyy HH:mm:ss" // you can add the format you need
        val sdf = SimpleDateFormat(format, Locale.getDefault()) // default local
        sdf.timeZone = TimeZone.getDefault() // set anytime zone you need
        return sdf.format(Date(time * 1000))
    }
}