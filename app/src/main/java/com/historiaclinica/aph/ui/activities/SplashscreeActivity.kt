package com.historiaclinica.aph.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.historiaclinica.aph.R

class SplashscreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscree)

        val animation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        ivlogo_aph.starAnimation(desplazamiento_arriba)



    }



}