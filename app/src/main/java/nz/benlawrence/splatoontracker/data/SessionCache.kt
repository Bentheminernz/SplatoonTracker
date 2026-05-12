package nz.benlawrence.splatoontracker.data

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import nz.benlawrence.splatoontracker.utils.debugOnly

// TODO: Add encryption for stored session data
val Context.userPreferencesStore: DataStore<Preferences> by preferencesDataStore(name = "session_store")

class SessionCache(private val context: Context) {
  private val SESSION_BLOB_KEY = stringPreferencesKey("session_blob")
  private val KEYSET_NAME = "session_cache_keyset"
  private val PREF_FILE_NAME = "session_cache_tink"
  private val MASTER_KEY_URI = "android-keystore://session_cache_master_key"

  init {
    AeadConfig.register()
  }

  private val aead: Aead by lazy {
    AndroidKeysetManager.Builder()
      .withSharedPref(context, KEYSET_NAME, PREF_FILE_NAME)
      .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
      .withMasterKeyUri(MASTER_KEY_URI)
      .build()
      .keysetHandle
      .getPrimitive(Aead::class.java)
  }

  private fun encrypt(value: String): String {
    val encrypted = aead.encrypt(value.toByteArray(), null)
    return Base64.encodeToString(encrypted, Base64.DEFAULT)
  }

  private fun decrypt(value: String): String {
    val decoded = Base64.decode(value, Base64.DEFAULT)
    return String(aead.decrypt(decoded, null))
  }

  suspend fun saveSessionBlob(blob: String) {
    try {
      val encrypted = encrypt(blob)
      context.userPreferencesStore.edit { it[SESSION_BLOB_KEY] = encrypted }
      debugOnly { Log.d("SessionCache", "Session blob saved") }
    } catch (e: Exception) {
      debugOnly { Log.e("SessionCache", "Error saving session blob", e) }
    }
  }

  suspend fun getSessionBlob(): String? {
    return try {
      context.userPreferencesStore.data
        .map { it[SESSION_BLOB_KEY] }
        .firstOrNull()
        ?.let { decrypt(it) }
        .also { debugOnly { Log.d("SessionCache", "Session blob retrieved") } }
    } catch (e: Exception) {
      debugOnly { Log.e("SessionCache", "Error reading session blob", e) }
      null
    }
  }

  suspend fun clearSession() {
    try {
      context.userPreferencesStore.edit { it.remove(SESSION_BLOB_KEY) }
      debugOnly { Log.d("SessionCache", "Session cache cleared") }
    } catch (e: Exception) {
      debugOnly { Log.e("SessionCache", "Error clearing session cache", e) }
    }
  }

  suspend fun hasValidSession(): Boolean = getSessionBlob() != null
}