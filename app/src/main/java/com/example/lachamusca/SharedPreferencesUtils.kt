// Paquete principal de la aplicación
package com.example.lachamusca

// Importaciones necesarias para el manejo de SharedPreferences
import android.content.Context
import android.content.SharedPreferences

// Extensión para obtener las SharedPreferences del contexto de la aplicación
fun Context.getSharedPrefs(): SharedPreferences {
    // Devuelve un archivo de preferencias compartidas llamado "user_prefs" en modo privado
    return this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
}

// Función de extensión para guardar el nombre del usuario en las SharedPreferences
fun SharedPreferences.saveUserName(userName: String) {
    // Inicia un editor para realizar cambios en las preferencias
    val editor = this.edit()
    // Guarda el nombre del usuario bajo la clave "user_name"
    editor.putString("user_name", userName)
    // Aplica los cambios de manera asíncrona
    editor.apply()
}

// Función de extensión para recuperar el nombre del usuario desde las SharedPreferences
fun SharedPreferences.getUserName(): String? {
    // Devuelve el valor asociado a la clave "user_name", o null si no existe
    return this.getString("user_name", null)
}
