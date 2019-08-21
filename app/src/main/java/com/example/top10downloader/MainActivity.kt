package com.example.top10downloader

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    Log.d(TAG,"onCreate called")
    val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
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
                val xmlResult = StringBuilder()

                try {    // it tries each line and if works continues if not throws to catch an exception
                    val url = URL(urlPath)
                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val response = connection.responseCode
                    Log.d(TAG, "downloadXML: response code was $response")

//            val inputStream = connection.inputStream
//            val inputStreamReader = InputStreamReader(inputStream)
//            val reader = BufferedReader(inputStreamReader)    // these three are just like next line
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val inputBuffer = CharArray(500)
                    var charsRead = 0
                    while (charsRead >= 0) {
                        charsRead = reader.read(inputBuffer)
                        if (charsRead > 0) {
                            xmlResult.append(String(inputBuffer, 0, charsRead))
                        }
                    }
                    reader.close()
                    Log.d(TAG, "Received ${xmlResult.length} bytes")
                    return xmlResult.toString()

                } catch (e: MalformedURLException) {
                    Log.e(TAG, "downloadXML: Invalid URL ${e.message}")
                } catch (e: IOException) {
                    Log.e(TAG, "downloadXML: IO Exception reading data: ${e.message}")
                }catch (e: SecurityException){
                    Log.e(TAG,"downloadXML: Security exception. Needs permissions? ${e.message} ")
                }catch (e: Exception){
                    Log.e(TAG,"Unknown error: ${e.message}")
                }

                return ""  // if we get to here, there's been a problem. Return empty string
            }
        }
    }


}