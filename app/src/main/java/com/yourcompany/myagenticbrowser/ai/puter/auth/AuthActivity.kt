package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.ai.puter.auth.PuterAuthActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        
        // Redirect immediately to the new PuterAuthActivity
        startAuthentication()
    }


    private fun startAuthentication() {
        val intent = Intent(this, PuterAuthActivity::class.java)
        startActivity(intent)
        finish() // Close this activity and let PuterAuthActivity handle the flow
    }

}