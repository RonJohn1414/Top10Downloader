package com.example.top10downloader

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import java.util.*

private const val TAG = "FeedViewModel"
val EMPTY_FEED_LIST: List<FeedEntry> = Collections.emptyList()

class FeedViewModel : ViewModel(), DownloadData.DownloaderCallback{

    private var downloadData: DownloadData? = null
    private var feedCachedUrl = "INVALIDATED"

    private val feed = MutableLiveData<List<FeedEntry>>()
    val feedEntries: LiveData<List<FeedEntry>>
    get() = feed

    init {
        feed.postValue(EMPTY_FEED_LIST)
    }

   fun downloadUrl(feedUrl: String){
       Log.d(TAG,"downloadUrl: called with url $feedUrl")
        if (feedUrl != feedCachedUrl){
            Log.d(TAG,"downloadUrl starting AsyncTask")
            downloadData = DownloadData(this)
            downloadData?.execute(feedUrl)
            feedCachedUrl = feedUrl
            Log.d(TAG,"download Url done")
        }else {
            Log.d(TAG, "downloadUrl - URL not changed")
        }

    }

    fun invalidate(){
        feedCachedUrl = "INVALIDATE"
    }

    override fun onDataAvailable(data: List<FeedEntry>) {
        Log.d(TAG,"OnDataAvailable: called +++++++")
        feed.value = data
        Log.d(TAG,"OnDataAvailable: ends  ----------")
    }

    override fun onCleared() {
        Log.d(TAG,"onCleared: cancelling pending downloads")
        downloadData?.cancel(true)
    }

}