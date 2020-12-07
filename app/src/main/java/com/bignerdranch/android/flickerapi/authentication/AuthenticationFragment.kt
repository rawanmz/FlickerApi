package com.bignerdranch.android.flickerapi.authentication

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bignerdranch.android.flickerapi.R
import java.io.File
import java.io.FileOutputStream

class AuthenticationFragment : Fragment() {

    lateinit var Img : ImageView
    var fileUri: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_authentication, container, false)
        val uploadBtn = view.findViewById<View>(R.id.btnFlickr) as Button
        uploadBtn.setOnClickListener(mFlickrClickListener)
        val btnPick = view.findViewById<View>(R.id.btnPick) as Button
        btnPick.setOnClickListener(mPickClickListener)
        Img = view.findViewById(R.id.imageView1)
        return view
    }
    var mPickClickListener = View.OnClickListener {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        startActivityForResult(intent, 102)
    }
    var mFlickrClickListener = View.OnClickListener {
        if (fileUri == null) {
            Toast.makeText(context, R.string.uploadPhoto, Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        val intent = Intent(context, FlickrjActivity::class.java
        )
        intent.putExtra("flickImagePath", fileUri!!.absolutePath)
        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK &&requestCode == 102){
            val inputStream = context?.contentResolver?.openInputStream(data!!.data!!)
            val imgbitmap = BitmapFactory.decodeStream(inputStream)
            val file = File(context?.filesDir.toString()+ "/${System.currentTimeMillis()}.png"
            )
            Img.setImageBitmap(imgbitmap)
            val out = FileOutputStream(file)
            imgbitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            fileUri = file
        }
    }
}