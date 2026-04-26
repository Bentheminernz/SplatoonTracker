package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.data.SplatoonDataViewModel
import nz.benlawrence.splatoontracker.data.models.Data
import nz.benlawrence.splatoontracker.ui.components.MatchTypeHeader
import nz.benlawrence.splatoontracker.ui.components.VsStageItem
import java.time.Instant

@Composable
fun UpcomingBattleSheet(
    data: Data,
    type: MatchType
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        MatchTypeHeader(type = type)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
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

                is MatchType.BankaraOpen ->
                    items(data.bankaraSchedules.nodes.mapNotNull { node ->
                        val openSettings = node.bankaraMatchSettings.filter { it.bankaraMode == "OPEN" }
                        if (openSettings.isEmpty()) null
                        else node.copy(bankaraMatchSettings = openSettings)
                    }) { item ->
                        VsStageItem(
                            vsStages = item.bankaraMatchSettings.first().vsStages,
                            vsRule = item.bankaraMatchSettings.first().vsRule,
                            startTime = item.startTime,
                            endTime = item.endTime,
                            isNext = Instant.parse(item.startTime).isAfter(Instant.now())
                        )
                    }

                is MatchType.BankaraChallenge ->
                    items(data.bankaraSchedules.nodes.mapNotNull { node ->
                        val challengeSettings = node.bankaraMatchSettings.filter { it.bankaraMode == "CHALLENGE" }
                        if (challengeSettings.isEmpty()) null
                        else node.copy(bankaraMatchSettings = challengeSettings)
                    }) { item ->
                        VsStageItem(
                            vsStages = item.bankaraMatchSettings.first().vsStages,
                            vsRule = item.bankaraMatchSettings.first().vsRule,
                            startTime = item.startTime,
                            endTime = item.endTime,
                            isNext = Instant.parse(item.startTime).isAfter(Instant.now())
                        )
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