package com.example.top10downloader

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


private const val TAG = "DownloadData"

 class DownloadData(private val callback: DownloaderCallback) : AsyncTask<String, Void, String>(){

     interface DownloaderCallback{
      fun onDataAvailable(data: List<FeedEntry>)
     }

    override fun onPostExecute(result: String) {

        val parseApplications = ParseApplications()
        if (result.isNotEmpty()){
            parseApplications.parse(result)
        }
        callback.onDataAvailable(parseApplications.applications)
    }

    override fun doInBackground(vararg url: String): String {
         Log.d(TAG,"doInBackground: starts with ${url[0]}")
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()){
            Log.e(TAG,"doInBackground: Error downloading")
        }
        return rssFeed
    }

    private fun downloadXML(urlPath: String): String {

        try {

        return URL(urlPath).readText()
    }catch (e: MalformedURLException){
            Log.d(TAG,"downloadXML: InvalidURL ${e.message}")
        }catch (e: IOException){
            Log.d(TAG,"downloadXML: IO exception reading data ${e.message}")
        }catch (e: SecurityException){
            Log.d(TAG,"downloadXML: Security exception. Needs permission? ${e.message}")
            // e.printStackTrace()
        }
        return ""  // return an empty string if there was an exception
    }
}