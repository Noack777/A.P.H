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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.fragment_inicio)

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        //logica boton grabar
        btn_grabar.setOnClickListener {
            mostrarmensaje()
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
            mostrarmensaje1()
            recorder?.apply {
                stop()
                release()
            }
            recorder = null
        }

        //logica boton reproducir
        btn_reproducir.setOnClickListener {
            mostrarmensaje2()
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


    }

    //Acciones del boton de grabar al ser pulsado

    private fun mostrarmensaje() {
        Toast.makeText(this, "Grabacioón Iniciada", Toast.LENGTH_LONG).show()
    }

    //Acciones del boton de Detener Grabacion al ser pulsado

    private fun mostrarmensaje1() {
        Toast.makeText(this, "Grabacioón Detenida", Toast.LENGTH_LONG).show()
    }

    //Acciones del boton de Reproducir Grabacion al ser pulsado

    private fun mostrarmensaje2() {
        Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_LONG).show()
    }

    //Acciones del boton de Pausar Grabacion al ser pulsado

    private fun mostrarmensaje3() {
        Toast.makeText(this, "Grabación Pausada", Toast.LENGTH_LONG).show()
    }

}
