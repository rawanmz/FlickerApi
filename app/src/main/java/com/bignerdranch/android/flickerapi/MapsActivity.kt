package com.bignerdranch.android.flickerapi


import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.bignerdranch.android.flickerapi.viewmodel.PhotoGalleryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity() {
    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        bottomNavigation=findViewById(R.id.mapNav) as BottomNavigationView

        val photoGalleryFragment=PhotoGalleryFragment()
        val favoriteFragment=FavoriteFragment()
        val mapFragment=FavoriteFragment()
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.pickLocationMode -> makeCurrentFragment(mapFragment)
                R.id.home -> makeCurrentFragment(photoGalleryFragment)
                R.id.like->makeCurrentFragment(favoriteFragment)
            }
            true
        }
    }
    fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Map_warp,fragment)
                .commit()
        }
}
