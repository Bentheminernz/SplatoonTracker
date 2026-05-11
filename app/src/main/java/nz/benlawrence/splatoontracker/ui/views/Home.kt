package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataState
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.toDisplayData
import nz.benlawrence.splatoontracker.ui.components.ScheduleCard
import nz.benlawrence.splatoontracker.ui.components.ScheduleDisplayData
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.ui.views.sheets.UpcomingBattleSheet
import nz.benlawrence.splatoontracker.ui.views.sheets.UserBattlesSheet
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
        var selectedBattleMatch by remember { mutableStateOf<MatchType?>(null) }

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
            onViewSchedule = { selectedMatch = MatchType.Regular },
            onShowBattles = { selectedBattleMatch = MatchType.Regular }
          )

          ScheduleCard(
            typename = "${currentBankara.bankaraMatchSettings.first { it.bankaraMode == "CHALLENGE" }.__typename}Challenge",
            currentNode = currentBankara.toDisplayData("CHALLENGE"),
            nextNode = nextBankara.toDisplayData("CHALLENGE"),
            rotation = 2f,
            onViewSchedule = { selectedMatch = MatchType.BankaraChallenge },
            onShowBattles = { selectedBattleMatch = MatchType.BankaraChallenge }
          )

          ScheduleCard(
            typename = "${currentBankara.bankaraMatchSettings.first { it.bankaraMode == "OPEN" }.__typename}Open",
            currentNode = currentBankara.toDisplayData("OPEN"),
            nextNode = nextBankara.toDisplayData("OPEN"),
            rotation = -2f,
            onViewSchedule = { selectedMatch = MatchType.BankaraOpen },
            onShowBattles = { selectedBattleMatch = MatchType.BankaraOpen }
          )

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
            onViewSchedule = { selectedMatch = MatchType.XBattle },
            onShowBattles = { selectedBattleMatch = MatchType.XBattle }
          )

          selectedMatch?.let { type ->
            ModalBottomSheet(onDismissRequest = { selectedMatch = null }) {
              UpcomingBattleSheet(
                data = state.data,
                bankaraState = coralViewModel.dataState.bankaraBattleHistories,
                type = type,
                navController = navController,
                closeModal = {
                  selectedMatch = null
                }
              )
            }
          }

          selectedBattleMatch?.let { type ->
            ModalBottomSheet(onDismissRequest = { selectedBattleMatch = null }) {
              LaunchedEffect(Unit) {
                coralViewModel.getBankaraBattleHistories()
                coralViewModel.getRegularBattleHistories()
              }

              UserBattlesSheet(
                matchType = type,
                regularState = coralViewModel.dataState.regularBattleHistories,
                bankaraState = coralViewModel.dataState.bankaraBattleHistories,
                currentRegular = currentRegular,
                currentBankara = currentBankara,
                navController = navController
              )
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