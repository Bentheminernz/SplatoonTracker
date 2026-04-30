package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.UserPreferencesViewModel

@Composable
fun Settings(
  viewModel: UserPreferencesViewModel,
  onRequestNotificationPermission: () -> Unit,
  navController: NavController
) {
  val notificationsEnabled by viewModel.notificationsEnabled.collectAsStateWithLifecycle()
  val regularBattleNotisEnabled by viewModel.regularBattleNotificationsEnabled.collectAsStateWithLifecycle()

  Column(
    modifier = Modifier
      .padding(horizontal = 16.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Enable Notifications")
      Switch(
        checked = notificationsEnabled,
        onCheckedChange = { enabled ->
          if (enabled) {
            onRequestNotificationPermission()
          } else {
            viewModel.setNotificationsEnabled(false)
          }
        }
      )
    }

    if (notificationsEnabled) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("Enable Regular Battle Notifications")
        Switch(
          checked = regularBattleNotisEnabled,
          onCheckedChange = { viewModel.setRegularBattleNotificationsEnabled(it) }
        )
      }
    }
  }
}