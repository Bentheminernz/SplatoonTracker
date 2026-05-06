package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.toDisplayData
import nz.benlawrence.splatoontracker.ui.components.ScheduleCard
import nz.benlawrence.splatoontracker.ui.components.ScheduleDisplayData
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  viewModel: SplatoonDataViewModel,
  coralViewModel: CoralDataViewModel,
  navController: NavController
) {
  Column {
    when (val state = viewModel.dataState) {
      is SplatoonDataState.Success -> {
        val now = Instant.now()
        val currentRegular = state.data.regularSchedules.nodes.first {
          Instant.parse(it.startTime).isBefore(now) && Instant.parse(it.endTime)
            .isAfter(now)
        }
        val nextRegular = state.data.regularSchedules.nodes.first {
          it.startTime != currentRegular.startTime
        }
        val currentBankara = state.data.bankaraSchedules.nodes.first {
          Instant.parse(it.startTime).isBefore(now) && Instant.parse(it.endTime)
            .isAfter(now)
        }
        val nextBankara = state.data.bankaraSchedules.nodes.first {
          it.startTime != currentBankara.startTime
        }
        val currentX = state.data.xSchedules.nodes.first {
          Instant.parse(it.startTime).isBefore(now) && Instant.parse(it.endTime)
            .isAfter(now)
        }
        val nextX = state.data.xSchedules.nodes.first {
          it.startTime != currentX.startTime
        }

        var selectedMatch by remember { mutableStateOf<MatchType?>(null) }
        var showBankaraOpenSheet by remember { mutableStateOf<Boolean>(false) }

        Column(
          verticalArrangement = Arrangement.spacedBy(16.dp),
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
          Text(
            "Schedules",
            fontFamily = BlitzFontFamily,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
          )

          ScheduleCard(
            typename = currentRegular.regularMatchSetting.__typename,
            currentNode = ScheduleDisplayData(
              currentRegular.regularMatchSetting.vsStages,
              currentRegular.regularMatchSetting.vsRule,
              currentRegular.startTime,
              currentRegular.endTime
            ),
            nextNode = ScheduleDisplayData(
              nextRegular.regularMatchSetting.vsStages,
              nextRegular.regularMatchSetting.vsRule,
              nextRegular.startTime,
              nextRegular.endTime
            ),
            rotation = -2f,
            onViewSchedule = { selectedMatch = MatchType.Regular }
          )

          ScheduleCard(
            typename = "${currentBankara.bankaraMatchSettings.first { it.bankaraMode == "CHALLENGE" }.__typename}Challenge",
            currentNode = currentBankara.toDisplayData("CHALLENGE"),
            nextNode = nextBankara.toDisplayData("CHALLENGE"),
            rotation = 2f,
            onViewSchedule = { selectedMatch = MatchType.BankaraChallenge },
          )

          ScheduleCard(
            typename = "${currentBankara.bankaraMatchSettings.first { it.bankaraMode == "OPEN" }.__typename}Open",
            currentNode = currentBankara.toDisplayData("OPEN"),
            nextNode = nextBankara.toDisplayData("OPEN"),
            rotation = -2f,
            onViewSchedule = { selectedMatch = MatchType.BankaraOpen },
          )
          Button(onClick = {
            showBankaraOpenSheet = true
          }) {
            Text("show sheet")
          }

          ScheduleCard(
            typename = currentX.xMatchSetting.__typename,
            currentNode = ScheduleDisplayData(
              currentX.xMatchSetting.vsStages,
              currentX.xMatchSetting.vsRule,
              currentX.startTime,
              currentX.endTime
            ),
            nextNode = ScheduleDisplayData(
              nextX.xMatchSetting.vsStages,
              nextX.xMatchSetting.vsRule,
              nextX.startTime,
              nextX.endTime
            ),
            rotation = 2f,
            onViewSchedule = { selectedMatch = MatchType.XBattle }
          )

          selectedMatch?.let { type ->
            ModalBottomSheet(onDismissRequest = { selectedMatch = null }) {
              UpcomingBattleSheet(data = state.data, type = type)
            }
          }

          if (showBankaraOpenSheet) {
            ModalBottomSheet(onDismissRequest = { showBankaraOpenSheet = false }) {
              LaunchedEffect(Unit) {
                coralViewModel.getBankaraBattleHistories()
              }

              when (val bankaraState = coralViewModel.dataState.bankaraBattleHistories) {
                is DataState.Success ->
                  LazyColumn {
                    val allCurrentMaps = currentBankara.bankaraMatchSettings
                      .filter {
                        it.bankaraMode == "OPEN"
                      }
                      .flatMap {
                        it.vsStages
                      }

                    val allPlayerMaps = bankaraState.data.historyGroups.nodes
                      .flatMap {
                        it.historyDetails.nodes
                      }
                      .filter {
                        it.vsStage.id in allCurrentMaps.map { stage -> stage.id }
                      }


                    items(allPlayerMaps) { map ->
                      Button(onClick = {
                        navController.navigate("bankara/detail/${map.id}")
                      }) {
                        Text(map.vsStage.name)
                      }
                    }

                    if (allPlayerMaps.isEmpty()) {
                      item {
                        Text("No battles found for current maps.")
                      }
                    }
                  }

                is DataState.Loading -> Text("loading")
                is DataState.Error -> Text(bankaraState.message)
              }
            }
          }
        }
      }

      is SplatoonDataState.Error ->
        Text(state.message)

      is SplatoonDataState.Loading ->
        Text("loading")
    }
  }
}

sealed class MatchType {
  data object Regular : MatchType()
  data object BankaraChallenge : MatchType()
  data object BankaraOpen : MatchType()
  data object XBattle : MatchType()
}