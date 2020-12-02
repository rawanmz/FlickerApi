package com.bignerdranch.android.flickerapi

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.flickerapi.api.GalleryItem
import com.bignerdranch.android.flickerapi.viewmodel.PhotoGalleryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.log

class PhotoGalleryFragment : Fragment() {
    private lateinit var photoViewModel:PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView
    private  var photo= emptyList<GalleryItem>()
    private var adapter = ImageAdapter(photo)
    private lateinit var currentlocation : FusedLocationProviderClient
    private var lat:Double=0.0
    private var lon:Double=0.0
    private var requestNumber = 1
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoViewModel=ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
         currentlocation = LocationServices.getFusedLocationProviderClient(requireContext())
           //git Current location
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            currentlocation.lastLocation.addOnCompleteListener {task ->
                var location = task.getResult()
                if(location != null){
                    var geocoder = Geocoder(requireContext(), Locale.getDefault())
                    var address = geocoder.getFromLocation(
                        location.latitude,location.longitude,1
                    )
                    lat = address[0].latitude
                    lon = address[0].longitude
                    photoViewModel.fetchPhoto(lat.toString(), lon.toString()).observe(viewLifecycleOwner, Observer {
                        adapter.setData(it)
                    })
                }else{
                    ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                requestNumber
            )
                }
                }
            }
        photoRecyclerView.adapter = adapter

        return view
    }

    private inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var image:GalleryItem
        var photoView:ImageView=view.findViewById(R.id.item_image_view)
        fun bind(photo:GalleryItem){
           this.image=photo
            Log.d("fff",photo.url)
            Glide.with(itemView)
                .load(photo.url)
                .centerCrop()
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(photoView)
        }
    }
    private inner class ImageAdapter(var photo:List<GalleryItem>) : RecyclerView.Adapter<ImageHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
            var view=layoutInflater.inflate(R.layout.image_list_item,null,false)
            return ImageHolder(view)
        }
        override fun onBindViewHolder(holder: ImageHolder, position: Int) {
           var photo=this.photo[position]
            holder.bind(photo)

        }

        override fun getItemCount(): Int {
return photo.size
        }
fun setData(photo :List<GalleryItem>){
    this.photo=photo
    notifyDataSetChanged()
}
    }
}
