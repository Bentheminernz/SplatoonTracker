package nz.benlawrence.splatoontracker.data

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import nz.benlawrence.splatoontracker.data.models.coral.SessionResponse

class SessionCache(context: Context) {
  private val gson = Gson()
  private val prefs = context.getSharedPreferences("session_cache", Context.MODE_PRIVATE)

  fun saveSession(session: SessionResponse) {
    try {
      val json = gson.toJson(session)
      prefs.edit {
        putString("cached_session", json)
      }
      Log.d("SessionCache", "Session saved to cache")
    } catch (e: Exception) {
      Log.e("SessionCache", "Error saving session to cache", e)
    }
  }

  fun getSession(): SessionResponse? {
    return try {
      val json = prefs.getString("cached_session", null) ?: return null
      gson.fromJson(json, SessionResponse::class.java).also {
        Log.d("SessionCache", "Session retrieved from cache")
      }
    } catch (e: Exception) {
      Log.e("SessionCache", "Error reading session from cache", e)
      null
    }
  }

  fun clearSession() {
    try {
      prefs.edit {
        remove("cached_session")
      }
      Log.d("SessionCache", "Session cache cleared")
    } catch (e: Exception) {
      Log.e("SessionCache", "Error clearing session cache", e)
    }
  }

  fun hasValidSession(): Boolean {
    return getSession() != null
  }
}


