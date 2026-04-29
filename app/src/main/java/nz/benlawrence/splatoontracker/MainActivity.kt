package nz.benlawrence.splatoontracker

import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import nz.benlawrence.splatoontracker.data.UserPreferencesViewModel
import nz.benlawrence.splatoontracker.ui.theme.SplatoonTrackerTheme
import nz.benlawrence.splatoontracker.ui.views.Challenge
import nz.benlawrence.splatoontracker.ui.views.HomeScreen
import nz.benlawrence.splatoontracker.ui.views.SalmonRun
import nz.benlawrence.splatoontracker.ui.views.Settings
import nz.benlawrence.splatoontracker.ui.views.Splatnet
import nz.benlawrence.splatoontracker.widget.SplatoonWidgetWorker
import java.util.jar.Manifest

class MainActivity : ComponentActivity() {
  val splatoonViewModel: SplatoonDataViewModel = SplatoonDataViewModel()
  val userPreferencesViewModel: UserPreferencesViewModel by viewModels()
  val coralViewModel: CoralDataViewModel by lazy { CoralDataViewModel(this) }

  private val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission()
  ) { granted ->
    userPreferencesViewModel.setNotificationsEnabled(granted)
    if (granted && !userPreferencesViewModel.hasSeenOnBoarding.value) {
      userPreferencesViewModel.setDefaultNotificationSettings()
    }
  }

  fun enableNotifications() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    } else {
      userPreferencesViewModel.setNotificationsEnabled(true)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    SplatoonWidgetWorker.schedule(this)

    enableEdgeToEdge()
    setContent {
      val hasSeenOnboarding by userPreferencesViewModel.hasSeenOnBoarding.collectAsStateWithLifecycle()

      LaunchedEffect(hasSeenOnboarding) {
        if (!hasSeenOnboarding) {
          enableNotifications()

        }
      }

      SplatoonTrackerTheme {
        SplatoonTrackerApp(
          splatoonViewModel,
          coralViewModel,
          userPreferencesViewModel,
          onRequestNotificationPermission = {
            enableNotifications()
          }
        )
      }
    }
  }
}

@Composable
fun SplatoonTrackerApp(
  splatoonViewModel: SplatoonDataViewModel,
  coralViewModel: CoralDataViewModel,
  userPreferencesViewModel: UserPreferencesViewModel,
  onRequestNotificationPermission: () -> Unit
) {
  var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

  NavigationSuiteScaffold(
    navigationSuiteItems = {
      AppDestinations.entries.forEach {
        item(
          icon = {
            Icon(
              painterResource(it.icon),
              contentDescription = it.label,
              modifier = Modifier.size(it.iconSize)
            )
          },
          label = { Text(it.label) },
          selected = it == currentDestination,
          onClick = { currentDestination = it }
        )
      }
    }
  ) {
    when (currentDestination) {
      AppDestinations.HOME ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          HomeScreen(
            viewModel = splatoonViewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }

      AppDestinations.GRIZZCO ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          SalmonRun(
            viewModel = splatoonViewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }

      AppDestinations.CHALLENGE ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Challenge(
            viewModel = splatoonViewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }

      AppDestinations.SETTINGS ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Settings(
            viewModel = userPreferencesViewModel,
            modifier = Modifier.padding(innerPadding),
            onRequestNotificationPermission
          )
        }

      AppDestinations.SPLATNET ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Splatnet(
            viewModel = coralViewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }
    }
  }
}

enum class AppDestinations(
  val label: String,
  val icon: Int,
  val iconSize: Dp = 32.dp
) {
  HOME("Schedules", R.drawable.turf_war),
  GRIZZCO("Salmon Run", R.drawable.coop),
  CHALLENGE("Challenge", R.drawable.challenge),
  SETTINGS("Settings", R.drawable.bankara_battle),
  SPLATNET("Splatnet", R.drawable.ic_home)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  SplatoonTrackerTheme {
    Greeting("Android")
  }
}