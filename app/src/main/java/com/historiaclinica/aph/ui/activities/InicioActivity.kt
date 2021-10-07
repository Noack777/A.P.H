/*
version 3.1.1
@uthor  TecnoDesingn
Date   07/10/2021
BACKENT
 */


package com.historiaclinica.aph.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.fragment_inicio.*
import java.io.IOException

enum class ProviderType {
    BASIC
}

private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.fragment_inicio)
    }

    //variables grabar audio
    private var fileName: String = ""
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    //permiso para dispositivo
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.fragment_inicio)

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        //logica boton grabar

        var cambio = false
        var cambio1 = false
        var cambio2 = false
        var cambio3 = false

        btn_grabar.setOnClickListener {
            mostrarmensaje()
            restaurar_play(btn_reproducir)
            restaurar_stop(btn_stop)
            restaurar_stop1(btn_detener_repro)
            cambio = cambio_icon(btn_grabar, R.raw.animatio_icon_grabar2, cambio)
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(fileName)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e(LOG_TAG, "prepare() failed")
                }
                start()
           }
        }

        //logica boton detener grabar
        btn_stop.setOnClickListener {
           restaurar_play(btn_reproducir)
            restaurar_Grabar(btn_grabar)
            restaurar_stop1(btn_detener_repro)
            mostrarmensaje1()
            cambio1 = cambio_icon1(btn_stop, R.raw.animation_stop1, cambio1)
            recorder?.apply {
                stop()
                release()
            }
            recorder = null
        }

        //logica boton reproducir
        btn_reproducir.setOnClickListener {
            mostrarmensaje2()
            restaurar_play(btn_reproducir)
            restaurar_Grabar(btn_grabar)
            restaurar_stop1(btn_detener_repro)
            restaurar_stop(btn_stop)
            cambio2 = cambio_icon(btn_reproducir, R.raw.animation_play, cambio2)
            player = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    start()
                } catch (e: IOException) {
                    Log.e(LOG_TAG, "prepare() failed")
                }
            }
        }

        //logica boton de quitar
        btn_detener_repro.setOnClickListener {
            player?.release()
            player = null
            mostrarmensaje3()
            restaurar_Grabar(btn_grabar)
            restaurar_stop(btn_stop)
            restaurar_play(btn_reproducir)
            cambio3 = cambio_icon(btn_detener_repro, R.raw.animation_stop, cambio3)
        }

    }

    //Logica para cambiar de icono al presionar el boton

    private fun cambio_icon(imageView: LottieAnimationView, animation: Int,cambio: Boolean) : Boolean {
        if (!cambio) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
            imageView.setImageResource(R.drawable.icon_grabar)
        }
        return !cambio }

    private fun cambio_icon1(imageView: LottieAnimationView, animation: Int,cambio1: Boolean) : Boolean {
        if (!cambio1) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
            imageView.setImageResource(R.drawable.icon_stop)
        }
        return !cambio1 }

    private fun cambio_icon2(imageView: LottieAnimationView, animation: Int,cambio2: Boolean) : Boolean {
        if (!cambio2) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
           restaurar_play(btn_reproducir)
        }
        return !cambio2 }

    private fun cambio_icon3(imageView: LottieAnimationView, animation: Int,cambio3: Boolean) : Boolean {
        if (!cambio3) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
            restaurar_stop1(btn_detener_repro)
        }
        return !cambio3 }

    //logica para mostrar mensajes al presiomar el boton
    private fun mostrarmensaje() {
        Toast.makeText(this, "Grabación Iniciada", Toast.LENGTH_LONG).show()
    }

    private fun mostrarmensaje1() {
        Toast.makeText(this, "Grabación Detenida", Toast.LENGTH_LONG).show()
    }

    private fun mostrarmensaje2() {
        Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_LONG).show()
    }

    private fun mostrarmensaje3() {
        Toast.makeText(this, "Reproducción Finalizada", Toast.LENGTH_LONG).show()
    }

    // funcion  Restaurar
    private fun restaurar_Grabar(imageView: LottieAnimationView) {
        imageView.setImageResource(R.drawable.icon_grabar)
    }
    private fun restaurar_stop(imageView: LottieAnimationView) {
        imageView.setImageResource(R.drawable.icon_stop)
    }
    private fun restaurar_play(imageView: LottieAnimationView) {
        imageView.setImageResource(R.drawable.icon_play)
        }
    private fun restaurar_stop1(imageView: LottieAnimationView) {
        imageView.setImageResource(R.drawable.icon_stop1)
    }

}
