package com.example.nasa.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nasa.databse.NasaDao
import com.example.nasa.databse.FavDataPagingSource

class FavViewModel(private val nasaDao: NasaDao) : ViewModel() {
    val favDataFlow = Pager(PagingConfig(3)) { FavDataPagingSource(nasaDao) }
        .flow
        .cachedIn(viewModelScope)
}