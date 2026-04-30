package nz.benlawrence.splatoontracker.ui.views.SalmonRun

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.CoopGroupingRegularScheduleNode
import nz.benlawrence.splatoontracker.ui.components.SalmonRunListItem
import java.time.Duration
import java.time.Instant

@Composable
fun SalmonRunSchedule(
  viewModel: SplatoonDataViewModel,
  coralViewModel: CoralDataViewModel,
  navController: NavController
) {
  LaunchedEffect(Unit) {
    val data = (coralViewModel.dataState.coopResult as? DataState.Success)?.data
    if (data == null) {
      coralViewModel.getCoopResult()
    }
  }

  Column(modifier = Modifier.padding(16.dp)) {
    when (val state = viewModel.dataState) {
      is SplatoonDataState.Success ->
        Column {
          val now = Instant.now()
          val currentSchedule: CoopGroupingRegularScheduleNode =
            state.data.coopGroupingSchedule.regularSchedules.nodes.filter {
              Instant.parse(it.startTime).isBefore(now) &&
                  Instant.parse(it.endTime).isAfter(now)
            }.first()
          val timeRemaining by produceState(initialValue = "") {
            while (true) {
              val end = Instant.parse(currentSchedule.endTime)
              val duration = Duration.between(Instant.now(), end)
              val hours = duration.toHours()
              val minutes = duration.toMinutesPart()
              val seconds = duration.toSecondsPart()
              value = "%02d:%02d:%02d".format(hours, minutes, seconds)
              delay(1000)
            }
          }
          val bossImage = when (currentSchedule.setting.boss.id) {
            "Q29vcEVuZW15LTIz" -> R.drawable.cohozuna
            "Q29vcEVuZW15LTI0" -> R.drawable.horrorboros
            "Q29vcEVuZW15LTI1" -> R.drawable.cohozuna
            "Q29vcEVuZW15LTMw" -> R.drawable.triumvirate
            else -> R.drawable.ic_launcher_foreground
          }

          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
              AsyncImage(
                model = bossImage,
                contentDescription = "Image of ${currentSchedule.setting.boss.name}",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
              )

              Text(
                currentSchedule.setting.coopStage.name,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
              )
            }

            AsyncImage(
              model = currentSchedule.setting.coopStage.image.url,
              contentDescription = "Image of ${currentSchedule.setting.coopStage.name}",
              contentScale = ContentScale.Crop,
              modifier = Modifier
                  .fillMaxWidth()
                  .height(150.dp)
                  .padding(vertical = 8.dp)
                  .clip(RoundedCornerShape(16.dp))
            )

            Text("Ends in $timeRemaining")

            LazyRow(
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              items(currentSchedule.setting.weapons) { weapon ->
                AsyncImage(
                  model = weapon.image.url,
                  contentDescription = "Image of ${weapon.name}",
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.size(64.dp)
                )
              }
            }
          }

          LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
              .fillMaxSize()
          ) {
            items(state.data.coopGroupingSchedule.regularSchedules.nodes) { node ->
              val battleHistory = (coralViewModel.dataState.coopResult as? DataState.Success)
                ?.data
                ?.historyGroups
                ?.nodes
                .orEmpty()
                .flatMap { it.historyDetails.nodes }
                .filter { it.coopStage.id == node.setting.coopStage.id }
              Log.i("SalmonRun", "Battle History: $battleHistory")

              battleHistory.forEach {
                Button(onClick = {
                  navController.navigate("coop_detail/${it.id}")
                }) {
                  Text("View Battle ${it.id.take(10)}")
                }
              }

              SalmonRunListItem(node)
            }
          }
        }


      else ->
        Text("else")
    }
  }
}