package com.bignerdranch.android.flickerapi.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.bignerdranch.android.flickerapi.R
import com.googlecode.flickrjandroid.oauth.OAuth
import com.googlecode.flickrjandroid.oauth.OAuthToken
import com.googlecode.flickrjandroid.people.User
import java.io.File
import java.util.*

class FlickrjActivity : Activity() {
    var path: String? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            if (intent.extras!!.containsKey("flickImagePath")) {
                path = intent.getStringExtra("flickImagePath")
            }
        }
        object : Thread() {
            override fun run() {
                h.post(init)
            }
        }.start()
    }
    var h = Handler()
    var init = Runnable {
        val oauth = oAuthToken
        if (oauth == null || oauth.user == null) {
            val task = OAuthTask(context)
            task.execute()
        } else {
            load(oauth)
        }
    }
    private fun load(oauth: OAuth?) {
        if (oauth != null) {
            val taskUpload = UploadPhotoTask(
                this, File(path)
            )

            taskUpload.setOnUploadDone(object : UploadPhotoTask.onUploadDone {
                override fun onComplete() {
                    setResult(RESULT_OK)
                    finish()

                }
            })
            taskUpload.execute(oauth)
        }
    }
    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
    }

    public override fun onResume() {
        super.onResume()
        val intent = intent
        val scheme = intent.scheme
        val savedToken = oAuthToken
        if (CALLBACK_SCHEME == scheme && (savedToken == null || savedToken.user == null)) {
            val uri = intent.data
            val query = uri!!.query
            val data =
                query!!.split("&".toRegex()).toTypedArray()
            if (data != null && data.size == 2) {
                val oauthToken =
                    data[0].substring(data[0].indexOf("=") + 1)
                val oauthVerifier = data[1]
                    .substring(data[1].indexOf("=") + 1)
                val oauth = oAuthToken
                if (oauth != null && oauth.token != null && oauth.token.oauthTokenSecret != null
                ) {
                    val task = GetOAuthTokenTask(this)
                    task.execute(
                        oauthToken, oauth.token
                            .oauthTokenSecret, oauthVerifier
                    )
                }
            }
        }
    }

    fun onOAuthDone(result: OAuth?) {
        if (result == null) {
            Toast.makeText(this, R.string.Authorization_failed,  //$NON-NLS-1$
                Toast.LENGTH_LONG
            ).show()
        } else {
            val user = result.user
            val token = result.token
            if (user == null || user.id == null || token == null || token.oauthToken == null || token.oauthTokenSecret == null
            ) {
                Toast.makeText(
                    this, R.string.Authorization_failed,  //$NON-NLS-1$
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            val message = String.format(
                Locale.US,
                "Authorization Succeed: user=%s, userId=%s, oauthToken=%s, tokenSecret=%s",  //$NON-NLS-1$
                user.username, user.id,
                token.oauthToken, token.oauthTokenSecret
            )
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            saveOAuthToken(
                user.username, user.id,
                token.oauthToken, token.oauthTokenSecret
            )
            load(result)
        }
    }
    val oAuthToken: OAuth?
        get() {
            val settings = getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            val oauthTokenString =
                settings.getString(KEY_OAUTH_TOKEN, null)
            val tokenSecret =
                settings.getString(KEY_TOKEN_SECRET, null)
            if (oauthTokenString == null && tokenSecret == null) {
                return null
            }
            val oauth = OAuth()
            val userName =
                settings.getString(KEY_USER_NAME, null)
            val userId = settings.getString(KEY_USER_ID, null)
            if (userId != null) {
                val user =
                    User()
                user.username = userName
                user.id = userId
                oauth.user = user
            }
            val oauthToken = OAuthToken()
            oauth.token = oauthToken
            oauthToken.oauthToken = oauthTokenString
            oauthToken.oauthTokenSecret = tokenSecret
            return oauth
        }

    fun saveOAuthToken(
        userName: String?, userId: String?, token: String?,
        tokenSecret: String?
    ) {
        val sp = getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.putString(KEY_OAUTH_TOKEN, token)
        editor.putString(KEY_TOKEN_SECRET, tokenSecret)
        editor.putString(KEY_USER_NAME, userName)
        editor.putString(KEY_USER_ID, userId)
        editor.commit()
    }

    private val context: Context
        private get() = this

    companion object {
        const val CALLBACK_SCHEME = "flickrj-android-sample-oauth" //$NON-NLS-1$
        const val PREFS_NAME = "flickrj-android-sample-pref" //$NON-NLS-1$
        const val KEY_OAUTH_TOKEN = "flickrj-android-oauthToken" //$NON-NLS-1$
        const val KEY_TOKEN_SECRET = "flickrj-android-tokenSecret" //$NON-NLS-1$
        const val KEY_USER_NAME = "flickrj-android-userName" //$NON-NLS-1$
        const val KEY_USER_ID = "flickrj-android-userId" //$NON-NLS-1$
    }
}