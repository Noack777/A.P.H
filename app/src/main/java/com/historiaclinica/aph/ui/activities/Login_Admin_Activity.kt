package com.historiaclinica.aph.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.historiaclinica.aph.R
import kotlinx.android.synthetic.main.fragment_login__admin.*

class Login_Admin_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login__admin)

        btnback.setOnClickListener {

            val intent:Intent = Intent (this, AuthActivity::class.java)
            startActivity(intent)
            finish()

        }

        botonloginadmin.setOnClickListener {
            if (nombreUsua.text!!.isNotEmpty() && RContrasenia.text!!.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(nombreUsua.text.toString()
                    ,RContrasenia.text.toString()).addOnCompleteListener {

                    if (it.isSuccessful){
                        regbien(it.result?.user?.email ?: "",ProviderType.BASIC)
                    }else{
                        alerta()
                    }
                }

            }
        }
    }



    private fun alerta(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al crear la cuenta")
        builder.setPositiveButton("aceptar",null)
        val dialog : AlertDialog =builder.create()
        dialog.show()

    }

    private fun regbien(email:String,provider:ProviderType){
        val InicioActivityIntent = Intent(this,InicioActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provaider",provider.name)
        }
        startActivity(InicioActivityIntent)
    }

}

