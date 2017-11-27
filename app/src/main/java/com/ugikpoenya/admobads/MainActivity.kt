package com.ugikpoenya.admobads

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private var interval = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("LOG","Start Aplikasi")

        // menampilkan iklan banner
        adView.loadAd(AdRequest.Builder().build())
        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                Log.i("LOG","Banner Loaded")
            }
            override fun onAdFailedToLoad(errorCode : Int) {
                Log.i("LOG","Banner Failed to load")
            }
        }




        // membuat data pada LIST
        val dataModelArrayList = ArrayList<DataModel>()
        for (i in 1 until 100) {
            //input data
            var data=DataModel()
            data.viewType=1
            data.judul="Judul data "+i
            dataModelArrayList.add(data)

            //input banner setiap 10 data
            if (i%10==0){
                var banner=DataModel()
                banner.viewType=2
                dataModelArrayList.add(banner)
            }
        }


        //sett vertikal LIST
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.generateDefaultLayoutParams()
        recyclerView.layoutManager = layoutManager

        // tampilkan data List
        var dataAdapter = DataAdapter(dataModelArrayList,this)
        recyclerView?.adapter = dataAdapter





        //set iklan interstitial
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd?.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
        mInterstitialAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.i("LOG", "Interstitial Loaded ")
                interval = 0
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                super.onAdFailedToLoad(errorCode)
                Log.i("LOG", "Interstitial Failed to load")
            }

            //Iklan INterstitial langsung di Request lagi ketika Iklan Interstitial di tutup
            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }
        }
    }


    //untuk menampilkan iklan INterstitial
    fun loadInterstitial() {
        interval++
        if (mInterstitialAd!!.isLoaded) {
            if (interval >= 3) {       // interval 3 kali pemanggilan baru iklan INterstitial Muncul
                mInterstitialAd?.show()
                Log.i("LOG", "Interstitial $interval => Show")
            } else
                Log.i("LOG", "Interstitial " + interval)
        } else
            Log.i("LOG", "Interstitial $interval => Not Loaded")
    }

}
