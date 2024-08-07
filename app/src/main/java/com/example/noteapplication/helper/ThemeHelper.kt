package com.example.noteapplication.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import androidx.transition.Visibility.Mode

class ThemeHelper {

    @SuppressLint("CommitPrefEdits")
    fun setTheme(activity: Activity, theme:Boolean)
    {
        var shared = activity.getSharedPreferences("Preference",MODE_PRIVATE)
        val editor =shared.edit()
        editor.putBoolean("theme",theme)
        editor.commit()
    }

    fun getTheme(activity: Activity) : Boolean
    {
        val shared = activity.getSharedPreferences("Preference", MODE_PRIVATE)
        return shared.getBoolean("theme",false)
    }
}