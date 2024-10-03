package com.example.lachamusca

import android.content.Context
import android.content.SharedPreferences

// Extensión para obtener las SharedPreferences
fun Context.getSharedPrefs(): SharedPreferences {
    return this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
}

// Guardar el nombre del usuario en SharedPreferences
fun SharedPreferences.saveUserName(userName: String) {
    val editor = this.edit()
    editor.putString("user_name", userName)
    editor.apply()  // Guardamos el nombre
}

// Recuperar el nombre del usuario desde SharedPreferences
fun SharedPreferences.getUserName(): String? {
    return this.getString("user_name", null)
}
