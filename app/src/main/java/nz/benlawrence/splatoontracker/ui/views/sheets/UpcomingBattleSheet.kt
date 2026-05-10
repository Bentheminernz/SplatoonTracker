package nz.benlawrence.splatoontracker.ui.views.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.Data
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleHistories
import nz.benlawrence.splatoontracker.ui.components.MatchTypeHeader
import nz.benlawrence.splatoontracker.ui.components.MatchTypeOrTypename
import nz.benlawrence.splatoontracker.ui.components.VsStageItem
import nz.benlawrence.splatoontracker.ui.views.MatchType
import nz.benlawrence.splatoontracker.utils.toTitleCase
import java.time.Instant

@Composable
fun UpcomingBattleSheet(
  data: Data,
  bankaraState: DataState<VsBattleHistories>,
  type: MatchType,
  navController: NavController,
  closeModal: () -> Unit
) {
  var selectedStageId by remember { mutableStateOf<Int?>(null) }

  Column(
    modifier = Modifier.fillMaxWidth().padding(16.dp)
  ) {
    MatchTypeHeader(source = MatchTypeOrTypename.KnownType(type))

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      when (type) {
        is MatchType.Regular ->
          items(data.regularSchedules.nodes) { item ->
            VsStageItem(
              vsStages = item.regularMatchSetting.vsStages,
              vsRule = item.regularMatchSetting.vsRule,
              startTime = item.startTime,
              endTime = item.endTime,
              isNext = Instant.parse(item.startTime).isAfter(Instant.now())
            )
          }

        is MatchType.BankaraOpen, is MatchType.BankaraChallenge -> {
          val mode = if (type is MatchType.BankaraOpen) "OPEN" else "CHALLENGE"
          val bankaraNodes = data.bankaraSchedules.nodes.mapNotNull { node ->
            val settings = node.bankaraMatchSettings.filter { it.bankaraMode == mode }
            if (settings.isEmpty()) null else node.copy(bankaraMatchSettings = settings)
          }

          items(bankaraNodes) { item ->
            val setting = item.bankaraMatchSettings.first()
            val stages = setting.vsStages
            val isSelected = selectedStageId?.let { id -> stages.any { it.vsStageId == id } } ?: false

            val matchingBattles = if (isSelected && bankaraState is DataState.Success) {
              val stageIds = stages.map { it.id }
              bankaraState.data.historyGroups.nodes
                .flatMap { it.historyDetails.nodes }
                .filter { it.vsStage.id in stageIds }
            } else emptyList()

            Column(
              modifier = Modifier.clickable {
                selectedStageId = if (isSelected) null else stages.first().vsStageId
              }
            ) {
              VsStageItem(
                vsStages = stages,
                vsRule = setting.vsRule,
                startTime = item.startTime,
                endTime = item.endTime,
                isNext = Instant.parse(item.startTime).isAfter(Instant.now())
              )

              if (isSelected) {
                if (matchingBattles.isEmpty()) {
                  Text("No past battles on these stages")
                } else {
                  matchingBattles.forEach { battle ->
                    Button(onClick = {
                      closeModal()
                      val type = when (type) {
                        is MatchType.Regular -> "regular"
                        is MatchType.BankaraChallenge -> "bankara_challenge"
                        is MatchType.BankaraOpen -> "bankara_open"
                        is MatchType.XBattle -> "xbattle"
                      }
                      navController.navigate("vsbattle/$type/detail/${battle.id}")
                    }) {
                      Text("${battle.vsStage.name} — ${battle.judgement.toTitleCase()}")
                    }
                  }
                }
              }
            }
          }
        }

        is MatchType.XBattle ->
          items(data.xSchedules.nodes) { item ->
            VsStageItem(
              vsStages = item.xMatchSetting.vsStages,
              vsRule = item.xMatchSetting.vsRule,
              startTime = item.startTime,
              endTime = item.endTime,
              isNext = Instant.parse(item.startTime).isAfter(Instant.now())
            )
          }
      }
    }
  }
}