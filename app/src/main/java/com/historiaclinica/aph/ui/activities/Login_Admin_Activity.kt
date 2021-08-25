package com.historiaclinica.aph.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.fragment_login__admin.*

class Login_Admin_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login__admin)

        btnback.setOnClickListener {

            val intent:Intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}