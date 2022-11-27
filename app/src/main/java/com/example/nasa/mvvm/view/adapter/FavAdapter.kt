package com.example.nasa.mvvm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.databinding.LayoutApodMainViewBinding
import com.example.nasa.databse.FavDataEntity
import com.example.nasa.mvvm.NasaUtil
import com.example.nasa.mvvm.model.NasaAOPD

class FavAdapter(private val mContext: Context) : PagingDataAdapter<FavDataEntity, FavAdapter.FavViewHolder>(DIFF_CALLBACK) {
    class FavViewHolder(val viewBinding: LayoutApodMainViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavDataEntity>() {
            override fun areItemsTheSame(oldItem: FavDataEntity, newItem: FavDataEntity): Boolean =
                oldItem.imageDate == newItem.imageDate

            override fun areContentsTheSame(
                oldItem: FavDataEntity,
                newItem: FavDataEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val favDataEntity = getItem(position)
        favDataEntity?.let {
            holder.viewBinding.resultApod = NasaAOPD(
                imageTitle = it.imageTitle,
                imageUrl = it.imageUrl,
                imageExplanation = it.imageExplanation,
                imageDate = it.imageDate,
                videoLinkUrl = it.videoLinkUrl,
                isVideo = NasaUtil.isVideoTypeMedia(it.mediaType)
            )
            Glide.with(mContext)
                .load(it.imageUrl)
                .into(holder.viewBinding.ivImageOfDay)
        }
        holder.viewBinding.ivAddToFav.visibility = View.GONE
        holder.viewBinding.ivImageOfDay.setOnClickListener {
            favDataEntity?.let {
                if (NasaUtil.isVideoTypeMedia(it.mediaType)) {
                    NasaUtil.openBrowserToPlayVideo(it.videoLinkUrl, mContext)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            LayoutApodMainViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}