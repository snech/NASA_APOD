package com.example.nasa.mvvm.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nasa.base.BaseViewModel
import com.example.nasa.base.results.NasaResult
import com.example.nasa.databse.NasaDao
import com.example.nasa.databse.FavDataEntity
import com.example.nasa.databse.FavDataPagingSource
import com.example.nasa.domain.usecase.GetAPODUseCase
import com.example.nasa.domain.usecase.InsertFavUseCase
import com.example.nasa.mvvm.model.NasaAOPD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAPODUseCase: GetAPODUseCase, private val insertFavUseCase: InsertFavUseCase
) : BaseViewModel<HomeViewModelIntent, HomeViewModelState>() {


    override fun sendIntent(intent: HomeViewModelIntent) {
        when (intent) {
            is HomeViewModelIntent.LoadAPOD -> loadAPOD(intent.searchDate)
            is HomeViewModelIntent.FetchFavList -> fetchFavListData()
            is HomeViewModelIntent.InsertFavData -> addItemToFavDataBase(intent.favData)
        }
    }

    private fun loadAPOD(searchDate: String) {
        viewModelScope.launch {
            HomeViewModelState.Loading.updateState()
            when (val result = getAPODUseCase.execute(searchDate)) {
                is NasaResult.Success -> HomeViewModelState.GetAPOD(result.response).updateState()
                is NasaResult.Fail.Error -> HomeViewModelState.Error(result.errorMessage)
                    .updateState()
                else -> {}
            }
        }
    }

    private fun fetchFavListData() {
        viewModelScope.launch {
            insertFavUseCase.getFavList().cachedIn(viewModelScope)
                .collectLatest { HomeViewModelState.FetchFavListData(it).updateState() }
        }
    }

    private fun addItemToFavDataBase(favData: NasaAOPD) {
        viewModelScope.launch {
            when (val result = insertFavUseCase.insert(favData)) {
                is NasaResult.Success -> {
                    HomeViewModelState.AddItemToFavDatabase.updateState()
                }
                else -> {}
            }
        }
    }
}