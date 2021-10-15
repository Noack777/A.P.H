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
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView
import com.historiaclinica.aph.R
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.fragment_inicio.*
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


enum class ProviderType {
    BASIC
}

//varibles grabar audio
private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class InicioActivity : AppCompatActivity() {


    //variables grabar audio
    private var fileName: String = ""
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null


    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)


    //permiso para dispositivo
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when(requestCode){
            STORAGE_CODE ->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                }

            }
        }

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

        //PARTE LOGICA CREACION PDF
        et_pdf_data = findViewById(R.id.b1_date1)


        finalizar.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ){
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                }else{
                    savePDF()
                }
            }else{
                savePDF()
            }
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
        return !cambio
    }
    private fun cambio_icon1(imageView: LottieAnimationView, animation: Int,cambio1: Boolean) : Boolean {
        if (!cambio1) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
            imageView.setImageResource(R.drawable.icon_stop)
        }
        return !cambio1
    }
    private fun cambio_icon2(imageView: LottieAnimationView, animation: Int,cambio2: Boolean) : Boolean {
        if (!cambio2) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
            restaurar_play(btn_reproducir)
        }
        return !cambio2
    }
    private fun cambio_icon3(imageView: LottieAnimationView, animation: Int,cambio3: Boolean) : Boolean {
        if (!cambio3) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        }
        else {
            restaurar_stop1(btn_detener_repro)
        }
        return !cambio3
    }


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


    //Inicio Proceso de PDF
    //variables guardar pdf
    private lateinit var et_pdf_data : EditText
    private lateinit var btn_generar_Pdf : EditText
    private val STORAGE_CODE = 1001


    private fun savePDF() {
        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyHHdd_HHmmss", Locale.getDefault())
            .format(System.currentTimeMillis())

        val mFilepath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"


        try {

            PdfWriter.getInstance(mDoc, FileOutputStream(mFilepath))
            mDoc.open()

            //Separador
            val separador = mDoc.add(Paragraph("    "))

            //Negrilla con Letra tamaño 12
            val negrilla = Font()
            negrilla.setStyle(Font.BOLD)
            negrilla.setSize(12F)

            //Espacio Horizontal
            val espacio = "                            "

            //Espacio Horizontal 2
            val espacio2 = "                    "

            //Espacio Horizontal 3
            val espacio3 = "              "

            //Letra Normal Tamaño 12
            val letra = Font()
            letra.setColor(BaseColor.BLACK)
            letra.setSize(12F)

            //Titulos con letra tamaño 15
            val Estantit = Font()
            Estantit.setColor(BaseColor.BLACK)
            Estantit.setSize(15F)

            //Titulo con Color Gris y Negrilla
            val fuente = Font()
            fuente.setStyle(Font.BOLD)
            fuente.setColor(BaseColor.GRAY)
            fuente.setSize(20F)

            //Titulo color negro y con negrilla tamaño 20
            val fuente_black = Font()
            fuente_black.setStyle(Font.BOLD)
            fuente_black.setColor(BaseColor.BLACK)
            fuente_black.setSize(20F)


            //Titulo
            val tit = "          UNIVERSIDAD MILITAR NUEVA GRANADA"
            mDoc.add(Paragraph(tit,fuente))

            //Titulo 2
            val tit2 = "                            Facultad de Medicina y Ciencia de la Salud"
            mDoc.add (Paragraph(tit2, Estantit))

            //Titulo3
            val tit3 = "                                                  Tecnologí en Atención Prehospitalaria"
            mDoc.add (Paragraph(tit3, letra))

            //Titulo4
            val tit4 = "                                                           Rotaciones Clínicas 2019-2"
            mDoc.add (Paragraph(tit4, letra))

            //Consecutivo
            val consecutivo = "                                                              HISTORIA CLINICA No."
            val n_auto = n_auto.text.toString().trim()
            mDoc.add (Paragraph(consecutivo + n_auto, negrilla, ))

            //Espacio
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Bloque 1

            val Info_trip = "             Informacion de la tripulación y el Turno"
            val fecha = "Fecha"
            val b1_date1 = b1_date1.text.toString().trim()
            val b1_date2 = b1_date2.text.toString().trim()
            val ambulancia = "Ambulancia"
            mDoc.add(Paragraph(Info_trip, fuente_black))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio + espacio2 + fecha + espacio + ambulancia, negrilla))
            separador
            mDoc.add(Paragraph(espacio + espacio2 + b1_date1 + espacio2 + b1_date2, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Tripulación

            val tripulacion = "                                       Tripulación"
            val conductor = "Conductor"
            val aph = "Aph"
            val estudiante = "Estudiante"
            val otro = "Otro"
            val b1_date3 = b1_date3.text.toString().trim()
            val b1_date4 = b1_date4.text.toString().trim()
            val b1_date5 = b1_date5.text.toString().trim()
            val b1_date6 = b1_date6.text.toString().trim()

            mDoc.add(Paragraph(tripulacion, fuente_black))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + conductor + espacio2 + aph + espacio2 + estudiante + espacio2 + otro, negrilla))
            separador
            mDoc.add(Paragraph(espacio2 + b1_date3 + espacio + b1_date4 + espacio2 + b1_date5 + espacio2 + b1_date6, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Evaluación de la Escena

            val evaluacion = "Evaluación de la Escena"
            val b1_date7 = b1_date7.text.toString().trim()

            mDoc.add(Paragraph(evaluacion, fuente_black))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(b1_date7, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Tiempo de Atencion

            val tiempo = "                             Tiempo de Atención"
            val despacho = "Despacho"
            val en_escena = "En la Escena"
            val centro = "Centro de Atención (Destino)"
            val final_atencion = "Finalización de Atención"
            val b2_date1 = b2_date1.text.toString().trim()
            val b2_date2 = b2_date2.text.toString().trim()
            val b2_date3 = b2_date3.text.toString().trim()
            val b2_date4 = b2_date4.text.toString().trim()

            mDoc.add(Paragraph(tiempo, fuente_black))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio + despacho + espacio + espacio + en_escena, negrilla))
            separador
            mDoc.add(Paragraph(espacio + b2_date1 + espacio + espacio + b2_date2, letra))

            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + centro + espacio + final_atencion, negrilla))
            separador

            mDoc.add(Paragraph(espacio2 + b2_date3 + espacio + espacio2 + espacio2 + b2_date4, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Titulo
            val tit_ = "          UNIVERSIDAD MILITAR NUEVA GRANADA"
            mDoc.add(Paragraph(tit_,fuente))

            //Titulo 2
            val tit_2 = "                            Facultad de Medicina y Ciencia de la Salud"
            mDoc.add (Paragraph(tit_2, Estantit))

            //Titulo3
            val tit_3 = "                                                  Tecnologí en Atención Prehospitalaria"
            mDoc.add (Paragraph(tit_3, letra))

            //Titulo4
            val tit_4 = "                                                           Rotaciones Clínicas 2019-2"
            mDoc.add (Paragraph(tit_4, letra))

            //Espacio
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Datos Personales
            val datos = "                                Datos Personales"
            val p_apellido = "Primer Apellido"
            val s_apellido = "Segundo Apellido"
            val p_nombre = "Nombres"
            val b3_date1 = b3_date1.text.toString().trim()
            val b3_date2 = b3_date2.text.toString().trim()
            val b3_date3 = b3_date3.text.toString().trim()

            val t_documento = "T. Documento"
            val n_documento = "Numero de Documento"
            val eps = "Eps"
            val b3_date4 = b3_date4.text.toString().trim()
            val b3_date5 = b3_date5.text.toString().trim()
            val b3_date6 = b3_date6.text.toString().trim()

            val m_pre = "Medicina Prepagada"
            val genero = "Genero"
            val f_nacimiento = "Fecha de Nacimiento"
            val b3_date7 = b3_date7.text.toString().trim()
            val b3_date8 = b3_date8.text.toString().trim()
            val b3_date9 = b3_date9.text.toString().trim()

            val edad = "Edad"
            val ciudad = "Ciudad"
            val telefono = "Telefono"
            val b3_date10 = b3_date10.text.toString().trim()
            val b3_date11 = b3_date11.text.toString().trim()
            val b3_date12 = b3_date12.text.toString().trim()

            val direccion = "Dirección"
            val f_a = "Familiar/Acompañante"
            val contacto = "Telefono de Constacto"
            val b3_date13 = b3_date13.text.toString().trim()
            val b3_date14 = b3_date14.text.toString().trim()
            val b3_date15 = b3_date15.text.toString().trim()

            mDoc.add(Paragraph(datos, fuente_black))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Primer Apellido, Segundo Apellido, Nombres
            mDoc.add(Paragraph(espacio2 + p_apellido + espacio2 + s_apellido + espacio2 + p_nombre, negrilla))

            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + b3_date1 + espacio + espacio2 + b3_date2 + espacio + b3_date3, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Tipo de Documento, Numero de Documento, Eps
            mDoc.add(Paragraph(espacio2 + t_documento + espacio2 + n_documento + espacio2 + eps, negrilla))

            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + b3_date4 + espacio + espacio3 +  b3_date5 + espacio + b3_date6, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Medicina Prepagada, Genero, Fecha de Nacimiento
            mDoc.add(Paragraph(espacio2 + m_pre + espacio2 + genero + espacio2 + f_nacimiento, negrilla))

            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + b3_date7 + espacio2 + espacio3 +  b3_date8 + espacio + b3_date9, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Edad, ciudad, Telefono
            mDoc.add(Paragraph(espacio2 + edad + espacio + espacio2 + ciudad + espacio2 + espacio3 + telefono, negrilla))

            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + b3_date10 + espacio + espacio2 +  b3_date11 + espacio2 + espacio3 + b3_date12, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Direccion, Familiar/Acompañante, Contacto
            mDoc.add(Paragraph(espacio2 + direccion + espacio2 + f_a + espacio2 + contacto, negrilla))

            mDoc.add(Paragraph("   "))

            mDoc.add(Paragraph(espacio2 + b3_date13 + espacio2 + espacio3 +  b3_date14 + espacio + espacio3 + b3_date15, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Motivo de la Consulta

            val m_consulta = "Motivo de la Consulta"
            val b4_date1 = b4_date1.text.toString().trim()

            mDoc.add(Paragraph(m_consulta, fuente_black))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(b4_date1, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Titulo
            val tit__ = "          UNIVERSIDAD MILITAR NUEVA GRANADA"
            mDoc.add(Paragraph(tit__,fuente))

            //Titulo 2
            val tit__2 = "                            Facultad de Medicina y Ciencia de la Salud"
            mDoc.add (Paragraph(tit__2, Estantit))

            //Titulo3
            val tit__3 = "                                                  Tecnologí en Atención Prehospitalaria"
            mDoc.add (Paragraph(tit__3, letra))

            //Titulo4
            val tit__4 = "                                                           Rotaciones Clínicas 2019-2"
            mDoc.add (Paragraph(tit__4, letra))

            //Espacio
            mDoc.add(Paragraph("   "))

            //Enfermedad Actual

            val enfermedad = "Enfermedad Actual"
            val b4_date2 = b4_date2.text.toString().trim()

            mDoc.add(Paragraph(enfermedad, fuente_black))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(b4_date2, letra))

            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph("   "))

            //Revision por Sistemas

            val r_sistemas = "Revisión Por Sistemas"
            val b4_date3 = b4_date3.text.toString().trim()
            val b4_date4 = b4_date4.text.toString().trim()
            val b4_date5 = b4_date5.text.toString().trim()

            mDoc.add(Paragraph(r_sistemas, fuente_black))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(b4_date3, letra))
            mDoc.add(Paragraph(b4_date4, letra))
            mDoc.add(Paragraph(b4_date5, letra))

            mDoc.add(Paragraph("   "))

            //Antecedentes

            val antecedentes = "Antecedentes:"
            val patologico = "Patologico:"
            val quirurgico = "Quirurgico:"
            val alergico = "Alergico:"
            val farmacologico = "Farmacologico:"
            val toxico = "Toxico:"
            val host = "Hospitalizaciones:"
            val ocupacional = "Ocupacional:"
            val familiares = "Familiares"
            val gineco = "Gineco"
            val trauma = "Trauma:"
            val otros = "Otro:"

            val b4_date6 = b4_date6.text.toString().trim()
            val b4_date7 = b4_date7.text.toString().trim()
            val b4_date8 = b4_date8.text.toString().trim()
            val b4_date9 = b4_date9.text.toString().trim()
            val b4_date10 = b4_date10.text.toString().trim()
            val b4_date11 = b4_date11.text.toString().trim()
            val b4_date12 = b4_date12.text.toString().trim()
            val b4_date13 = b4_date13.text.toString().trim()
            val b4_date14 = b4_date14.text.toString().trim()
            val b4_date15 = b4_date15.text.toString().trim()
            val b4_date16 = b4_date16.text.toString().trim()

            mDoc.add(Paragraph(antecedentes, fuente_black))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(patologico + b4_date6, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(quirurgico + b4_date7, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(alergico + b4_date8, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(farmacologico + b4_date9, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(toxico + b4_date10, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(host + b4_date11, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(ocupacional + b4_date12, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(familiares + b4_date13, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(gineco + b4_date14, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(trauma + b4_date15, letra))
            mDoc.add(Paragraph("   "))
            mDoc.add(Paragraph(otros + b4_date16, letra))



            mDoc.addAuthor("Tecno-Design")
            mDoc.close()
            Toast.makeText(this, "$mFileName.pdf\n es creado  en \n $mFilepath", Toast.LENGTH_SHORT).show()

        }catch (e:Exception){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show()
        } catch(i:IOException )
        {
            Toast.makeText(this, ""+i.toString(), Toast.LENGTH_SHORT).show()
        }
    }




}
