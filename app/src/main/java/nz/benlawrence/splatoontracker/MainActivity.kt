package nz.benlawrence.splatoontracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import nz.benlawrence.splatoontracker.data.UserPreferencesViewModel
import nz.benlawrence.splatoontracker.ui.theme.SplatoonTrackerTheme
import nz.benlawrence.splatoontracker.ui.views.Challenge
import nz.benlawrence.splatoontracker.ui.views.HomeScreen
import nz.benlawrence.splatoontracker.ui.views.SalmonRun.CoopHistoryDetail
import nz.benlawrence.splatoontracker.ui.views.SalmonRun.SalmonRunSchedule
import nz.benlawrence.splatoontracker.ui.views.Settings
import nz.benlawrence.splatoontracker.ui.views.Splatnet
import nz.benlawrence.splatoontracker.widget.SplatoonWidgetWorker

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
          splatoonViewModel = splatoonViewModel,
          coralViewModel = coralViewModel,
          userPreferencesViewModel = userPreferencesViewModel,
          onRequestNotificationPermission = { enableNotifications() }
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
      AppDestinations.HOME -> {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
          ) {
            composable("home") {
              HomeScreen(
                viewModel = splatoonViewModel,
                navController = navController
              )
            }
            // Add Home tab detail screens here, e.g.:
            // composable("schedule_detail/{id}") { backStackEntry ->
            //     val id = backStackEntry.arguments?.getString("id")
            //     ScheduleDetailScreen(id = id, navController = navController)
            // }
          }
        }
      }

      AppDestinations.GRIZZCO -> {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavHost(
            navController = navController,
            startDestination = "salmon_run",
            modifier = Modifier.padding(innerPadding)
          ) {
            composable("salmon_run") {
              SalmonRunSchedule(
                viewModel = splatoonViewModel,
                coralViewModel = coralViewModel,
                navController = navController
              )
            }
            composable("coop_detail/{id}") { backStackEntry ->
              val id = backStackEntry.arguments?.getString("id")
              CoopHistoryDetail(
                coopHistoryId = id ?: "",
                viewModel = coralViewModel,
                navController = navController
              )
            }
          }
        }
      }

      AppDestinations.CHALLENGE -> {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavHost(
            navController = navController,
            startDestination = "challenge",
            modifier = Modifier.padding(innerPadding)
          ) {
            composable("challenge") {
              Challenge(
                viewModel = splatoonViewModel,
                navController = navController
              )
            }
          }
        }
      }

      AppDestinations.SETTINGS -> {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavHost(
            navController = navController,
            startDestination = "settings",
            modifier = Modifier.padding(innerPadding)
          ) {
            composable("settings") {
              Settings(
                viewModel = userPreferencesViewModel,
                navController = navController,
                onRequestNotificationPermission = onRequestNotificationPermission
              )
            }
          }
        }
      }

      AppDestinations.SPLATNET -> {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavHost(
            navController = navController,
            startDestination = "splatnet",
            modifier = Modifier.padding(innerPadding)
          ) {
            composable("splatnet") {
              Splatnet(
                viewModel = coralViewModel,
                navController = navController
              )
            }
          }
        }
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