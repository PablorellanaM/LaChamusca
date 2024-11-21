package com.example.lachamusca.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

// Clase de datos para Equipo
data class Equipo(
    val id: String = "", // ID único del equipo
    val nombre: String = "", // Nombre del equipo
    val miembros: List<String> = emptyList(), // Lista de IDs de usuarios que pertenecen al equipo
    val posicionSolicitada: String = "" // Posición solicitada por el equipo
)


// Repositorio para manejar equipos
class EquipoRepository(private val db: FirebaseFirestore) {

    // Método para obtener todos los equipos de Firestore
    fun obtenerTodosLosEquipos(
        onSuccess: (List<Equipo>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("equipos")
            .get()
            .addOnSuccessListener { result ->
                try {
                    // Filtrar equipos con menos de 10 miembros
                    val equipos = result.documents.mapNotNull { doc ->
                        val equipo = doc.toObject<Equipo>()
                        equipo?.copy(id = doc.id) // Asignar el ID del documento a `id`
                    }.filter { it.miembros.size < 10 }

                    onSuccess(equipos)
                } catch (e: Exception) {
                    onFailure(e)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Método para agregar un equipo a Firestore
    fun agregarEquipo(
        equipo: Equipo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("equipos")
            .add(equipo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Método para unirse a un equipo
    fun unirseAEquipo(
        equipoId: String,
        usuarioId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val equipoRef = db.collection("equipos").document(equipoId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(equipoRef)
            val equipo = snapshot.toObject<Equipo>()

            if (equipo != null && equipo.miembros.size < 10) {
                val nuevosMiembros = equipo.miembros + usuarioId
                transaction.update(equipoRef, "miembros", nuevosMiembros)
            } else {
                throw Exception("El equipo está lleno o no existe")
            }
        }.addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }
}
