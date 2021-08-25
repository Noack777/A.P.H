package com.historiaclinica.aph.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.activity_splashscree.*


class SplashscreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscree)


        val animation = AnimationUtils.loadAnimation(this, R.anim.animacion)
        val animation1 = AnimationUtils.loadAnimation(this, R.anim.animacion_abajo)
        logo1.startAnimation(animation)
        logotecno.startAnimation(animation1)
        nombretecno.startAnimation(animation1)

        val intent = Intent(this, MainActivity::class.java)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {

                startActivity(intent)
                finish()

            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })



    }



}