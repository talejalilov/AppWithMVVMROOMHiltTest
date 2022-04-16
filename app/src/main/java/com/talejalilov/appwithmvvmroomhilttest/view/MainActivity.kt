package com.talejalilov.appwithmvvmroomhilttest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talejalilov.appwithmvvmroomhilttest.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //burada fragment factory-ni var oldugunu gosteriyoruz
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}