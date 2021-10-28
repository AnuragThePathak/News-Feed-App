package com.anurag.newsfeedapp.viewmodels

import androidx.lifecycle.*
import com.anurag.newsfeedapp.data.NewsFeedRepository
import com.anurag.newsfeedapp.data.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsFeedRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val category = savedStateHandle.getLiveData<String>("category")
    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.postValue(
                repository.getNewsFeed(
                    category.value ?: NewsFeedRepository.DEFAULT_CATEGORY
                )
            )
        }
    }
}
