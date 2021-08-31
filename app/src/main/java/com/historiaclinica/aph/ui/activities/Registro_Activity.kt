package com.historiaclinica.aph.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.fragment_registro.*

class Registro_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro)

        btnback2.setOnClickListener {

           val intent: Intent =Intent (this, AuthActivity::class.java )
            startActivity(intent)
            finish()

        }
    }
}