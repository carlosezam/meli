package com.ezam.melichallenge.search.presentation.search_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor() : ViewModel() {

    private val state = MutableStateFlow(SearchListState())
    fun getState() = state.asStateFlow()


    fun onErrorShown(){
        state.update {
            it.copy( error = null)
        }
    }
}