package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    var db = Firebase.firestore
    var db2 = FirebaseApp.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //subir("prueba2@gmail.com","Juan","guia")
        //mostrar("prueba2@gmail.com")
        mandar()
    }

    fun subir(email:String,nombre:String,tipo:String){

        db.collection("users").document(email)
            .set(
                hashMapOf(
                    "nombre" to nombre,
                    "tipo" to tipo,
                    "rutas" to hashMapOf("id" to 1,
                    "Incio de ruta" to "Calle el olivar nº1")
                )
            ).addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID:")
            }
            .addOnFailureListener { e ->
                Log.w("ERROR", "Error adding document", e)
            }
    }
    fun mostrar(email:String){
        val docRef = db.collection("users").document(email)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Datos", "Email del Usuario: ${document.get("rutas.Incio de ruta")}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
    fun mandar(){
        var email: Intent = Intent(Intent.ACTION_SENDTO)
        email.putExtra(Intent.EXTRA_EMAIL,"danielobrero@iesflorenciopintado.es")
        email.putExtra(Intent.EXTRA_SUBJECT,"Recupera Tu clave")
        email.setData(Uri.parse("malito:robalotodo@gmail.com"))
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        email.putExtra(Intent.EXTRA_TEXT, 1234)

        email.setType("message/rfc822")

        startActivity(Intent.createChooser(email,"Recuperación de Clave"))
    }
    fun recu(){
        
    }
}