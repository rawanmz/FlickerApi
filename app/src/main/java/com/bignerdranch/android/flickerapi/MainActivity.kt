package com.bignerdranch.android.flickerapi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
val photoGalleryFragment=PhotoGalleryFragment()
      buttomNav.setOnNavigationItemSelectedListener {
when(it.itemId) {
    R.id.map -> openMapActivity()
    R.id.home -> openPhotoGalleryFragment(photoGalleryFragment)
}
            true
        }
    }
    fun openMapActivity(): Boolean {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        return true
    }
    fun openPhotoGalleryFragment(fragment: PhotoGalleryFragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,fragment)
                .commit()
        }
    }
}