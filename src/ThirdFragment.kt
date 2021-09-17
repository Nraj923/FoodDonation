package com.example.foodsaver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
//import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.*

class ThirdFragment:Fragment(R.layout.activity_fragment_third) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.activity_fragment_third, container, false)
//        setSupportActionBar(toolBar)
//        supportActionBar?.title = "MainActivity"
        // Inflate the layout for this fragment
        return rootView
    }



}