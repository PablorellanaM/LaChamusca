package com.example.lachamusca.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.example.lachamusca.repository.Partido


class PartidoRepository(private val db: FirebaseFirestore) {

    fun obtenerTodosLosPartidos(
        onSuccess: (List<Partido>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("partidos")
            .get()
            .addOnSuccessListener { result ->
                val partidos = result.documents.mapNotNull { document ->
                    try {
                        document.toObject<Partido>()
                    } catch (e: Exception) {
                        null
                    }
                }
                onSuccess(partidos)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun agregarPartido(
        partido: Partido,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
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
