package nz.benlawrence.splatoontracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import nz.benlawrence.splatoontracker.ui.theme.SplatoonTrackerTheme
import nz.benlawrence.splatoontracker.ui.views.Challenge
import nz.benlawrence.splatoontracker.ui.views.HomeScreen
import nz.benlawrence.splatoontracker.ui.views.SalmonRun
import nz.benlawrence.splatoontracker.widget.SplatoonWidgetWorker

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestPermissions(
        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
        0
      )
    }

    SplatoonWidgetWorker.schedule(this)

    enableEdgeToEdge()
    setContent {
      SplatoonTrackerTheme {
        SplatoonTrackerApp()
      }
    }
  }
}

@PreviewScreenSizes
@Composable
fun SplatoonTrackerApp() {
  var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
  val viewModel: SplatoonDataViewModel = SplatoonDataViewModel()

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
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }

      AppDestinations.GRIZZCO ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          SalmonRun(
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }

      AppDestinations.CHALLENGE ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Challenge(
            viewModel = viewModel,
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