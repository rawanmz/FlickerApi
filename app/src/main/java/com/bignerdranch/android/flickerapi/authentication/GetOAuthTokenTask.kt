package com.bignerdranch.android.flickerapi.authentication

import android.os.AsyncTask
import com.googlecode.flickrjandroid.Flickr
import com.googlecode.flickrjandroid.oauth.OAuth

class GetOAuthTokenTask(private val activity: FlickrjActivity?) :
    AsyncTask<String?, Int?, OAuth?>() {
    override fun onPostExecute(result: OAuth?) {
        activity?.onOAuthDone(result)
    }
    override fun doInBackground(vararg params: String?): OAuth? {
        val oauthToken = params[0]
        val oauthTokenSecret = params[1]
        val verifier = params[2]
        val f: Flickr? = FlickrHelper.instance?.flickr
        var oauthApi = f?.oAuthInterface
        if (f != null) {
            oauthApi = f.oAuthInterface
            return try {
                oauthApi.getAccessToken(oauthToken, oauthTokenSecret, verifier)
            } catch (e: Exception) {
                null
            }
        }
        return null
    }
}