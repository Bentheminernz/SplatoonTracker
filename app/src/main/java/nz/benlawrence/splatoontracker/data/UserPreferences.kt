package nz.benlawrence.splatoontracker.data

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesViewModel(application: Application): AndroidViewModel(application) {
  private val dataStore = getApplication<Application>().dataStore

  companion object {
    val HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val REGULAR_BATTLE_NOTIFICATIONS = booleanPreferencesKey("regular_battle_notifications")
  }

  val hasSeenOnBoarding = dataStore.data
    .map { it[HAS_SEEN_ONBOARDING] ?: false }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  val notificationsEnabled = dataStore.data
    .map { it[NOTIFICATIONS_ENABLED] ?: false }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  val regularBattleNotificationsEnabled = dataStore.data
    .map { it[REGULAR_BATTLE_NOTIFICATIONS] ?: false }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  fun setHasSeenOnBoarding(value: Boolean) {
    viewModelScope.launch { dataStore.edit { it[HAS_SEEN_ONBOARDING] = value } }
  }

  fun setNotificationsEnabled(value: Boolean) {
    viewModelScope.launch { dataStore.edit { it[NOTIFICATIONS_ENABLED] = value } }
  }

  fun setRegularBattleNotificationsEnabled(value: Boolean) {
    viewModelScope.launch { dataStore.edit { it[REGULAR_BATTLE_NOTIFICATIONS] = value } }
  }

  fun setDefaultNotificationSettings() {
    setRegularBattleNotificationsEnabled(true)
  }
}