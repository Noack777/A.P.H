package com.historiaclinica.aph.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.activity_terminos_condiciones.*

class terminos_condiciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terminos_condiciones)

        acepta_terminos.setOnClickListener {

            val intent: Intent = Intent (this, AuthActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

    }
}