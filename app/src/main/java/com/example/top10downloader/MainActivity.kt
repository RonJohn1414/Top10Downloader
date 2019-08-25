package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates


class FeedEntry{

    var name: String =""
    var artist: String =""
    var releaseDate: String =""
    var price: String =""
    var rights: String =""
    override fun toString(): String {
        return """
            
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            price = $price
            rights = $rights
            """.trimIndent()

    }
}

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val downloadData by lazy {DownloadData(this, xmlListView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    Log.d(TAG,"onCreate called")
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topalbums/limit=10/xml")
        //("URL goes here" but up on line above)

    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) : AsyncTask<String, Void, String>(){
            private val TAG = "DownloadData"

            var propContext : Context by Delegates.notNull()
            var propListView : ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
               // Log.d(TAG, "onPostExecute: parameter is $result")
                val parseApplications = ParseApplications()
                parseApplications.parse(result)

//                val arrayAdapter = ArrayAdapter<FeedEntry>(propContext,R.layout.list_item,parseApplications.applications)
//                propListView.adapter = arrayAdapter

                val feedAdapter = FeedAdapter(propContext,R.layout.list_record,parseApplications.applications)
                propListView.adapter = feedAdapter

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
