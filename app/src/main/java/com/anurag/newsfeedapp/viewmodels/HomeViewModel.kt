package com.anurag.newsfeedapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.newsfeedapp.data.NewsFeedRepository
import com.anurag.newsfeedapp.data.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: NewsFeedRepository) : ViewModel() {
    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(IO) {
            _newsResponse.postValue(repository.getNewsFeed())
        }
    }
}
