package com.example.nasa.mvvm.viewmodel

import androidx.paging.PagingData
import com.example.nasa.mvvm.model.NasaAOPD
import com.example.nasa.base.ViewModelIntent
import com.example.nasa.base.ViewModelState
import com.example.nasa.databse.FavDataEntity


sealed class HomeViewModelIntent : ViewModelIntent {
    data class LoadAPOD(val searchDate: String) : HomeViewModelIntent()
    object FetchFavList : HomeViewModelIntent()
    data class InsertFavData(val favData: NasaAOPD) : HomeViewModelIntent()
}

sealed class HomeViewModelState : ViewModelState {
    object Loading : HomeViewModelState()
    class Error(val errorMsg: String) : HomeViewModelState()
    class GetAPOD(val data: NasaAOPD) : HomeViewModelState()
    class FetchFavListData(val data: PagingData<FavDataEntity>) : HomeViewModelState()
    object AddItemToFavDatabase : HomeViewModelState()
}