package com.bignerdranch.android.flickerapi.fragment

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.flickerapi.R
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var isLocateMode = false
    private lateinit var photoViewModel: PhotoGalleryViewModel
    private lateinit var pickLocationTextView: TextView
    private lateinit var floatingActionButton: FloatingActionButton
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        googleMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(geo: LatLng?) {
                if (isLocateMode) {
                    loadThumbnails(geo!!)
                }
            }
        }
        )
        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                val id: String = markers.filterValues { it == marker }.keys.toList()[0]
                val item: GalleryItem = items[id]!!
                return true
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        photoViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        pickLocationTextView = view.findViewById(R.id.textView)
        floatingActionButton.setOnClickListener {
            pickLocationMode()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    fun pickLocationMode() {
        pickLocationTextView.text = "Normal mode"
        isLocateMode = !isLocateMode
        if (isLocateMode) {
            pickLocationTextView.text = "Pick Location mode"
        } else {
            pickLocationTextView.text = "Normal mode"
        }
    }

    var markers = mutableMapOf<String, Marker>()
    var items = mutableMapOf<String, GalleryItem>()
    fun loadThumbnails(searchLoaction: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchLoaction, 14f))
        photoViewModel.fetchPhoto(
            searchLoaction.latitude.toString(),
            searchLoaction.longitude.toString()
        )
            .observe(this, Observer {
                it.forEach {
                    val url = it.url
                    Glide.with(this)
                        .load(url).into(object : CustomTarget<Drawable>() {
                            override fun onLoadCleared(placeholder: Drawable?) {
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                val location = LatLng(it.latitude, it.longitude)
                                val marker = MarkerOptions()
                                    .position(location)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resource.toBitmap()))
                                markers[it.id] = mMap.addMarker(marker)
                                items[it.id] = it
                            }
                        })
                }
            })
    }
}