package com.historiaclinica.aph.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Analytics Event
        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        // Setup
        setup()
    }

    private fun setup() {

        //Logica del Boton de ingreso

        botonlogin.setOnClickListener{

            if (codigologueo.text.isNotEmpty() && Contrasenia.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(codigologueo.text.toString(),
                        Contrasenia.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {

                            showinicio(it.result?.user?.email ?: "", ProviderType.BASIC)

                        } else {

                            showAlert()

                        }
                    }
            }

        }

    }

    private fun showAlert (){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showinicio(email: String, provider: ProviderType) {

        val inicioIntent = Intent(this, InicioActivity::class.java).apply {

        }

        startActivity(inicioIntent)

    }
}
