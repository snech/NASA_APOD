package com.example.nasa.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasa.R
import com.example.nasa.base.observe
import com.example.nasa.databinding.FragmentFavBinding
import com.example.nasa.mvvm.view.adapter.FavAdapter
import com.example.nasa.mvvm.view.adapter.FavLoadStateAdapter
import com.example.nasa.mvvm.viewmodel.HomeViewModel
import com.example.nasa.mvvm.viewmodel.HomeViewModelIntent
import com.example.nasa.mvvm.viewmodel.HomeViewModelState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavFragment : Fragment(R.layout.fragment_fav) {
    private lateinit var binding: FragmentFavBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavAdapter(requireContext())
        binding.rvFav.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFav.adapter = adapter.withLoadStateFooter(
            FavLoadStateAdapter()
        )

        viewModel.sendIntent(HomeViewModelIntent.FetchFavList)

        observe(viewModel.getStateFlow()) {
            when (it) {
                is HomeViewModelState.FetchFavListData -> {
                    lifecycleScope.launch {
                        adapter.submitData(it.data)
                    }
                }
                else -> {}
            }
        }
    }

}