package com.example.nasa.mvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasa.databinding.FavLoadStateViewBinding

class FavLoadStateAdapter : LoadStateAdapter<FavLoadStateAdapter.FavLoadStateViewHolder>() {
    class FavLoadStateViewHolder(val viewBinding: FavLoadStateViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: FavLoadStateViewHolder, loadState: LoadState) {
        holder.viewBinding.apply {
            ltFavLoadStateMain.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FavLoadStateViewHolder {
        return FavLoadStateViewHolder(
            FavLoadStateViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}