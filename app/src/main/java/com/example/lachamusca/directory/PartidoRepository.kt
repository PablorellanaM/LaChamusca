package com.example.lachamusca.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint


// Modelo de datos actualizado
data class Partido(
    val nombre: String = "",
    val ubicacion: GeoPoint? = null,
    val descripcion: String = "",
    val fecha: String = "",
    val posicionesNecesarias: List<String> = emptyList(), // Posiciones disponibles en el equipo
    val posicionesOcupadas: Map<String, String> = emptyMap() // Posiciones ocupadas (posición: jugador)
)

class PartidoRepository(private val db: FirebaseFirestore) {

    // Método para agregar un partido o equipo
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

    // Método para filtrar partidos por posición
    fun obtenerPartidosPorPosicion(
        posicion: String,
        onSuccess: (List<Partido>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("partidos")
            .whereArrayContains("posicionesNecesarias", posicion) // Filtrar por posición
            .get()
            .addOnSuccessListener { result ->
                val partidos = result.documents.mapNotNull { it.toObject(Partido::class.java) }
                onSuccess(partidos)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Método para actualizar las posiciones ocupadas en un equipo
    fun actualizarPosicionesOcupadas(
        equipoId: String,
        nuevasPosiciones: Map<String, String>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("partidos")
            .document(equipoId)
            .update("posicionesOcupadas", nuevasPosiciones)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}

