package com.example.top10downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(v: View){
   val tvName: TextView = v.findViewById(R.id.tvName)
   val tvArtist: TextView = v.findViewById(R.id.tvArtist)
   val tvReleasedate: TextView =v.findViewById(R.id.tvReleaseDate)
   val tvPrice: TextView = v.findViewById(R.id.tvPrice)
   val tvRights: TextView = v.findViewById(R.id.tvRights)

}


class FeedAdapter(context: Context, private val resource: Int, private var applications: List<FeedEntry>)
   : ArrayAdapter<FeedEntry>(context, resource) {
  // private val TAG = "FeedAdapter"
   private val inflater = LayoutInflater.from(context)

   fun setFeedList(feedList: List<FeedEntry>){
      this.applications = feedList
      notifyDataSetChanged()
   }

override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

 //  Log.d(TAG, "getView() called")
   val view:View
   val viewHolder: ViewHolder
   if (convertView == null){
     // Log.d(TAG, "getView called with null converter")
      view = inflater.inflate(resource,parent,false)
      viewHolder = ViewHolder(view)
      view.tag = viewHolder
   }else {
      //Log.d(TAG, "getView provided a convertView")
      view = convertView
      viewHolder = view.tag as ViewHolder
   }

   val currentApp = applications[position]
   viewHolder.tvName.text = currentApp.name
   viewHolder.tvArtist.text = currentApp.artist
   viewHolder.tvReleasedate.text = currentApp.releaseDate
   viewHolder.tvPrice.text = currentApp.price
   viewHolder.tvRights.text = currentApp.rights

   return view
}

   override fun getCount(): Int {
      //  Log.d(TAG, "getCount() called")
      return applications.size
   }
}