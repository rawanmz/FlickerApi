package com.bignerdranch.android.flickerapi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.android.flickerapi.R
import com.bignerdranch.android.flickerapi.data.GalleryItem
import com.bignerdranch.android.flickerapi.viewmodel.PhotoGalleryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class FavoriteFragment : Fragment() {
    private lateinit var delete: ImageView
    private lateinit var pager: ViewPager2
    var photo = emptyList<GalleryItem>()
    private var adapter: FavoritePhotoAdapter = FavoritePhotoAdapter(photo)
    private val photoViewModel: PhotoGalleryViewModel by lazy {
        ViewModelProviders.of(this).get(PhotoGalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        pager = view.findViewById(R.id.pager)
        photoViewModel.photos.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        pager.adapter = adapter
        return view
    }

    inner class FavoritePhotoAdapter(var photoList: List<GalleryItem>) :
        RecyclerView.Adapter<FavoritePhototHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhototHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.favorite_image_list_item, parent, false)
            delete = view.findViewById(R.id.delete)

            return FavoritePhototHolder(view)
        }

        override fun onBindViewHolder(holder: FavoritePhototHolder, position: Int) {
            val listItem = photoList[position]
            holder.apply {
                bind(listItem)
                delete.setOnClickListener {
                    photoViewModel.deletePhoto(listItem)
                }
            }
        }

        override fun getItemCount(): Int {
            return photoList.size
        }
        fun setData(photo: List<GalleryItem>) {
            this.photoList = photo
            notifyDataSetChanged()
        }
    }
    inner class FavoritePhototHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var titleTxt = itemView.findViewById(R.id.titleTxt) as TextView
        lateinit var photo: GalleryItem
        val image = itemView.findViewById(R.id.imageview) as ImageView
        fun bind(photo: GalleryItem) {
            this.photo = photo
            titleTxt.setText(photo.title).toString()
            Glide.with(itemView)
                .load(photo.url)
                .centerCrop()
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(image)

        }
    }
}