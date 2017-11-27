package com.ugikpoenya.admobads

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.item_list_data.view.*

/**
 * Created by Sugeng on 26/11/2017.
 */

class DataAdapter(private val dataModelArrayList: ArrayList<DataModel>, private val activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            1 -> {
                val v = inflater.inflate(R.layout.item_list_data, parent, false)
                viewHolder = DataViewHolder(v)
            }
            2-> {
                val v = inflater.inflate(R.layout.item_list_admob, parent, false)
                viewHolder = AdmobViewHolder(v)
            }
        }
        return viewHolder
    }


    //Menampilkan iklan pada list
    class AdmobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mAdView: AdView = view.findViewById<View>(R.id.adView) as AdView
        init {
            mAdView.visibility = View.GONE
            mAdView.loadAd(AdRequest.Builder().build())
            mAdView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    mAdView.visibility = View.VISIBLE
                    Log.i("LOG","Banner List Loaded")
                }
                override fun onAdFailedToLoad(errorCode : Int) {
                    Log.i("LOG","Banner List Failed to load")
                }
            }

        }
    }


    // memnampilkan data list
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(dataModel: DataModel, activity: Activity) = with(itemView) {
            txtJudul.text = dataModel.judul
            setOnClickListener {
                Log.i("LOG", "Klik "+dataModel.judul)
                (activity as MainActivity).loadInterstitial()    // menampilkan iklan interstitial
            }
        }
    }




    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).viewType) {
            1 -> {
                val holder = viewHolder as DataViewHolder
                holder.bind(getItem(position),activity)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataModelArrayList[position].viewType!!
    }
    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }
    private fun getItem(position: Int): DataModel {
        return dataModelArrayList[position]
    }



}