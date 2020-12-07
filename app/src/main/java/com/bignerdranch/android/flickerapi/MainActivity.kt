package com.bignerdranch.android.flickerapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bignerdranch.android.flickerapi.authentication.AuthenticationFragment
import com.bignerdranch.android.flickerapi.fragment.FavoriteFragment
import com.bignerdranch.android.flickerapi.fragment.MapsFragment
import com.bignerdranch.android.flickerapi.fragment.PhotoGalleryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val photoGalleryFragment = PhotoGalleryFragment()
        val favoriteFragment = FavoriteFragment()
        val mapFragment = MapsFragment()
        val uploadFragment = AuthenticationFragment()
        makeCurrentFragment(photoGalleryFragment)
        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.map -> makeCurrentFragment(mapFragment)
                R.id.home -> makeCurrentFragment(photoGalleryFragment)
                R.id.like -> makeCurrentFragment(favoriteFragment)
                R.id.upload -> makeCurrentFragment(uploadFragment)
            }
            true
        }
    }

    fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.warpper, fragment)
                .commit()
        }
}