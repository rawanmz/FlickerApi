package com.bignerdranch.android.flickerapi.authentication

import android.app.ProgressDialog
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.googlecode.flickrjandroid.Flickr
import com.googlecode.flickrjandroid.oauth.OAuth
import com.googlecode.flickrjandroid.uploader.UploadMetaData
import java.io.File
import java.io.FileInputStream

class UploadPhotoTask(
    private val flickerActivity: FlickrjActivity,
    private val file: File, ) : AsyncTask<OAuth?, Void?, String?>() {

    private lateinit var myProgressDialog: ProgressDialog
    override fun onPreExecute() {
        super.onPreExecute()
        myProgressDialog = ProgressDialog.show(
            flickerActivity,
            "", "Uploading..."
        )
        myProgressDialog.setCanceledOnTouchOutside(true)
        myProgressDialog.setCancelable(true)
        myProgressDialog.setOnCancelListener(
            { cancel(true) })
    }

    override fun onPostExecute(response: String?) {
        if (myProgressDialog != null) {
            myProgressDialog!!.dismiss()
        }
        if (response != null) {
            Log.e("rawan", "" + response)
        } else {
        }
        if (upload != null) {
            upload!!.onComplete()
        }
        Toast.makeText(
            flickerActivity.applicationContext,
            " Uploaded Successfully", Toast.LENGTH_SHORT
        ).show()
    }

    var upload: onUploadDone? = null
    fun setOnUploadDone(monUploadDone: onUploadDone?) {
        this.upload = monUploadDone
    }

    interface onUploadDone {
        fun onComplete()
    }

    override fun doInBackground(vararg params: OAuth?): String? {
        val oauth = params[0]
        val token = oauth?.token
        try {
            val f: Flickr? = FlickrHelper.instance?.getFlickrAuthed(
                token?.oauthToken, token?.oauthTokenSecret
            )
            val uploadMetaData = UploadMetaData()
            uploadMetaData.title = "title" + file.name
            return f?.uploader?.upload(
                file.name,
                FileInputStream(file), uploadMetaData
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}