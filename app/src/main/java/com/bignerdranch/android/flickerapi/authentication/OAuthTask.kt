package com.bignerdranch.android.flickerapi.authentication

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.googlecode.flickrjandroid.Flickr
import com.googlecode.flickrjandroid.auth.Permission

class OAuthTask(private val mContext: Context) :
    AsyncTask<Void?, Int?, String?>() {

    private lateinit var myProgressDialog: ProgressDialog
    override fun onPreExecute() {
        super.onPreExecute()
        myProgressDialog =
            ProgressDialog.show(mContext, "", "Generating the Authorization Request")
        myProgressDialog.setCanceledOnTouchOutside(true)
        myProgressDialog.setCancelable(true)
        myProgressDialog.setOnCancelListener(
            { cancel(true) })
    }
    private fun saveTokenSecrent(tokenSecret: String) {
        val act = mContext as FlickrjActivity
        act.saveOAuthToken(null, null, null, tokenSecret)
    }
    override fun onPostExecute(result: String?) {
        if (myProgressDialog != null) {
            myProgressDialog!!.dismiss()
        }
        if (result != null && !result.startsWith("error")) {
            mContext.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(result)))
        } else {
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show()
        }
    }
    companion object {
        private val OAUTH_CALLBACK_URI =
            Uri.parse(FlickrjActivity.CALLBACK_SCHEME + "://oauth") //$NON-NLS-1$
    }
    override fun doInBackground(vararg p0: Void?): String? {
        return try {
            val f: Flickr? = FlickrHelper.instance?.flickr
            val oauthToken = f?.oAuthInterface
                ?.getRequestToken(OAUTH_CALLBACK_URI.toString())
            oauthToken?.oauthTokenSecret?.let { saveTokenSecrent(it) }
            val oauthUrl = f?.oAuthInterface?.buildAuthenticationUrl(
                Permission.WRITE,
                oauthToken
            )
            oauthUrl.toString()
        } catch (e: Exception) {
            "error:" + e.message
        }
    }
}