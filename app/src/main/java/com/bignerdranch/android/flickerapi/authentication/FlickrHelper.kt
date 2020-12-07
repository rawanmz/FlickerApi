package com.bignerdranch.android.flickerapi.authentication

import com.googlecode.flickrjandroid.Flickr
import com.googlecode.flickrjandroid.REST
import com.googlecode.flickrjandroid.RequestContext
import com.googlecode.flickrjandroid.interestingness.InterestingnessInterface
import com.googlecode.flickrjandroid.oauth.OAuth
import com.googlecode.flickrjandroid.oauth.OAuthToken
import com.googlecode.flickrjandroid.photos.PhotosInterface
import javax.xml.parsers.ParserConfigurationException

const val API_KEY = "3c51a83973ab1d283946d0ea8e62cd7a"
const val API_SEC = "62641895549248e6"
class FlickrHelper private constructor() {
    val flickr: Flickr?
        get() = try {
            Flickr(API_KEY, API_SEC, REST())
        } catch (e: ParserConfigurationException) {
            null
        }
    fun getFlickrAuthed(token: String?, secret: String?): Flickr? {
        val f = flickr
        val requestContext =
            RequestContext.getRequestContext()
        val auth = OAuth()
        auth.token = OAuthToken(token, secret)
        requestContext.oAuth = auth
        return f
    }
    companion object {
        var instance: FlickrHelper? = null
            get() {
                if (field == null) {
                    field = FlickrHelper()
                }
                return field
            }
            private set
    }
}