package com.example.lachamusca.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

// Clase de datos para Equipo
data class Equipo(
    val nombre: String = "",
    val descripcion: String = "",
    val ubicacion: String = ""
)

// Repositorio para manejar equipos
class EquipoRepository(private val db: FirebaseFirestore) {

    // Método para obtener todos los equipos de Firestore
    fun obtenerTodosLosEquipos(
        onSuccess: (List<Equipo>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("equipos") // Cambia "equipos" por el nombre de tu colección en Firestore
            .get()
            .addOnSuccessListener { result ->
                try {
                    // Convertir los documentos obtenidos a objetos Equipo
                    val equipos = result.documents.mapNotNull { it.toObject<Equipo>() }
                    onSuccess(equipos)
                } catch (e: Exception) {
                    // Manejar errores de conversión
                    onFailure(e)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta a Firestore
                onFailure(exception)
            }
    }

    // Método para guardar un equipo en Firestore
    fun agregarEquipo(
        equipo: Equipo,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("equipos") // Cambia "equipos" por el nombre de tu colección en Firestore
            .add(equipo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
