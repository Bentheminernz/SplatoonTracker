package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import nz.benlawrence.splatoontracker.ui.components.ChallengeCard

@Composable
fun Challenge(
  viewModel: SplatoonDataViewModel,
  navController: NavController
) {
  when (val state = viewModel.dataState) {
    is SplatoonDataState.Success ->
      Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
      ) {
        LazyColumn {
          items(state.data.eventSchedules.nodes) { item ->
            ChallengeCard(challenge = item)
          }
        }
      }

    is SplatoonDataState.Loading ->
      Text("Loading...")

    is SplatoonDataState.Error ->
      Text("Error: ${state.message}")
  }
}