package com.bignerdranch.android.flickerapi


import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.flickerapi.R
import com.bignerdranch.android.flickerapi.api.GalleryItem
import com.bignerdranch.android.flickerapi.repository.FlickrFetchr
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.buttomNav
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var requestNumber = 1
    private var isLocateMode= false
    private lateinit var photoViewModel: PhotoGalleryViewModel

    private lateinit var cLocation: Location
//    private lateinit var floatingActionButton: FloatingActionButton
//    private lateinit var pickLocationButton:Button
      private lateinit var pickLocationTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val photoGalleryFragment=PhotoGalleryFragment()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        photoViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)
//        floatingActionButton = findViewById(R.id.floatingButton)
//        pickLocationButton = findViewById(R.id.button)
  pickLocationTextView = findViewById(R.id.textView)
        mapNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.pickLocationMode -> pickLocationMode()
                R.id.home -> openPhotoGalleryFragment(photoGalleryFragment)
                R.id.map->currentLocation()
            }
            true
        }
    }



    override fun onStart() {
        super.onStart()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    fun openPhotoGalleryFragment(fragment: PhotoGalleryFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
                .commit()
        }
    }
         fun pickLocationMode() {
            pickLocationTextView.setText("Noraml mode")
                isLocateMode = !isLocateMode
                if (isLocateMode) {
                    pickLocationTextView.setText("Pick Location mode")
                } else {
                    pickLocationTextView.setText("Noraml mode")
                }
            }

    fun currentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), requestNumber)
        } else {
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null) {
                    cLocation = location
                    Toast.makeText(applicationContext, location.latitude.toString() + " " + location.longitude.toString(), Toast.LENGTH_SHORT).show()
      loadThumbnails(LatLng(cLocation.latitude,cLocation.longitude))

                }
            }
        }
    }//currentLocation
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            requestNumber -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                currentLocation()
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.d("fff","onMapReady")

        mMap.setOnMapClickListener(object:GoogleMap.OnMapClickListener{
            override fun onMapClick(p0: LatLng?) {
                Log.d("fff" , "MapClicked");
if (isLocateMode) {
    loadThumbnails(p0!!);
}            }}
        )
        mMap.setOnMarkerClickListener(object:GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                val id:String=markers.filterValues { it==p0 }.keys.toList()[0]
                val item :GalleryItem= items[id]!!
//add listener to images
                return true
            }
        } )
    }

    var markers = mutableMapOf<String,Marker>();
    var items = mutableMapOf<String,GalleryItem>();

    fun loadThumbnails(searchLoaction:LatLng) {
        Log.d("fff" , "loadThumbnails");
         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchLoaction, 14f))

        photoViewModel.fetchPhoto(searchLoaction.latitude.toString(), searchLoaction.longitude.toString())
                .observe(this, Observer {
                    it.forEach {
                        var url = it.url

                        Glide.with(this)
                                .load(url).into(object : CustomTarget<Drawable>() {
                                    override fun onLoadCleared(placeholder: Drawable?) {
                                        TODO("Not yet implemented")
                                    }
                                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                        // Add a marker in Sydney, Australia,
                                        // and move the map's camera to the same location.
                                        val location = LatLng(it.latitude, it.longitude)
                                        var marker = MarkerOptions()
                                                .position(location)
                                                .icon(BitmapDescriptorFactory.fromBitmap(resource.toBitmap()))
                                                 markers[it.id]= mMap.addMarker(marker)
                                                 items[it.id] = it;
                                                 Log.d("fff" , "markerAdded"+ it.id);
                                    }
                                });
                    }
                })
    }
}
