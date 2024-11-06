package com.example.lachamusca.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

data class Partido(
    val nombre: String = "",
    val ubicacion: GeoPoint? = null,
    val descripcion: String = "",
    val fecha: String = ""
)

class PartidoRepository(private val db: FirebaseFirestore) {

    fun agregarPartido(partido: Partido, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("partidos")
            .add(partido)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
