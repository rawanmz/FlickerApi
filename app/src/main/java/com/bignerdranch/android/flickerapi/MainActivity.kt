package com.bignerdranch.android.flickerapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_favorite.*
class MainActivity : AppCompatActivity() {
 lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val photoGalleryFragment=PhotoGalleryFragment()
        val favoriteFragment=FavoriteFragment()
        val mapFragment=MapsFragment()
        makeCurrentFragment(photoGalleryFragment)
        bottomNavigation=findViewById(R.id.buttomNav) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener {
        when(it.itemId) {
            R.id.map -> makeCurrentFragment(mapFragment)
            R.id.home -> makeCurrentFragment(photoGalleryFragment)
            R.id.like -> makeCurrentFragment(favoriteFragment)
        }
            true
        }
    }

    fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.warpper,fragment)
                .commit()
        }
}