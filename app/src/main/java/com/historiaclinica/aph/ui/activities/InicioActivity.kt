package com.historiaclinica.aph.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.activity_auth.*

enum class ProviderType {
    BASIC
}

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inicio)

    }
}