package com.example.foodsaver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FirstFragment:Fragment(R.layout.activity_fragment_first) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.activity_fragment_first, container, false)






//        setSupportActionBar(toolBar)
//        supportActionBar?.title = "MainActivity"
        // Inflate the layout for this fragment
        return rootView
    }
//    fun gotoItemActivity(v: View?) {
//        val intent = Intent(this, ItemActivity::class.java)
//        startActivity(intent)
//    }

//    fun gotoItemActivity(v: View?) {
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//    }
    
}