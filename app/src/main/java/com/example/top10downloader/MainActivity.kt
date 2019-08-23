package com.example.top10downloader

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.net.URL


class FeedEntry{
    var name: String =""
    var artistName: String =""
    var releaseDate: String =""
    var kind: String =""
    var artistURL: String =""
    override fun toString(): String {
        return """
            name = $name
            artist = $artistName
            releaseDate = $releaseDate
            kind = $kind
            artistUrl = $artistURL
            """.trimIndent()

    }
}

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    Log.d(TAG,"onCreate called")
    val downloadData = DownloadData()
        downloadData.execute("https://rss.itunes.apple.com/api/v1/us/ios-apps/new-apps-we-love/all/10/explicit.json")
        //("URL goes here" but up on line above)

    }

    companion object {
        private class DownloadData : AsyncTask<String, Void, String>(){
            private val TAG = "DownloadData"

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG, "onPostExecute: parameter is $result")
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG,"doInBackground: starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()){
                    Log.e(TAG,"doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {

                  return URL(urlPath).readText()

            }
        }
    }


}
